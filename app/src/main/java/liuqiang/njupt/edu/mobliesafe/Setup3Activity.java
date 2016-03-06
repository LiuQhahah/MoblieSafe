package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import utils.ToastUtils;

/**
 * Created by asus on 2016/3/2.
 */
public class Setup3Activity extends BaseSetupActivity {
    private static final String TAG = "Setup3Activity";
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        etPhone = (EditText) findViewById(R.id.et_phone);

        String phone =  mPref.getString("safe_phone","");
        etPhone.setText(phone);
    }

    /**
     * 展示上一頁,子類必須實現
     */
    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup2Activity.class));
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
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(this, "安全號碼不能為空！");
            return;
        }
        mPref.edit().putString("safe_phone",phone).commit();//保存安全號碼
            startActivity(new Intent(this, Setup4Activity.class));
            finish();

            // 两个界面切换的动画
            overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画

    }

    /**
     * 选择联系人
     */
    public void selectContact(View view) {

        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "resultCode為：" + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            String phone = data.getStringExtra("phone");
            phone = phone.replaceAll("-", "").replaceAll(" ", "");//替換-和空格

            etPhone.setText(phone);//把電話號碼設置給輸入框
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
