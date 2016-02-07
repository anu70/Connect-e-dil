package verkstad.org.in.valentineapp;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by coder on 1/15/2016.
 */
public class ThreeFragment extends android.support.v4.app.Fragment  {
    //String login_url="http://192.168.16.1/Valentine/index.php";
    String[] name={"anu","abhi","chetan"};
    Context context;
    private ShoutListview adapter;
   private Long lastMsgTime;
    private static Handler handler;
    private boolean isRunning;
    private List<Shout> Shoutitems;
    ListView listView;
   Button threefragment;
    LayoutInflater inflator;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shoutitems = new ArrayList<Shout>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_three, container, false);
        Button shout=(Button)view.findViewById(R.id.shout);
        //ListView listView=(ListView)view.findViewById(R.id.list);
        listView= (ListView)view. findViewById(R.id.list);

       /** ArrayAdapter adapter=new ArrayAdapter(ThreeFragment.this.getActivity(), android.support.design.R.layout.support_simple_spinner_dropdown_item,name);
        listView.setAdapter(adapter); **/
       /** for(int i=0;i<3;i++) {
            Shout shouts = new Shout();
            shouts.setId("123456789");
            shouts.setMessage("Hi There");
            shouts.setName("Abhi");
            shouts.setTime((long) 1453139839);
            Shoutitems.add(shouts);
        } **/


        //ShoutList();
        adapter=new ShoutListview(ThreeFragment.this.getActivity(),Shoutitems);
        listView.setAdapter(adapter);
        //listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
       //listView.setStackFromBottom(false);
        handler=new Handler();
        shout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = Profile.getCurrentProfile();
                final String name = profile.getName();
                final String id=profile.getId();
                final EditText editText = (EditText) view.findViewById(R.id.EditText);
                final String message = editText.getText().toString();
                if(editText.length()==0)
                    return;
                Shout shouts = new Shout();
                final Date obj=new Date();
                shouts.setId(id);
                shouts.setMessage(message);
                shouts.setName(name);
                // shouts.setTime(jsonObject.getLong("time"));
                shouts.setTime(obj.getTime());
                final Long time=shouts.getTime();

                Shoutitems.add(0, shouts);
                adapter.notifyDataSetChanged();

                //listView.setSelection(0);
               //listView.smoothScrollToPosition(0);

                editText.setText(null);
                RequestQueue request = Volley.newRequestQueue(ThreeFragment.this.getActivity());


                StringRequest stringrequest = new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      /**  Toast toast= Toast.makeText(ThreeFragment.this.getActivity(), "Check Internet Connection ", Toast.LENGTH_LONG);
                        toast.getView().setBackgroundColor(Color.RED);
                        toast.show(); **/
                       /** inflator = (LayoutInflater)ThreeFragment.this.getActivity() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v= inflator.inflate(R.layout.internet_connection, null);
                        threefragment=(Button)v.findViewById(R.id.retry);
                        threefragment.setTag("threefragment"); **/
                        getActivity().setContentView(R.layout.internet_connection);

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("message", message);
                        params.put("id",id);
                        params.put("time",time.toString());
                        return params;
                    }
                };
                request.add(stringrequest);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
        ShoutList();

    }

    @Override
    public void onPause() {

        isRunning = false;
       RequestQueue requestQueue=Volley.newRequestQueue(ThreeFragment.this.getActivity());
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        super.onPause();
    }

    public void ShoutList(){

        RequestQueue request= Volley.newRequestQueue(ThreeFragment.this.getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {

                    JSONArray jsonArray=new JSONArray(s);
                    if(jsonArray!=null && jsonArray.length()>0) {
                        for (int i =0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Shout shouts = new Shout();
                            shouts.setId(jsonObject.getString("id"));
                            shouts.setMessage(jsonObject.getString("message"));
                            shouts.setName(jsonObject.getString("name"));
                            shouts.setTime(jsonObject.getLong("time"));
                            Shoutitems.add(0, shouts);

                            if (lastMsgTime == null
                                    || lastMsgTime.compareTo(shouts.getTime())<0)
                                lastMsgTime = shouts.getTime();
                            adapter.notifyDataSetChanged();

                            //listView.setSelection(0);
                            //listView.smoothScrollToPosition(0);
                        }
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRunning)
                            ShoutList();
                        }
                    },1000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               if (Shoutitems.size()==0)
                params.put("TAG","ShoutList");
                else{
                    params.put("TAG","ShoutListSorted");
                    params.put("lastMsgTime",lastMsgTime.toString());
                   params.put("id",Profile.getCurrentProfile().getId());
                   //params.put("lastMsgTime","1450289156000");
                }
                return params;
            }
        };
        request.add(stringRequest);
    }
}
