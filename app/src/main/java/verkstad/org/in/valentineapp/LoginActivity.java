package verkstad.org.in.valentineapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String name,email_id,id,gender;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    RelativeLayout fb_login_inner_relative;
    TextView loading_fb_login;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            fb_login_inner_relative.setVisibility(View.GONE);
            loading_fb_login.setVisibility(View.VISIBLE);
            GraphRequest request = GraphRequest.newMeRequest(
             loginResult.getAccessToken(),
             new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(
            JSONObject object,
            GraphResponse response) {
            object=response.getJSONObject();

            try {
               id=object.getString("id").toString();
              email_id=object.getString("email").toString();
              gender=object.getString("gender").toString();
               name=object.getString("name").toString();
                displayMessage(email_id, name, id, gender);


                SharedPreferences sharedPreferences = getSharedPreferences("current_profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",id);
                editor.putString("email_id",email_id);
                editor.putString("gender",gender);
                editor.putString("name",name);
                editor.apply();



            }
            catch (JSONException e) {
            e.printStackTrace();
            }

            }
            });
             Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,gender,email");
            request.setParameters(parameters);
             request.executeAsync();


          /**  Intent intent=new Intent(LoginActivity.this,Home.class);
            startActivity(intent); **/

        }


        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }


                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void displayMessage(final String email_id, final String name, final String id, final String gender){



                RequestQueue request= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //textView.setText(id);
                          Intent intent=new Intent(LoginActivity.this,Home.class);
                         startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //textView.setText(email_id);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email_id",email_id);
                        params.put("name",name);
                        params.put("id",id);
                        params.put("gender",gender);
                        return params;
                    }
                };

                request.add(stringRequest);



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        fb_login_inner_relative= (RelativeLayout) findViewById(R.id.facebook_login_inner_relative);
        loading_fb_login= (TextView) findViewById(R.id.loading_facebook_login);
        //textView = (TextView) view.findViewById(R.id.textView);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        loginButton.registerCallback(callbackManager, callback);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


}
