package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utils.MD5Utils;

/**
 * 主页面
 * Created by asus on 2016/2/28.
 */
public class HomeActivity extends Activity {
    private GridView gvHome;
    private String TAG = "Home";
    private String[] mItems = new String[]{"手机防盗", "通信卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

    private int[] mPics = new int[]{R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new HomeAdapter());
        //设置监听
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 8:
                        //设置中心
                        startActivity(new Intent(HomeActivity.this,
                                SettingActivity.class));
                        break;
                    case 0:
                        //手机防盗
                        showPasswordDialog();
                        break;

                }
            }
        });
    }

    /**
     * 显示密码弹窗
     */
    protected void showPasswordDialog() {
        //判断是否设置密码
        String savedPassword = mPref.getString("password", null);
        if (!TextUtils.isEmpty(savedPassword)) {
            showPasswordInputDialog();
        } else {
            //如果没有设置过，弹出设置密码的弹窗
            showPasswordSetDailog();
        }
    }

    /**
     * 輸入密碼彈窗
     */
    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dailog_input_password, null);
        //dialog.setView(view);//将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);//设置边距为0.在2.x版本上运行没问题

        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);


        Button btnOk = (Button) view.findViewById(R.id.bt_ok);
        Button btnCancel = (Button) view.findViewById(R.id.bt_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(password)) {
                    String savedPassword = mPref.getString("password", null);

                    if (MD5Utils.encode(password).equals(savedPassword)) {
                        Toast.makeText(HomeActivity.this, "登陸成功！",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //跳转到手机防盗页
                        startActivity(new Intent(HomeActivity.this,
                                LostFindActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "密碼錯誤！",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入框内容不能为空！",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//隐藏dialog
            }
        });
        dialog.show();
    }

    /**
     * 设置密码的弹窗
     */
    private void showPasswordSetDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dailog_set_password, null);
        //dialog.setView(view);//将自定义的布局文件设置给dialog
        dialog.setView(view, 0, 0, 0, 0);//设置边距为0.在2.x版本上运行没问题

        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfim = (EditText) view.findViewById(
                R.id.et_password_confirm
        );
        Button btnOk = (Button) view.findViewById(R.id.bt_ok);
        Button btnCancel = (Button) view.findViewById(R.id.bt_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfim = etPasswordConfim.getText().toString();

                if (!TextUtils.isEmpty(password) && !passwordConfim.isEmpty()) {
                    if (password.equals(passwordConfim)) {

                        Toast.makeText(HomeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        //将密码保存起来
                        mPref.edit()
                                .putString("password",
                                        MD5Utils.encode(password)).commit();


                        Log.i(TAG, "密碼是" + MD5Utils.encode(password));
                        dialog.dismiss();
                        //跳转到手机防盗页
                        startActivity(new Intent(HomeActivity.this,
                                LostFindActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致！",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入框内容不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//隐藏dialog
            }
        });
        dialog.show();
    }

    /**
     * 活要细啊！！！！
     */
  /*  @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_home);

        gvHome = (GridView)findViewById(R.id.gv_home);
       // gvHome.setAdapter(new HomeAdapter());


    }*/
    class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this,
                    R.layout.home_list_item, null);
            ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
            TextView tvItem = (TextView) view.findViewById(R.id.tv_item);

            tvItem.setText(mItems[position]);
            ivItem.setImageResource(mPics[position]);
            return view;
        }

    }
}
