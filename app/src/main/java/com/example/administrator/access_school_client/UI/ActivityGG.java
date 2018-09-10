package com.example.administrator.access_school_client.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

public class ActivityGG extends AppCompatActivity {

    private String[] date = new String[]{"","2018-9-14","2018-09-12","2018-09-10","2018-09-09","2018-09-08","2018-04-09"};
    private String[] title1 = new String[]{"关于加强校园安全管理的通告",
            "关于国庆长假的通知",
            "开学期间网络维护通知",
            "停水通知",
            "",
            "关于成教学院办公地点搬迁的通告"};
    private String[] content1 = new String[]{"1.加强校门管理，严格执行门卫登记制度，认真核实进校人员的身份；2.校外因公因进校的人员须事先联系好被访人员，并经门卫核实方可进入校；3.校园内的公共财产一律不得破坏，如有则按登记价格两倍进行赔偿；",
            "国庆长假即将来临，按照国家规定，结合我校实际情况，通知如下：1.放假时间：10月1日至10月7日，请同学们离校前做好登记。",
            "开学了，为了更好的网络提供学习，特此通知近日开始网络维护，请大家做好准备。",
            "因某些原因，近日停水两天",
            "",
            "    因学校发展需要，成教学院现已搬迁至宁波大红鹰学院集团大楼办公（宁波市海曙区学院路899号）。因搬迁给大家带来的不便，敬请谅解！\\n\" +\n" +
                    "            \"       办公室一：集团大楼211-1 电话：88054391\\n\" +\n" +
                    "            \"       办公室二：集团大楼211-2 电话：88054871\\n\" +\n" +
                    "            \"       办公室三：集团大楼209   电话：88054293\\n\" +\n" +
                    "            \"       报名咨询办公室：3号楼101 电话：87328973、87306739\\n\" +\n" +
                    "            \"       特此通告。"};
    private int[] pictures = new int[]{R.drawable.schoolsafe,R.drawable.guoqing,R.drawable.net,R.drawable.waterstop,R.drawable.zhisu,R.drawable.zhisu};
    ImageView picture;
    TextView title;
    TextView content;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        picture = findViewById(R.id.iv_details2);
        time = findViewById(R.id.time_detials2);
        title = findViewById(R.id.title_detials2);
        content = findViewById(R.id.content_detials2);
//        int position = Integer.parseInt(getIntent().getStringExtra("position"));
        Bundle bundle = getIntent().getBundleExtra("data");
        int position = bundle.getInt("position");

        Log.e("position",position+"");
        picture.setImageResource(pictures[position]);
        time.setText(date[position]);
        title.setText(title1[position]);
        content.setText(content1[position]);

    }
}
