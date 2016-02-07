package verkstad.org.in.valentineapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by anu on 1/19/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<String> sender_names,sent_messages,sender_image;
    ArrayList<Integer> which_rose_sent;
    public RecyclerViewAdapter(Context context, ArrayList<String> sender_names, ArrayList<String> sent_messages, ArrayList<String> sender_image, ArrayList<Integer> which_rose_sent){
        inflater=LayoutInflater.from(context);
        this.sender_names=sender_names;
        this.sent_messages=sent_messages;
        this.sender_image=sender_image;
        this.which_rose_sent=which_rose_sent;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.senders_list_row,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String sender_name = sender_names.get(i);
        String sent_message = sent_messages.get(i);
        String profile_pic_id=sender_image.get(i);
        int which_rose = which_rose_sent.get(i);
        viewHolder.sender_name.setText(sender_name);
        viewHolder.sent_message.setText(sent_message);
        viewHolder.senders_image.setProfileId(profile_pic_id);
        viewHolder.which_rose_sent.setImageResource(which_rose);

    }

    @Override
    public int getItemCount() {
        return sender_names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView sender_name;
        TextView sent_message;
        ProfilePictureView senders_image;
        ImageView which_rose_sent;
        public ViewHolder(View itemView) {
            super(itemView);
            sender_name= (TextView) itemView.findViewById(R.id.sender_name);
            sent_message= (TextView) itemView.findViewById(R.id.sent_message);
            senders_image= (ProfilePictureView) itemView.findViewById(R.id.senders_image);
            which_rose_sent= (ImageView) itemView.findViewById(R.id.which_rose_sent);
        }
    }
}