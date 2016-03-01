package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import view.SettingItemView;

/**
 * Created by asus on 2016/3/1.
 */
public class SettingActivity extends Activity {

    private SettingItemView sivUpdate;//设置升级
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mPref = getSharedPreferences("config", MODE_PRIVATE);

        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        //sivUpdate.setTitle("自动更新设置");

        //默认情况开启自动更新
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if (autoUpdate) {
            // sivUpdate.setDesc("自动更新开启");
            sivUpdate.setChecked(true);
        } else {
            //sivUpdate.setDesc("自动更新关闭");
            sivUpdate.setChecked(false);
        }

        //sivUpdate.setDesc("自动更新已开启");
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前的勾选状态
                if (sivUpdate.isChecked()) {
                    //设置不勾选
                    sivUpdate.setChecked(false);
                    //      sivUpdate.setDesc("自动更新已关闭");
                    //更新sharePreferences,保存数据，下次启动调用数据
                    mPref.edit().putBoolean("auto_update", false).commit();
                } else {
                    sivUpdate.setChecked(true);
                    //    sivUpdate.setDesc("自动更新已开启");
                    mPref.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }
}
