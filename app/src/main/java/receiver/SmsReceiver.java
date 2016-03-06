package receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import liuqiang.njupt.edu.mobliesafe.R;
import service.LocationService;

/**
 * Created by asus on 2016/3/3.
 */
public class SmsReceiver extends BroadcastReceiver {


    private static final String TAG = "SmsReceiver";
    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objects = (Object[]) intent.getExtras().get("pdus");

        for (Object object : objects) {//短信最多140字节，超出的话，会分多条短信发送，所以是一个数组
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);

            String originatingAddress = message.getOriginatingAddress();//短信的来源号码
            String messageBody = message.getMessageBody();//短信内容

            Log.i(TAG, originatingAddress + ":" + messageBody);

            if ("#*alarm*#".equals(messageBody)) {
                //播放报警音乐
                MediaPlayer player = MediaPlayer.create(context, R.raw.background);
                player.setVolume(1f, 1f);
                player.setLooping(true);
                player.start();
                abortBroadcast();//中断短信的传递，从而系统短信app就收不到内容了
            } else if ("#*location*#".equals(messageBody)) {
                //或取经纬度

                context.startService(new Intent(context, LocationService.class));//开启定位服务

                SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                String location = sp.getString("location", "");
                Log.i(TAG, "location:" + location);

                abortBroadcast();//中断短信的传递，从而系统短信app就收不到内容了
            } else if ("#*wipedate*#".equals(messageBody)) {
//                if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
//                    mDPM.wipeData(0);// 清除数据,恢复出厂设置
//                } else {
//                    Toast.makeText(context, "必须先激活设备管理器!", Toast.LENGTH_SHORT).show();
//                }
                abortBroadcast();//中断短信的传递，从而系统短信app就收不到内容了
            } else if ("#*lockscreen*#".equals(messageBody)) {

                mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);// 获取设备策略服务
                mDeviceAdminSample = new ComponentName(context, AdminReceiver.class);// 设备管理组件

                Intent sup_DPM = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                sup_DPM.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        mDeviceAdminSample);
                sup_DPM.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "哈哈哈, 我们有了超级设备管理器, 好NB!");
                context.startActivity(sup_DPM);

                if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
                    mDPM.lockNow();// 立即锁屏
                    mDPM.resetPassword("", 0);
                } else {
                    Toast.makeText(context, "必须先激活设备管理器!", Toast.LENGTH_SHORT).show();
                }
                abortBroadcast();//中断短信的传递，从而系统短信app就收不到内容了
            }
        }
    }
}
