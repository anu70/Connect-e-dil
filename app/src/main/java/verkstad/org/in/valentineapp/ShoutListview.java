package verkstad.org.in.valentineapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by coder on 1/16/2016.
 */
public class ShoutListview extends BaseAdapter {
    private Activity activity;

   Bitmap bitmaps;
    ProfilePictureView profilePictureView;
    private List<Shout> Shoutitems;
    private LayoutInflater inflater;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public ShoutListview(Activity activity,List<Shout> Shoutitems){
      this.activity=activity;
        this.Shoutitems=Shoutitems;
    }




    @Override
    public int getCount() {
        return Shoutitems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        TextView name=(TextView)convertView.findViewById(R.id.name);
        TextView message=(TextView)convertView.findViewById(R.id.message);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        //ImageView profile=(ImageView)convertView.findViewById(R.id.list_image);
        profilePictureView = (ProfilePictureView)convertView.findViewById(R.id.image);

        Shout s=Shoutitems.get(position);
        //String timeAgo=getTimeAgo(s.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultdate = new Date(s.getTime());
        name.setText(s.getName());
        message.setText(s.getMessage());
      /**  try {
             bitmaps= getFacebookProfilePicture("1683072511916372");

            //bitmapr=getResizedBitmap(bitmaps,30,30);
            //profile.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();


        } **/
        // Converting timestamp into x ago format

        time.setText(sdf.format(resultdate));

        profilePictureView.setProfileId(s.getId());
        //ThreeFragment obj=new ThreeFragment();
        //obj.CallToFunction(bitmaps);

        //profile.setImageBitmap(bitmap);

        return convertView;
    }
}
