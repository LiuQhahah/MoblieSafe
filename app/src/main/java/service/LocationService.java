package service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by asus on 2016/3/5.
 */
public class LocationService extends Service {
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
     * as given to {@link Context#bindService
     * Context.bindService}.  Note that any extras that were included with
     * the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */

    private LocationManager lm;
    private MyLocationListener listener;
    private SharedPreferences mPref;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//是否允许付费，比如使用流量。
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = lm.getBestProvider(criteria, true);//获取最佳位置提供者
        listener = new MyLocationListener();
        lm.requestLocationUpdates(bestProvider, 0, 0, listener);// 参1表示位置提供者,参2表示最短更新时间,参3表示最短更新距离

    }

    class MyLocationListener implements LocationListener {

        // 位置发生变化
        @Override
        public void onLocationChanged(Location location) {
          //将获取的经纬度保存在sp中

            mPref.edit().putString("location", "j:" + location.getLongitude() + ";w" +
                    location.getLatitude()).commit();

            stopSelf();//停掉service

        }

        // 位置提供者状态发生变化
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("onStatusChanged");
        }

        // 用户打开gps
        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("onProviderEnabled");
        }

        // 用户关闭gps
        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("onProviderDisabled");
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(listener);// 当activity销毁时,停止更新位置, 节省电量
    }
}
