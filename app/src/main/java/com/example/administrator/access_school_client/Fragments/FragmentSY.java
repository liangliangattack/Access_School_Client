package com.example.administrator.access_school_client.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.access_school_client.H5tmlWebView.WebViewActivity;
import com.example.administrator.access_school_client.MainActivity;
import com.example.administrator.access_school_client.Model.BannerModel;
import com.example.administrator.access_school_client.Service.AudioService;
import com.example.administrator.access_school_client.UI.ActivityGG;
import com.example.administrator.access_school_client.UI.AudioActivity;
import com.example.administrator.access_school_client.UI.CarQuesActivity;
import com.example.administrator.access_school_client.UI.FastActivity;
import com.example.administrator.access_school_client.UI.Fragmentfour;
import com.example.administrator.access_school_client.UI.Fragmentnews;
import com.example.administrator.access_school_client.UI.SchoolActivitiesFrament;
import com.example.administrator.access_school_client.UI.TransImgAct;
import com.example.administrator.access_school_client.bean.Fast;
import com.example.administrator.access_school_client.bean.HistoryEvent;
import com.example.administrator.access_school_client.xmarqueeview.XMarqueeView;
import com.example.administrator.access_school_client.xmarqueeview.XMarqueeViewAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sivin.Banner;
import com.sivin.BannerAdapter;
import com.example.administrator.access_school_client.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/7/18 16:17.
 */
public class FragmentSY extends Fragment implements View.OnClickListener{

    private Banner banner;
    private List<BannerModel> mDatas;
    private TextView activity;
    private TextView shop;
    private TextView fast;
//    TextView record;
    private TextView news;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mWay;
    private int mHour;
    private int mMinute;
    private ListView gglv;

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
    private int[] picture = new int[]{R.drawable.schoolsafe,R.drawable.guoqing,R.drawable.net,R.drawable.waterstop,R.drawable.bg,R.drawable.bg};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragmentsy , container , false);
        banner = rootview.findViewById(R.id.id_banner);
        activity = rootview.findViewById(R.id.activity);
        shop = rootview.findViewById(R.id.shop);
        fast = rootview.findViewById(R.id.fastsend);
//        record = rootview.findViewById(R.id.record);
        news = rootview.findViewById(R.id.news);
        gglv = (ListView) rootview.findViewById(R.id.gg_lv);
        activity.setOnClickListener(this);
        shop.setOnClickListener(this);
        fast.setOnClickListener(this);
        news.setOnClickListener(this);

        MyLVAdapter adapter1 = new MyLVAdapter();
        gglv.setAdapter(adapter1);
        gglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ActivityGG.class);

                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                intent.putExtra("data",bundle);

                intent.putExtra("position",position);
                Log.e("position",position+"");
                startActivity(intent);
            }
        });
        List<String> data = getMarqueeData();
        XMarqueeView xMarqueeView = (XMarqueeView) rootview.findViewById(R.id.marquee);
        xMarqueeView.setAdapter(new MarqueeViewAdapter(data, getContext()));
        mDatas = new ArrayList<BannerModel>();
        //初始化mdatas图像数据
        getData();
        //绑定banner适配器
        BannerAdapter adapter = new BannerAdapter<BannerModel>(mDatas) {

            @Override
            protected void bindTips(TextView tv, BannerModel bannerModel) {
                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, BannerModel bannerModel) {
                Glide.with(getActivity())
                        .load(bannerModel.getImageUrl())
                        //研究中。。。
//                        .placeholder(R.mipmap.empty)
//                        .error(R.mipmap.error)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(getContext(), "load..error", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imageView);
            }

        };
        banner.setBannerAdapter(adapter);

        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                switch (position){
                    default:break;
                    case 0:
//                        intent.setClass(getContext() , WebViewActivity.class);
//                        intent.putExtra("url","http://www.baidu.com/");
//                        startActivity(intent);
                        Toast.makeText(getContext(), "欢迎使用智宿app", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        intent.setClass(getContext() , CarQuesActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
//                        intent.setClass(getContext() , WebViewActivity.class);
//                        intent.putExtra("url","http://www.hjenglish.com/");
//                        startActivity(intent);
                        intent.setClass(getContext() , AudioActivity.class);
                        startActivity(intent);
                        break;
//                    case 3:
//                        Toast.makeText(getActivity(), "banner", Toast.LENGTH_SHORT).show();
//                        break;
                }
            }
        });
        //实现网络加载数据更新

        //banner.notifyDataHasChanged();

        return rootview;
    }

    public void time() {
        Calendar c = Calendar.getInstance();//
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        mHour = c.get(Calendar.HOUR_OF_DAY);//时
        mMinute = c.get(Calendar.MINUTE);//分
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        FragmentManager fragmentManager = null;
        FragmentTransaction transaction = null;
        switch (view.getId()){
            default:
                break;
            case R.id.activity:
                //校园活动：
//                intent.setClass(getContext() , WebViewActivity.class);
//                intent.putExtra("url","https://www.dhyedu.com/imgae/list.htm?typeid2=1415");
//                startActivity(intent);
//                Toast.makeText(getContext(), "dianji ", Toast.LENGTH_SHORT).show();
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.drawlayout_content,new SchoolActivitiesFrament());
                transaction.addToBackStack(null).commit();
                break;
            case R.id.shop:
                //OCR：
                intent.setClass(getContext() , TransImgAct.class);
//                intent.putExtra("url","https://www.taobao.com/");
                startActivity(intent);
                break;
            case R.id.fastsend:
                //兼职：
                intent.setClass(getContext() , FastActivity.class);
                startActivity(intent);
                break;
//            case R.id.record:
//                //记录：
//                intent.setClass(getContext() , WebViewActivity.class);
//                intent.putExtra("url","http://www.baidu.com/");
//                startActivity(intent);
//                break;
            case R.id.news:
                //校园头条
//                intent.setClass(getContext() , WebViewActivity.class);
//                intent.putExtra("url","http://www.baidu.com/");
//                startActivity(intent);
//                break;
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.drawlayout_content,new Fragmentnews());
                transaction.addToBackStack(null).commit();
            case R.id.change:
