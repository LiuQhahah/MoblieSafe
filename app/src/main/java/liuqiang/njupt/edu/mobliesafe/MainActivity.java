package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {
    private TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvVersion = (TextView)findViewById(R.id.tv_version);
        tvVersion.setText("版本号："+getVersionName());
    }

    private String getVersionName(){

        PackageManager packageManager = getPackageManager();
        try {
          PackageInfo packageInfo =
                  packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            System.out.println("versionName = "+versionName+";versionCode="+versionCode);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //没有找到包名的时候会走此异常
            e.printStackTrace();
            System.out.print("dd");
        }
        return  "";
    }

}
