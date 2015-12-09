package mobileprogramming.koreatech.together.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mobileprogramming.koreatech.together.R;

/**
 * Created by user on 2015-12-08.
 */
public class ProfileView {

    private String department;
    private String name;
    private String image_url;

    public Context context;

    public LayoutInflater layoutInflater;

    public ProfileView(Context context, String department, String name, String image_url){
        this.context = context;
        this.department = department;
        this.name = name;
        this.image_url = image_url;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Bitmap getBitmapFromURL(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public LinearLayout getLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.profile_layout, parent, false);

        ImageView profile = (ImageView) res_layout.findViewById(R.id.profile_image);
        TextView department = (TextView) res_layout.findViewById(R.id.department);
        TextView name = (TextView) res_layout.findViewById(R.id.name);

        if(image_url.length() > 0) {
            Bitmap bitmap = getBitmapFromURL(this.image_url);
            profile.setImageBitmap(bitmap);
        }


        department.setText(this.department);
        name.setText(this.name);

        return res_layout;
    }

    public LinearLayout getSmallLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.small_profile_layout, parent, false);

        ImageView profile = (ImageView) res_layout.findViewById(R.id.profile_image);
        TextView name = (TextView) res_layout.findViewById(R.id.name);

        if(image_url.length() > 0) {
            Bitmap bitmap = getBitmapFromURL(this.image_url);
            profile.setImageBitmap(bitmap);
        }

        name.setText(this.name);

        return res_layout;
    }

    public LinearLayout getListLayout(ViewGroup parent){
        LinearLayout res_layout = (LinearLayout) layoutInflater.inflate(R.layout.profile_list_layout, parent, false);

        ImageView profile = (ImageView) res_layout.findViewById(R.id.profile_image);
        TextView department = (TextView) res_layout.findViewById(R.id.department);
        TextView name = (TextView) res_layout.findViewById(R.id.name);

        if(image_url.length() > 0) {
            Bitmap bitmap = getBitmapFromURL(this.image_url);
            profile.setImageBitmap(bitmap);
        }

        Log.d("TAG", "department : " + this.department);

        //department.setText(this.department);
        name.setText(this.name);

        return res_layout;
    }
}
