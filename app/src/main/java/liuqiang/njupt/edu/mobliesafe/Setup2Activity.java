package liuqiang.njupt.edu.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import utils.ToastUtils;
import view.SettingItemView;

/**
 * 第2个设置向导页
 *
 * @author Kevin
 */
public class Setup2Activity extends BaseSetupActivity {
    private String TAG = "Setup2Activity";

    private SettingItemView sivSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);


        sivSim = (SettingItemView) findViewById(R.id.siv_sim);
        String sim = mPref.getString("sim", null);
        if (!TextUtils.isEmpty(sim)) {
            sivSim.setChecked(true);
        } else {
            sivSim.setChecked(false);
        }
        sivSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivSim.isChecked()) {

                    sivSim.setChecked(false);
                    mPref.edit().remove("sim").commit();//删除已绑定的SIM卡
                } else {
                    sivSim.setChecked(true);
                    //保存SIM卡的信息
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String simSerialNumber = tm.getSimSerialNumber();//获取sim卡序列号
                    Log.i(TAG, "sim卡序列号" + simSerialNumber);

                    mPref.edit().putString("sim", simSerialNumber).commit();//将SIM卡序列号保存在sp中
                }
            }
        });
    }

    /**
     * 展示上一頁,子類必須實現
     */
    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();

        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_previous_in,
                R.anim.tran_previous_out);// 进入动画和退出动画
    }

    /**
     * 展示下一頁
     */
    @Override
    public void showNextPage() {
        //如果SIM卡麼有綁定就不允許進入下一個頁面
        String sim = mPref.getString("sim",null);
        if (TextUtils.isEmpty(sim)){
            ToastUtils.showToast(this,"必須綁定sim卡！");
            return;
        }

        startActivity(new Intent(this, Setup3Activity.class));
        finish();

        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画

    }
}


