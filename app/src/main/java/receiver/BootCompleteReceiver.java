package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 监听手机启动的广播
 * Created by asus on 2016/3/2.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    private String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        //只有在防盜保護開啟的前提下，才進行sim卡判斷
        boolean protect = sp.getBoolean("protect", false);
        if (protect) {
            String sim = sp.getString("sim", null);
            if (!TextUtils.isEmpty(sim)) {
                //获取当前手机的SIM卡
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String currentSim = tm.getSimSerialNumber();//拿到當前手機的sim卡

                if (sim.equals(currentSim)) {
                    Log.i(TAG, "手机安全");
                } else {
                    Log.i(TAG, "sim卡已经变化，发送报警短信！！！！");
                    String phone = sp.getString("safe_phone","");//讀取安全號碼
                    //發送短信給安全號碼
                    SmsManager smsManager =SmsManager.getDefault();
                    smsManager.sendTextMessage(phone,null,"SIM card changed",
                            null,null);
                }
            }
        }
    }
}
