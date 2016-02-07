package verkstad.org.in.valentineapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import java.util.Arrays;

/**
 * Created by coder on 1/15/2016.
 */
public class OneFragment extends android.support.v4.app.Fragment {
    NestedScrollView scroll_home;
    ImageView home_girl;
    Functions functions;
    TextView red_from_girls,red_from_boys,yellow_from_girls,yellow_from_boys;
    EditText editText2;
    String receiver,message;
    String anonymous="no";
    RadioGroup radioGroup;
    RadioButton radiobutton_red,radiobutton_yellow;
    Button send_flowers,received_flowers,send;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    TableLayout tableLayout_send,tableLayout_received;
    RecyclerView recyclerView;
    ArrayList<String> sender_name,sent_message,sender_id;
    CheckBox anonymous_checkbox;
    String red_rose,yellow_rose;
    ArrayList<String> friends_list_name,friends_list_image;
    ArrayList<Integer> which_rose_sent;
    TableLayout table_onefragment;
    ProgressBar loader_onefragment;
    SwipeRefreshLayout swipeLayout;
    Handler handler;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_one, container, false);

        functions = new Functions();
        radioGroup = (RadioGroup)view.findViewById(R.id.radio);
        autoCompleteTextView =(AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        editText2= (EditText)view.findViewById(R.id.message_box);
        tableLayout_send= (TableLayout) view.findViewById(R.id.send_table_layout);
        tableLayout_received= (TableLayout) view.findViewById(R.id.received_table_layout);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        red_from_girls= (TextView)view.findViewById(R.id.red_from_girls);
        yellow_from_girls= (TextView)view.findViewById(R.id.yellow_form_girls);
        red_from_boys= (TextView) view.findViewById(R.id.red_from_boys);
        yellow_from_boys= (TextView) view.findViewById(R.id.yellow_from_boys);
        send_flowers= (Button) view.findViewById(R.id.send_flower);
        received_flowers= (Button) view.findViewById(R.id.received_flowers);
        send = (Button) view.findViewById(R.id.send);
        anonymous_checkbox= (CheckBox) view.findViewById(R.id.anonymous);
        radiobutton_red= (RadioButton) view.findViewById(R.id.radiobutton_red);
        radiobutton_yellow= (RadioButton) view.findViewById(R.id.radiobutton_yellow);
        sender_name = new ArrayList<String>();
        sender_id = new ArrayList<String>();
        sent_message=new ArrayList<String>();
        which_rose_sent = new ArrayList<Integer>();
        friends_list_name=new ArrayList<String>();
        friends_list_image=new ArrayList<String>();
        table_onefragment= (TableLayout) view.findViewById(R.id.table_onefragment);
        loader_onefragment= (ProgressBar) view.findViewById(R.id.loader_onefragment);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);


        all();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               swipeLayout.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        all();
                    }
                }, 3000);


            }
        });
        received_flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout_received.setVisibility(View.VISIBLE);
                tableLayout_send.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(OneFragment.this.getActivity()));
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(OneFragment.this.getActivity(), sender_name, sent_message, sender_id, which_rose_sent);
                recyclerView.setAdapter(adapter);
            }
        });


        radiobutton_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radiobutton_red:
                        if (checked) {
                            red_rose = "1";
                            yellow_rose = "0";
                        }
                        break;
                    case R.id.radiobutton_yellow:
                        if (checked) {
                            red_rose = "0";
                            yellow_rose = "1";
                        }
                        break;
                }
            }
        });

        radiobutton_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton)v).isChecked();
                switch(v.getId()){
                    case R.id.radiobutton_red:
                        if(checked){
                            red_rose="1";
                            yellow_rose="0";
                        }
                        break;
                    case R.id.radiobutton_yellow:
                        if(checked){
                            red_rose="0";
                            yellow_rose="1";
                        }
                        break;
                }

            }
        });

        anonymous_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=((CheckBox)v).isChecked();
                if(check){
                    anonymous="yes";
                }
                else{anonymous="no";}
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                receiver = arrayAdapter.getItem(position).toString();

            }
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiver = null;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = editText2.getText().toString();

                if (receiver == null) {
                    Toast.makeText(OneFragment.this.getActivity(), "Enter valid name", Toast.LENGTH_SHORT).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(OneFragment.this.getActivity(), "Please Select a rose", Toast.LENGTH_SHORT).show();
                    } else {
                        if(message.equals("")){
                            functions.send(OneFragment.this.getActivity(), functions.current_user_name, receiver, red_rose, yellow_rose, anonymous,"No message to display");
                        }
                        else {
                            functions.send(OneFragment.this.getActivity(), functions.current_user_name, receiver, red_rose, yellow_rose, anonymous, message);
                        }
                    }

                    radioGroup.clearCheck();
                }
                autoCompleteTextView.setText("");
                editText2.setText("");
                anonymous_checkbox.setChecked(false);

            }
        });

        send_flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout_send.setVisibility(View.VISIBLE);
                tableLayout_received.setVisibility(View.GONE);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }






    public void all(){

        functions.get_current_user(OneFragment.this.getActivity());
        tableLayout_send.setVisibility(View.VISIBLE);
        tableLayout_received.setVisibility(View.GONE);



        functions.get_users(OneFragment.this.getActivity(), new Functions.VolleyCallback() {
            @Override
            public void onSuccess(int size) {

                for (int i = 0; i < size; i++) {
                    if (functions.users[i].equals(functions.current_user_name)) {
                        red_from_girls.setText(functions.red_roses_from_girls[i]);
                        yellow_from_girls.setText(functions.yellow_roses_from_girls[i]);
                        red_from_boys.setText(functions.red_roses_from_boys[i]);
                        yellow_from_boys.setText(functions.yellow_roses_from_boys[i]);
                    }
                }
                loader_onefragment.setVisibility(View.GONE);
                table_onefragment.setVisibility(View.VISIBLE);



            }
        });

        functions.get_users(OneFragment.this.getActivity(), new Functions.VolleyCallback() {
            @Override
            public void onSuccess(int size) {
                friends_list_image.clear();friends_list_name.clear();
                for (int i = 0; i < size; i++) {
                    if (!functions.users[i].equals(functions.current_user_name)) {
                        friends_list_name.add(functions.users[i]);
                        friends_list_image.add(functions.id[i]);
                    }
                }
                // recyclerViewAdapterac = new RecyclerViewAdapterac(OneFragment.this.getActivity(),friends_list_image,friends_list_name);
                //  autoCompleteTextView.setAdapter(recyclerViewAdapterac);
                arrayAdapter = new ArrayAdapter<String>(OneFragment.this.getActivity(), R.layout.support_simple_spinner_dropdown_item, friends_list_name);
                autoCompleteTextView.setAdapter(arrayAdapter);
            }
        });


        functions.count_roses(OneFragment.this.getActivity(), new Functions.VolleyCallback() {
            @Override
            public void onSuccess(int size) {
                sender_name.clear();sent_message.clear();sender_id.clear();which_rose_sent.clear();
                for (int i = 0; i < size; i++) {
                    final String sender = functions.sender_json[i];
                    final  String msg = functions.message_json[i];
                    final String rr = functions.red_rose_json[i];
                    if (functions.receiver_json[i].equals(functions.current_user_name)) {
                        if (functions.anonymous_json[i].equals("yes")) {
                            sender_name.add("Anonymous");
                            sent_message.add(functions.message_json[i]);
                            sender_id.add(null);
                            if (functions.red_rose_json[i].equals("1")) {
                                which_rose_sent.add(R.drawable.red_rose);
                            } else which_rose_sent.add(R.drawable.yellow_rose);
                            //sender_id.add();

                        } else {
                            functions.get_users(OneFragment.this.getContext(), new Functions.VolleyCallback() {
                                @Override
                                public void onSuccess(int size_users) {
                                    for (int j = 0; j < size_users; j++) {
                                        if (sender.equals(functions.users[j])) {
                                            sender_id.add(functions.id[j]);
                                        }
                                    }
                                    sender_name.add(sender);
                                    sent_message.add(msg);
                                    if (rr.equals("1")) {
                                        which_rose_sent.add(R.drawable.red_rose);
                                    } else which_rose_sent.add(R.drawable.yellow_rose);

                                }
                            });
                        }
                    }
                }
              //  Toast.makeText(OneFragment.this.getActivity(),sender_id.toString(),Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new LinearLayoutManager(OneFragment.this.getActivity()));
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(OneFragment.this.getActivity(), sender_name, sent_message, sender_id, which_rose_sent);
                recyclerView.setAdapter(adapter);


            }
        });



    }


}

