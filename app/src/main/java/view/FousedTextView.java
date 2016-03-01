package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2016/2/29.
 */

/**
 * ��ȡ�����textview
 */
public class FousedTextView extends TextView {

    //�ô���new����ʱ�ߴ˷���
    public FousedTextView(Context context) {
        super(context);

    }
    //������ʱ�ߴ˷���
    public FousedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //��style��ʽ�Ļ����ߴ˷���
    public FousedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * ��ʾ��û�л�ȡ����
     * �����Ҫ���У����ȵ��ô˺����ж��Ƿ��н��㣬��true��ʾ����ƲŻ���Ч��
     * ����ǿ�Ʒ���true���������ǲ���ʵ����textview��û�н��㣬���Ƕ�ǿ�Ʒ���true,���������Ϊ�н���
     * @return
     */
    @Override
    public boolean isFocused() {

        return true;
    }
}