//                Mypost();
                break;
        }
    }

    class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rootview;

            //ListView的优化
            if(view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                rootview = inflater.inflate(R.layout.simplenewsitem2, null);
            }
            else{
                rootview = view;
            }
            TextView title = rootview.findViewById(R.id.simple_title2);
            TextView content = rootview.findViewById(R.id.simple_content2);
            TextView time = rootview.findViewById(R.id.simple_time2);
            ImageView imageView = rootview.findViewById(R.id.imageview002);
            Log.e("________","&*********"+i);
            title.setText(title1[i]);
            content.setText(content1[i]);
            time.setText(date[i]);
            imageView.setImageResource(picture[i]);
//            imageView.setImageResource(R.drawable.news1);


            return rootview;
        }
    }

    class MarqueeViewAdapter extends XMarqueeViewAdapter{

        private Context mContext;
        public MarqueeViewAdapter(List<String> datas, Context context) {
            super(datas);
            mContext = context;
        }

        @Override
        public View onCreateView(XMarqueeView parent) {
            //跑马灯单个显示条目布局，自定义
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.marqueeview_item, null);
        }

        @Override
        public void onBindView(View parent, View view, final int position) {
            //布局内容填充
            TextView tvOne = (TextView) view.findViewById(R.id.marquee_tv_one);
            tvOne.setText(mDatas.get(position).toString());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "快去校园头条查看详情吧....", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private List<String> getMarqueeData() {
        List<String> data = new ArrayList<>();
        data.add("开学啦，同学们纷纷前去办网....");
        data.add("开展大扫除活动检查。");
        data.add("大扫除活动开展！！！");
        data.add("我院网络系16级学生在信息安全铁人三项赛中获奖");
        data.add("校领导参观本校");
        data.add("宁波大红鹰学院 关于举办 “2017‘象山影视城杯’ 文化创意设计大赛”的通知");

        return data;
        //刷新数据
        //marqueeViewAdapter.setData(data);
    }

    private void getData() {
        mDatas.clear();
        BannerModel model = null;
        model = new BannerModel();
        //天猫
        model.setImageUrl("http://img5.imgtn.bdimg.com/it/u=173532944,1975000701&fm=26&gp=0.jpg");
        model.setTips("校园宿舍好帮手");
        mDatas.add(model);

        //驾考
        model = new BannerModel();
        model.setImageUrl("http://img1.imgtn.bdimg.com/it/u=244790144,3579711323&fm=26&gp=0.jpg");
        model.setTips("驾考学习归宿");
        mDatas.add(model);

        //学习英语
        model = new BannerModel();
        model.setImageUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1964576144,2912205023&fm=26&gp=0.jpg");
        model.setTips("英语学习");
        mDatas.add(model);

//        model = new BannerModel();
//        model.setImageUrl("https://gw.alicdn.com/tps/i2/TB1ku8oMFXXXXciXpXXdIns_XXX-1125-352.jpg_q50.jpg");
//        model.setTips("这是页面4");
//        mDatas.add(model);
        //banner.notifyDataHasChanged();
    }

}

