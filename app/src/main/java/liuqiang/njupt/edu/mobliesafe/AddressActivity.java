package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import db.dao.AddressDao;

/**
 * 归属地查询页面
 * Created by asus on 2016/3/6.
 */
public class AddressActivity extends Activity {
    private static final String TAG = "AddessActivity";
    private EditText etNumber;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        etNumber = (EditText) findViewById(R.id.et_number);
        tvResult = (TextView) findViewById(R.id.tv_result);

        //监听EDITTEXT的变化
        etNumber.addTextChangedListener(new TextWatcher() {
            //变化前回调
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //文字发生变化时回调
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = AddressDao.getAddress(s.toString());
                tvResult.setText(address);
            }

            //变化结束后的回调
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 查询
     *
     * @param view
     */

    public void query(View view) {
        String number = etNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(number)) {
            Log.i(TAG, "号码为：" + number);
            String address = AddressDao.getAddress(number);
            tvResult.setText(address);
        } else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//            shake.setInterpolator(new Interpolator() {
//                @Override
//                public float getInterpolation(float x) {
//                    y = a*x+b;
//                    int y = 0;
//                    return y;
//                }
//            });
            etNumber.startAnimation(shake);
            vibrate();
        }
    }

    /**
     * 手机振动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(2000);震动两秒
        vibrator.vibrate(new long[]{1000,2000,3000},-1);//隔一秒震动一次， 参2：-1意味只执行一次，不循环

    }
}
