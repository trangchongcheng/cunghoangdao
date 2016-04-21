package cheng.com.android.cunghoangdao.ultils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 4/21/2016.
 */
public class CustomToast {

    @SuppressLint("SetTextI18n")
    public static void show(Activity context, String content){
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) context.findViewById(R.id.custom_toast_layout));
        TextView text = (TextView) layout.findViewById(R.id.custom_toast_message);
        text.setText(content+"");
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
