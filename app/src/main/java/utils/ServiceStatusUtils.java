package utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by asus on 2016/3/6.
 */
public class ServiceStatusUtils  {
    private static final String TAG ="ServiceStatusUtils" ;

    /**
     * 檢查服務是否運行
     * @return
     */
    public static boolean isServiceRunning(Context context,String serviceName){

        ActivityManager am = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices =
                am.getRunningServices(100);//獲取系統所有運行的服務，最多一百個

        for (ActivityManager.RunningServiceInfo runningServiceInfo:runningServices){
            String className = runningServiceInfo.service.getClassName();//獲取服務名稱

            Log.i(TAG,className);

            if (className.equals(serviceName)){//服務存在

                return true;
            }
        }
        return false;
    }
}
