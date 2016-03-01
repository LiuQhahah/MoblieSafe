package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.StreamUtils;


public class SplashActivity extends Activity {
    private TextView tvVersion;
    private final String TAG = "Splash";
    //版本信息
    private String mVersionName;//版本名
    private int mVersionCode;//版本号
    private String mDesc;//版本描述
    private String mDownloadUrl;//下载地址
    protected static final int CODE_UPDATE_DIALOG = 0;
    protected static final int  CODE_URL_ERROR = 1;
    protected static final int CODE_NET_ERROR = 2;
    protected static final int CODE_JSON_ERROR = 3;
    protected static final int CODE_ENTER_HOME = 4;//进入主界面
    private TextView tvProgress;//下载进度展示
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case CODE_UPDATE_DIALOG:
                   showUpdateDailog();
                   break;
               case CODE_URL_ERROR:
                   Toast.makeText(SplashActivity.this,"url错误",Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_NET_ERROR:
                   Toast.makeText(SplashActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_JSON_ERROR:
                   Toast.makeText(SplashActivity.this,"数据解析错误",Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_ENTER_HOME:
                   enterHome();
                   break;
               default:
                   break;
           }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvVersion = (TextView)findViewById(R.id.tv_version);
        tvVersion.setText("版本号："+getVersionCode());
        tvProgress = (TextView)findViewById(R.id.tv_progress);//默认隐藏
        //调用更新版本
        checkVersion();
    }

    /**
     * 获取本地app版本号
     * @return
     */
    private int getVersionCode(){

        PackageManager packageManager = getPackageManager();
        try {
          PackageInfo packageInfo =
                  packageManager.getPackageInfo(getPackageName(), 0);//获取包的信息

            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //没有找到包名的时候会走此异常
            e.printStackTrace();
        }
        return  -1;
    }

    /**
     * 从服务器获取版本信息进行校验
     */
    private void checkVersion() {

        final long startTime = System.currentTimeMillis();
        new Thread(){
            @Override
            public void run() {
                Message msg  = Message.obtain();
                HttpURLConnection connection = null;
                try{
                    //本机地址用localhost ,但是如果用模拟器加载本机的地址时，可以用ip (10.0.2.2)来替换
                    URL url = new URL("http://192.168.1.102/update.json");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");//设置请求方法
                    connection.setConnectTimeout(5000);//设置连接超时
                    connection.setReadTimeout(5000);//设置响应超时，连接上，但服务器迟迟不给响应
                    connection.connect();//连接服务器

                    int  responseCode =  connection.getResponseCode();//获取响应码

                    Log.i(TAG,"网络返回：" + responseCode);
                    if(responseCode==200){
                        InputStream inputStream = connection.getInputStream();
                        //调用StreamUtils.readFromStream的方法
                        String result = StreamUtils.readFromStream(inputStream);
                        Log.i(TAG,"网络返回：" + result);
                        System.out.println("网络返回：" + result);
                        //解析json
                        JSONObject jo = new JSONObject(result);

                        mVersionName = jo.getString("versionName");
                        mVersionCode = jo.getInt("versionCode");
                        mDesc = jo.getString("description");
                        mDownloadUrl = jo.getString("downloadUrl");

                        Log.i(TAG,"版本名："+mVersionName+
                                "版本号："+mVersionCode+
                                "版本描述："+mDesc+
                                "版本描述："+mDownloadUrl);
                        if(mVersionCode>getVersionCode()){
                            //判断是否有更新
                            //服务器的versionCode 大于本地versionCode
                            //说明有更新，弹出升级对话框
                            msg.what = CODE_UPDATE_DIALOG;
                        }else {
                            //没有版本更新
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                }catch (MalformedURLException e){
                    //url错误异常
                    e.printStackTrace();
                    msg.what=CODE_URL_ERROR;
                }catch (IOException e){
                    //网络异常
                    e.printStackTrace();
                    msg.what=CODE_NET_ERROR;
                }catch (JSONException e){
                    //json解析失败
                    e.printStackTrace();
                    msg.what=CODE_JSON_ERROR;
                }finally {
                    long endTime = java.lang.System.currentTimeMillis();

                    long timeUsed = endTime-startTime;//访问网络花费时间
                    if (timeUsed<2000){
                        //强制休眠一段时间，保证闪屏页展示两秒钟
                        try {
                            Thread.sleep(2000-timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                    //在finial中关闭网络连接
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
            }.start();
        }

    /**
     * 升级对话框
     */
    protected void showUpdateDailog(){
        /**是 this 而不是getApplicationContext(),
         * 子类拥有父类的所有方法，而且有更多的方法
         * 平时，要获取context对象的话，优先选择Activity,避免bug出现尽量不用getApplicationContext()
         * 说明activity是子类拥有的方法比getApplicationContext()要多
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本：" + mVersionName);
        builder.setMessage(mDesc);
        //builder.setCancelable(false);//不让用户点返回,用户体验太差
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "立即更新");
                download();
            }
        });
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterHome();
                    }
                }
        );
        //设置取消监听,点击返回键时触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 下载apk文件
     */
    protected void download(){
        //XUtils
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

        tvProgress.setVisibility(View.VISIBLE);//显示进度

        String target = Environment.getExternalStorageDirectory()+"/update.apk";
        HttpUtils utils = new HttpUtils();
        utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
            //文件下载进度
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                Log.i(TAG,"下载进度："+current+"/"+total);
                tvProgress.setText("下载进度："+current*100/total+"%");
            }

            //下载成功
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {

                Log.i(TAG, "下载成功");
                //跳转到系统界面
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(responseInfo.result),
                        "application/vnd.android.package-archive");
                //startActivity(intent);
                startActivityForResult(intent,0);
            }
            //下载失败
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SplashActivity.this,"下载失败！",Toast.LENGTH_SHORT).show();
            }
        });}else {
            Toast.makeText(SplashActivity.this,"没有sd卡",Toast.LENGTH_SHORT).show();
        }
    }
    //如果用户取消安装的话，会返回结果,回调方法onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 进入主界面
     */
    private void enterHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
