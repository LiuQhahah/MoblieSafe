package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by asus on 2016/3/3.
 */
public class ToastUtils {
    public static void showToast(Context ctx,String text){
        Toast.makeText(ctx,text,Toast.LENGTH_SHORT).show();
    }
}
