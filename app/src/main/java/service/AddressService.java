package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import db.dao.AddressDao;
import liuqiang.njupt.edu.mobliesafe.R;

/**
 * Created by asus on 2016/3/6.
 */
public class AddressService extends Service {
    private static final String TAG = "AddressService";
    private MyListener listener;
    private TelephonyManager tm;
    private OutCallReceiver receiver;
    private WindowManager mWM;
    private View view;
    private SharedPreferences mPref;

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     * 来电提醒
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电的状态

        receiver = new OutCallReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receiver, filter);//動態註冊廣播
    }

    class MyListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://电话铃声响了
                    Log.i(TAG, "电话铃响。。。");
                    String address = AddressDao.getAddress(incomingNumber);//根据来电号码查询归属地
                    //Toast.makeText(AddressService.this, address, Toast.LENGTH_SHORT).show();
                    showToast(address);
                    break;
                case TelephonyManager.CALL_STATE_IDLE://电话闲置状态,取消显示
                    // Log.i(TAG, "电话闲置:CALL_STATE_IDLE");
                    if (mWM != null && view != null) {
                        mWM.removeView(view);
                        view = null;
                    }
                    break;
                default:
                    Log.i(TAG, "电话闲置:CALL_STATE_IDLE");
                    if (mWM != null && view != null) {
                        mWM.removeView(view);
                        view = null;
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    /**
     * 监听去电的广播接受者 需要权限: android.permission.PROCESS_OUTGOING_CALLS
     *
     * @author Kevin
     */
    class OutCallReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String number = getResultData();//獲取去電電話號碼
            String address = AddressDao.getAddress(number);
            // Toast.makeText(context, address, Toast.LENGTH_LONG).show();
            showToast(address);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);//停止來電監聽
        unregisterReceiver(receiver);//註銷廣播
    }

    /**
     * 自定义归属地浮窗
     */
    private void showToast(String text) {
        mWM = (WindowManager) this.
                getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;

        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");

        //view = new TextView(this);
        view = View.inflate(this, R.layout.toast_address, null);


        int[] bgs = new int[]{R.drawable.call_locate_white, R.drawable.call_locate_orange,
                R.drawable.call_locate_gray, R.drawable.call_locate_green, R.drawable.call_locate_blue};

        int style = mPref.getInt("address_style", 0);//读取保存的style

        view.setBackgroundResource(bgs[style]);//根据存储style样式更新背景
        TextView tvText = (TextView) view.findViewById(R.id.tv_number);

        tvText.setText(text);

        mWM.addView(view, params);//将view添加在屏幕上（window）

    }
}
