package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import liuqiang.njupt.edu.mobliesafe.R;


/**
 * Created by asus on 2016/3/1.
 */

/**
 * 设置中心的自定义控件
 */
public class SettingItemView extends RelativeLayout {


    private TextView tvTitle;
    private CheckBox cbStaus;
    private TextView tvDesc;
    private String TAG = "SettingItemView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private String mDesc_0ff;
    private String mDesc_on;
    private String mTitle;

    public SettingItemView(Context context) {
        super(context);
        ininView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            Log.i(TAG, attributeName + "=" + attributeValue);
        }
        //根据属性名称获取属性的值
        mTitle = attrs.getAttributeValue(NAMESPACE, "siv_title");
        mDesc_on = attrs.getAttributeValue(NAMESPACE, "desc_on");
        mDesc_0ff = attrs.getAttributeValue(NAMESPACE, "desc_off");
        Log.i(TAG, mTitle + mDesc_0ff + mDesc_on);
        ininView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView();
    }

    /**
     * 初始化布局
     */
    private void ininView() {
        /**
         将自定义好的布局问件设置给当前的SettingItemView
         */
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStaus = (CheckBox) findViewById(R.id.cb_status);

        setTitle(mTitle);//设置标题
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }

    /**
     * 返回勾选状态
     *
     * @return
     */
    public boolean isChecked() {
        return cbStaus.isChecked();
    }

    public void setChecked(boolean check) {
        cbStaus.setChecked(check);
        //根据选择的状态,更新textview文本描述
        if (check) {
            setDesc(mDesc_on);
            Log.i(TAG, mDesc_on);
        } else {
            setDesc(mDesc_0ff);
            Log.i(TAG, mDesc_0ff);
        }
    }
}
