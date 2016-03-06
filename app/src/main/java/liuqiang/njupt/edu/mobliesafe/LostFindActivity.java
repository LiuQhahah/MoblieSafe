package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asus on 2016/3/1.
 */

/**
 * 手机防盗页面
 */
public class LostFindActivity extends Activity {

    private SharedPreferences mPrefs;

    private TextView tvSafePhone;
    private ImageView ivProtect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getSharedPreferences("config", MODE_PRIVATE);

        boolean configed = mPrefs.getBoolean("configed", false);// 判断是否进入过设置向导

        if (configed) {
            setContentView(R.layout.activity_lost_find);
            //根據sp更新安全號碼
            tvSafePhone = (TextView) findViewById(R.id.tv_safe_phone);
            String phone = mPrefs.getString("safe_phone", "");
            tvSafePhone.setText(phone);

            //根據sp更新保護鎖
            ivProtect = (ImageView) findViewById(R.id.iv_protect);
            boolean protect = mPrefs.getBoolean("protect", false);
            if (protect) {
                ivProtect.setImageResource(R.mipmap.lock);
            } else {
                ivProtect.setImageResource(R.mipmap.unlock);
            }
        } else {
            //跳转设置向导页
            startActivity(new Intent(LostFindActivity.this, Setup1Activity.class));
            finish();
        }

    }

    /**
     * 重新进入设置向导
     *
     * @param view
     */
    public void reEnter(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}