/**int count_red_from_girls = 0;
 int count_yellow_from_girls =0;
 int count_red_from_boys = 0;
 int count_yellow_from_boys = 0;**/
//  functions.count_roses(OneFragment.this.getActivity(), new Functions.VolleyCallback() {
//     @Override
//    public void onSuccess(final int size_json) {
/**   for (int i = 0; i < size_json; i++) {
 if (functions.receiver_json[i].equals(functions.current_user_name)) {
 if(functions.anonymous_json[i].equals("yes")) {
 sender_name.add("anonymous");
 sent_message.add(functions.message_json[i]);
 profile_pic.add(R.mipmap.ic_launcher);
 }
 else{
 sender_name.add(functions.sender_json[i]);
 sent_message.add(functions.message_json[i]);
 profile_pic.add(R.mipmap.ic_launcher);
 }
 }
 }**/
/**   for (int i = 0; i < size_json; i++) {
 if (functions.receiver_json[i].equals(functions.current_user_name)) {
 if (functions.red_rose_json[i].equals("1")) {
 for (int j = 0; j < size; j++) {
 if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("female")) {
 count_red_from_girls++;
 } else if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("male")) {
 count_red_from_boys++;
 }
 }
 } else if (functions.yellow_rose_json[i].equals("1")) {
 for (int j = 0; j < size; j++) {
 if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("female")) {
 count_yellow_from_girls++;
 } else if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("male")) {
 count_yellow_from_boys++;
 }
 }
 }
 }
 }**/
// Toast.makeText(OneFragment.this.getActivity(), "" + count_yellow_from_boys, Toast.LENGTH_SHORT).show();
//});
//  count_yellow_from_boys=0;count_yellow_from_girls=0;count_red_from_boys=0;count_red_from_girls=0;