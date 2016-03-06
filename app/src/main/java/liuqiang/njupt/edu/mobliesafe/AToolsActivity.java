package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 高级工具
 * Created by asus on 2016/3/6.
 */
public class AToolsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_atools);

    }

    /**
     *
     * @param view
     */
    public void numberAddressQuery(View view){

        startActivity(new Intent(this,AddressActivity.class));
    }
}
