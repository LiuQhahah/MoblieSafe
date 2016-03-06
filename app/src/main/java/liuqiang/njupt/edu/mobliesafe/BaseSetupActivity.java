package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 設置引導頁基類，不要要在清單文件中註冊，因為不需要界面展示
 * Created by asus on 2016/3/2.
 */
public abstract class BaseSetupActivity extends Activity {
    private GestureDetector mDetector;
    public SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }
            //監聽滑動事件

            /**
             *
             * @param e1：表示滑動起始點
             * @param e2：表示滑動終點
             * @param velocityX：表示水平速度
             * @param velocityY：表示垂直速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判斷縱向滑動是否過大，過大的話，不允許切換界面
                if (Math.abs(e2.getRawY() - e1.getY()) > 100) {
                    Toast.makeText(BaseSetupActivity.this, "不能這樣劃呦", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //判斷滑動是否過慢
                if (Math.abs(velocityX) < 100) {
                    Toast.makeText(BaseSetupActivity.this, "滑動太慢", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //向右滑，上一頁
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showPreviousPage();

                    return true;
                }
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 展示上一頁,子類必須實現
     */
    public abstract void showPreviousPage();

    /**
     * 展示下一頁
     */
    public abstract void showNextPage();

    // 下一页
    public void next(View view) {
        showNextPage();
    }

    // 上一页
    public void previous(View view) {
        showPreviousPage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);//委託手勢識別器處理觸摸事件
        return super.onTouchEvent(event);
    }
}
