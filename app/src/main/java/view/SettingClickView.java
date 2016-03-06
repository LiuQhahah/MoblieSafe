package view;

import android.content.Context;
import android.util.AttributeSet;
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
public class SettingClickView extends RelativeLayout {


    private TextView tvTitle;
    private CheckBox cbStaus;
    private TextView tvDesc;
    private String TAG = "SettingClickView";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private String mTitle;

    public SettingClickView(Context context) {
        super(context);
        initView();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);


        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        /**
         将自定义好的布局问件设置给当前的SettingClickView
         */
        View.inflate(getContext(), R.layout.view_setting_click, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);


    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }


}
