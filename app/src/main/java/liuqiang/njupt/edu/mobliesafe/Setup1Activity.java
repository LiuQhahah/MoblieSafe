package liuqiang.njupt.edu.mobliesafe;

import android.content.Intent;
import android.os.Bundle;

/**
 * 第一个设置向导页
 * Created by asus on 2016/3/1.
 */
public class Setup1Activity extends BaseSetupActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup1);

    }

    /**
     * 展示上一頁,子類必須實現
     */
    @Override
    public void showPreviousPage() {

    }

    /**
     * 展示下一頁
     */
    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();

        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画

    }
}
