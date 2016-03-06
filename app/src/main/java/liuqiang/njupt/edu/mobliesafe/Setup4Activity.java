package liuqiang.njupt.edu.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by asus on 2016/3/2.
 */
public class Setup4Activity extends BaseSetupActivity {

    private CheckBox cbProtect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        cbProtect = (CheckBox)findViewById(R.id.cb_protect);

        boolean protect =  mPref.getBoolean("protect",false);

        //根據sp保存的狀態更新checkbox狀態
        if (protect){
            cbProtect.setText("防盜保護已經開啟");
            cbProtect.setChecked(true);
        }else {
            cbProtect.setText("防盜保護沒有開啟");
            cbProtect.setChecked(false);
        }
        //當checkBox發生變化時，回調此方法
        cbProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbProtect.setText("防盜保護已經開啟");
                    mPref.edit().putBoolean("protect",true).commit();
                }else {
                    cbProtect.setText("防盜保護沒有開啟");
                    mPref.edit().putBoolean("protect",false).commit();
                }
            }
        });
    }


    /**
     * 展示上一頁,子類必須實現
     */
    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
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
        startActivity(new Intent(this, LostFindActivity.class));
        finish();

        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画

        mPref.edit().putBoolean("configed", true).commit();// 更新sp,表示已经展示过设置向导了,下次进来就不展示啦

    }


}
