package verkstad.org.in.valentineapp;

/**
 * Created by coder on 1/17/2016.
 */

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anu on 1/11/2016.
 */
public class
        Functions {
    Context context;
    Activity activity;
    public interface VolleyCallback{
        void onSuccess(int size);
    }



    String current_user_name,current_user_gender,current_user_id;
    public void get_current_user(Context context1){
        context=context1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("current_profile", context.MODE_PRIVATE);
        current_user_name = sharedPreferences.getString("name",null);
        current_user_gender = sharedPreferences.getString("gender",null);
        current_user_id= sharedPreferences.getString("id",null);
        //Toast.makeText(context,current_user_name,Toast.LENGTH_SHORT).show();

    }



    // Sending rose
    static String sender,receiver,red_rose,yellow_rose,anonymous,message;
    public void send(Context context1,String sender1,String receiver1, String red_rose1,String yellow_rose1,String anonymous1,String message1){
        sender=sender1;
        receiver=receiver1;
        red_rose=red_rose1;
        yellow_rose=yellow_rose1;
        anonymous=anonymous1;
        message=message1;
        context=context1;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String message = jsonObject.getString("MESSAGE");
                    //String ann  = jsonObject.getString("anonymous");
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                activity = (Activity) context;
                activity.setContentView(R.layout.internet_connection);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sender",sender);
                params.put("receiver",receiver);
                params.put("red_rose",red_rose);
                params.put("yellow_rose",yellow_rose);
                params.put("anonymous",anonymous);
                params.put("message",message);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



    int size_json;
    String[] sender_json,receiver_json,red_rose_json,yellow_rose_json,message_json,anonymous_json;
    public int count_roses(Context context1, final VolleyCallback callback){
        context=context1;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    //Toast.makeText(context, "checking",Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = new JSONArray(s);

                    sender_json=new String[jsonArray.length()];  anonymous_json=new String[jsonArray.length()];
                    receiver_json=new String[jsonArray.length()];  red_rose_json=new String[jsonArray.length()];
                    yellow_rose_json=new String[jsonArray.length()]; message_json=new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        sender_json[i]=n.getString("sender");
                        receiver_json[i]=n.getString("receiver");
                        red_rose_json[i]=n.getString("red_roses");
                        yellow_rose_json[i]=n.getString("yellow_roses");
                        message_json[i]=n.getString("message");
                        anonymous_json[i]=n.getString("anonymous");
                    }
                    // Toast.makeText(context,Arrays.toString(sender_json),Toast.LENGTH_LONG).show();

                    size_json=jsonArray.length();
                    callback.onSuccess(jsonArray.length());

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                activity = (Activity) context;
                activity.setContentView(R.layout.internet_connection);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TAG","CountRoses");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        return size_json;
    }




    String[] users,email,id,gender,red_roses_from_girls,red_roses_from_boys,yellow_roses_from_girls,yellow_roses_from_boys,total_roses_from_girls,
            total_roses_from_boys,total_yellow_roses,total_red_roses,total_roses;
    int users_json_size;
    public int get_users(Context context1, final VolleyCallback callback){
        context=context1;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                // Toast.makeText(context,"ok2",Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    users = new String[jsonArray.length()]; email = new String[jsonArray.length()];
                    id = new String[jsonArray.length()]; gender = new String[jsonArray.length()];
                    red_roses_from_girls = new String[jsonArray.length()];red_roses_from_boys = new String[jsonArray.length()];
                    yellow_roses_from_girls = new String[jsonArray.length()]; yellow_roses_from_boys = new String[jsonArray.length()];
                    total_roses_from_girls = new String[jsonArray.length()]; total_roses_from_boys = new String[jsonArray.length()];
                    total_yellow_roses = new String[jsonArray.length()]; total_red_roses = new String[jsonArray.length()];
                    total_roses= new String[jsonArray.length()];


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        users[i]=jsonObject.getString("name");
                        email[i]=jsonObject.getString("email_id");
                        id[i]=jsonObject.getString("id");
                        gender[i]=jsonObject.getString("gender");
                        red_roses_from_girls[i]=jsonObject.getString("red_roses_from_girls");
                        red_roses_from_boys[i]=jsonObject.getString("red_roses_from_boys");
                        yellow_roses_from_girls[i]=jsonObject.getString("yellow_roses_from_girls");
                        yellow_roses_from_boys[i]=jsonObject.getString("yellow_roses_from_boys");
                        total_roses_from_girls[i]=jsonObject.getString("total_roses_from_girls");
                        total_roses_from_boys[i]=jsonObject.getString("total_roses_from_boys");
                        total_yellow_roses[i]=jsonObject.getString("total_yellow_roses");
                        total_red_roses[i]=jsonObject.getString("total_red_roses");
                        total_roses[i]=jsonObject.getString("total_roses");
                    }
                    users_json_size=jsonArray.length();

                    // Toast.makeText(context, Arrays.toString(gender),Toast.LENGTH_LONG).show();
                    callback.onSuccess(jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                activity= (Activity) context;
                activity.setContentView(R.layout.internet_connection);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TAG","GetUsers");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return users_json_size;

    }




    String gender_sorting;
    String[] users_sorted,gender_sorted,red_roses_from_girls_sorted,red_roses_from_boys_sorted,yellow_roses_from_girls_sorted,yellow_roses_from_boys_sorted;
    public int sorting(Context context1, String gender_sorting1, final VolleyCallback callback){
        gender_sorting=gender_sorting1;
        context=context1;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                // Toast.makeText(context, Arrays.toString(users_sorted),Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    users_sorted = new String[jsonArray.length()];gender_sorted = new String[jsonArray.length()];
                    red_roses_from_girls_sorted = new String[jsonArray.length()];red_roses_from_boys_sorted = new String[jsonArray.length()];
                    yellow_roses_from_girls_sorted = new String[jsonArray.length()]; yellow_roses_from_boys_sorted = new String[jsonArray.length()];


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        users_sorted[i]=jsonObject.getString("name");
                        gender_sorted[i]=jsonObject.getString("gender");
                        red_roses_from_girls_sorted[i]=jsonObject.getString("red_roses_from_girls");
                        red_roses_from_boys_sorted[i]=jsonObject.getString("red_roses_from_boys");
                        yellow_roses_from_girls_sorted[i]=jsonObject.getString("yellow_roses_from_girls");
                        yellow_roses_from_boys_sorted[i]=jsonObject.getString("yellow_roses_from_boys");

                    }
                    users_json_size=jsonArray.length();

                    //  Toast.makeText(context, Arrays.toString(users_sorted),Toast.LENGTH_LONG).show();
                    callback.onSuccess(jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                activity = (Activity) context;
                activity.setContentView(R.layout.internet_connection);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TAG","Sorting");
                params.put("gender_sorting",gender_sorting);
                return params;
            }
        };

        requestQueue.add(stringRequest);
        return users_json_size;
    }





    RecyclerView recyclerviewlb;
    SearchView searchView;
    ArrayList<Integer> rank_search;ArrayList<String> name_search,red_rose_search,yellow_rose_search;
    public void display_in_recyclerview(Context context1,RecyclerView recyclerviewlb1,SearchView searchView1,ArrayList<Integer> rank1,ArrayList<String> name1,ArrayList<String> red_rose1,ArrayList<String> yellow_rose1){
        context=context1;
        recyclerviewlb=recyclerviewlb1;
        searchView=searchView1;
        rank_search=rank1;name_search=name1;red_rose_search=red_rose1;yellow_rose_search=yellow_rose1;
        final ArrayList<Integer> RANK = new ArrayList<Integer>();
        final ArrayList<String> NAME = new ArrayList<String>();
        final ArrayList<String> RED_ROSES = new ArrayList<String >();
        final ArrayList<String> YELLOW_ROSE = new ArrayList<String>();

        for (int i=0;i<name_search.size();i++){
            RANK.add(rank_search.get(i));
            NAME.add(name_search.get(i));
            RED_ROSES.add(red_rose_search.get(i));
            YELLOW_ROSE.add(yellow_rose_search.get(i));
        }
        recyclerviewlb.setLayoutManager(new LinearLayoutManager(context));
        RecyclerViewAdapterlb recyclerViewAdapterlb = new RecyclerViewAdapterlb(context,RANK,NAME,RED_ROSES,YELLOW_ROSE);
        recyclerviewlb.setAdapter(recyclerViewAdapterlb);
        recyclerViewAdapterlb.notifyDataSetChanged();

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    RANK.clear();NAME.clear();RED_ROSES.clear();YELLOW_ROSE.clear();
                    for (int i=0;i<name_search.size();i++){
                        RANK.add(rank_search.get(i));
                        NAME.add(name_search.get(i));
                        RED_ROSES.add(red_rose_search.get(i));
                        YELLOW_ROSE.add(yellow_rose_search.get(i));
                    }
                    recyclerviewlb.setLayoutManager(new LinearLayoutManager(context));
                    RecyclerViewAdapterlb recyclerViewAdapterlb = new RecyclerViewAdapterlb(context,RANK,NAME,RED_ROSES,YELLOW_ROSE);
                    recyclerviewlb.setAdapter(recyclerViewAdapterlb);
                    recyclerViewAdapterlb.notifyDataSetChanged();
                }

                else {
                    newText = newText.toLowerCase();
                    RANK.clear();NAME.clear();RED_ROSES.clear();YELLOW_ROSE.clear();
                    for (int i=0;i<name_search.size();i++){
                        String text = name_search.get(i).toLowerCase();
                        if(text.contains(newText)){
                            RANK.add(rank_search.get(i));
                            NAME.add(name_search.get(i));
                            RED_ROSES.add(red_rose_search.get(i));
                            YELLOW_ROSE.add(yellow_rose_search.get(i));
                        }
                    }
                    recyclerviewlb.setLayoutManager(new LinearLayoutManager(context));
                    RecyclerViewAdapterlb recyclerViewAdapterlb = new RecyclerViewAdapterlb(context,RANK,NAME,RED_ROSES,YELLOW_ROSE);
                    recyclerviewlb.setAdapter(recyclerViewAdapterlb);
                    recyclerViewAdapterlb.notifyDataSetChanged();

                }
                return true;
            }
        };

        searchView.setOnQueryTextListener(listener);


    }

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("ping -c 1 www.google.com");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace();
        }
        catch (InterruptedException e) { e.printStackTrace();

        }

        return false;
    }


}