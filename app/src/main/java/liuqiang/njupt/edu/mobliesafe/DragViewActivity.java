package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 修改归属地显示位置
 * Created by asus on 2016/3/6.
 */
public class DragViewActivity extends Activity {
    private static final String TAG = "DragViewActivity";
    private TextView tvTop;
    private TextView tvBottom;


    private ImageView ivDrag;
    private int startX;
    private int startY;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        tvTop = (TextView) findViewById(R.id.tv_top);
        tvBottom = (TextView) findViewById(R.id.tv_bottom);
        ivDrag = (ImageView) findViewById(R.id.iv_drag);

        int lastX = mPref.getInt("lastX", 0);
        int lastY = mPref.getInt("lastY", 0);
        //onMeasure(测量) ,onLayout（安放位置） ,onDraw（绘制）
//        ivDrag.layout(lastX, lastY, lastX + ivDrag.getWidth(),
//                lastY + ivDrag.getWidth());//不能用这个方法，因为还没有测量完成，就不能安放位置

        //获取布局对象
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                ivDrag.getLayoutParams();
        layoutParams.leftMargin = lastX;//设置左边距
        layoutParams.topMargin = lastY;//设置top边距

        Log.i(TAG,"移动的坐标为："+lastX+","+lastY);
        ivDrag.setLayoutParams(layoutParams);


        ivDrag.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        //Log.i(TAG, "移动前坐标x为：" + startX);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawX();

                        //计算移动偏移量
                        int dx = endX - startX;
                        int dy = endY - startY;

                        int l = ivDrag.getLeft() -50;
                        int r = ivDrag.getRight() - 50;

                        int t = ivDrag.getTop() - 50;
                        int b = ivDrag.getBottom() - 50;

                        //更新界面
                        ivDrag.layout(l, t, r, b);

                        //重新初始化起点坐标
                        startX = (int) event.getRawX();
                        //Log.i(TAG, "移动后坐标x为：" + startX);
                        startY = (int) event.getRawY();
                    case MotionEvent.ACTION_UP:
                        //记录坐标点
                        SharedPreferences.Editor edit = mPref.edit();
                        edit.putInt("lastX", ivDrag.getLeft());
                        edit.putInt("lastY", ivDrag.getTop());
                        edit.commit();
                        break;
                    default:

                        break;

                }
                return true;
            }
        });
    }
}
