package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2016/2/29.
 */

/**
 * 获取焦点的textview
 */
public class FousedTextView extends TextView {

    //用代码new对象时走此方法
    public FousedTextView(Context context) {
        super(context);

    }
    //有属性时走此方法
    public FousedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //有style样式的话会走此方法
    public FousedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 表示有没有获取焦点
     * 跑马灯要运行，首先调用此函数判断是否有焦点，是true表示跑马灯才会有效果
     * 所以强制返回true，所以我们不管实际上textview有没有焦点，我们都强制返回true,让跑马灯认为有焦点
     * @return
     */
    @Override
    public boolean isFocused() {

        return true;
    }
}
