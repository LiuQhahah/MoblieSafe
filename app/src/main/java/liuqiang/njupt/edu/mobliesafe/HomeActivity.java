package liuqiang.njupt.edu.mobliesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**主页面
 * Created by asus on 2016/2/28.
 */
public class HomeActivity extends Activity {
    private GridView gvHome;

    private String[] mItems= new String[]{"手机防盗","通信卫士","软件管理",
    "进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};

    private int[] mPics = new int[]{R.mipmap.home_safe,R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager,R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize,R.mipmap.home_tools, R.mipmap.home_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvHome = (GridView)findViewById(R.id.gv_home);
        gvHome.setAdapter(new HomeAdapter());
    }

    /**
     *活要细啊！！！！
     */
  /*  @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_home);

        gvHome = (GridView)findViewById(R.id.gv_home);
       // gvHome.setAdapter(new HomeAdapter());


    }*/
    class HomeAdapter extends BaseAdapter{
        @Override
        public int getCount() {

            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this,
                    R.layout.home_list_item,null);
            ImageView ivItem = (ImageView)view.findViewById(R.id.iv_item);
            TextView tvItem = (TextView)view.findViewById(R.id.tv_item);

            tvItem.setText(mItems[position]);
            ivItem.setImageResource(mPics[position]);
            return view;
        }

    }
}
