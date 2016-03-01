package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by asus on 2016/3/1.
 */

/**
 * 手机防盗页面
 */
public class LostFindActivity extends Activity {

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getSharedPreferences("config",MODE_PRIVATE);

        boolean configed = mPrefs.getBoolean("config",false);//判断是否进入过设置向导

        if (configed){
            setContentView(R.layout.activity_lost_find);
        }else {
            //跳转设置向导页
            startActivity(new Intent(LostFindActivity.this,Setup1Activity.class));
            finish();
        }

    }
}
