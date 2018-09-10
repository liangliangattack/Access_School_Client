package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/6 21:58.
 */
public class SchoolActivitiesFrament extends Fragment {

    private ListView listView;

    private String[] date = new String[]{"2018-09-12","2018-09-11","2018-09-09","2018-08-30","2018-08-28","2018-08-25","2018-08-20"};
    private String[] title1 = new String[]{"篮球比赛开始报名参赛！",
            "新生军训！！！",
            "我校许多老师获得荣誉证书与奖项",
            "校领导参观本校",
            "2018年暑期兼职实习专场招聘会举行",
            "共建和谐平安校园",
            "运动会展现你我风采"};
    private String[] content1 = new String[]{"为了提高大学生的综合素质，丰富大学生的课余生活，展现大学生积极向上的风貌，增强团队精神和集体凝聚力，让体育精神深入人心，各个学院热烈的比赛帷幕拉开，大家踊跃报名！",
            "军训培养了同学们艰苦朴素的作风。军人不讲究穿戴，就是一套军装，但军人有的是站如松、坐如钟、行如风的气质。能够得到他人认可和赞赏的是人的精神，人的美在于气质，在于他内在的精神境界。\n" +
                    "此外，军训还能够培养人的纪律观念，我们学校强调依法治校、从严管理、从严要求，这是人才培养所必需的。军训生活培养人的组织性、纪律性，这和依法治校是相符合的。军队是讲奉献、讲团结、讲文明的一个集体，先人后己，在军训中得到的收获能够使同学们养成讲文明、守纪、守法的好习惯，对以后我们建设文明校园、和谐校园是很有好处的。",
            "老师的辛勤劳动换来的成果是为国家和社会培养了一批又一批优秀人才。正式因为如此，老师应该获得这份荣誉与光荣！",
            "考察团到校后，学校领导就我校的基本情况、办学理念、办学成绩等向考察团做了详细的介绍；随后，校领导依次参观了教学楼、餐厅食堂、操场、综合馆、体育馆等地。",
            "6月14日下午，2018年暑期兼职实习专场招聘会在信息大楼举行。本次招聘会由中国宁波人才市场宁波大红鹰学院分市场主办。中汇税务师事务所、太平鸟、招商证券、华润万家等60余家用人单位参加招聘活动，共提供600多个优质兼职实习岗位。\n" +
                    "    招聘会现场人头攒动、秩序井然，同学们积极与招聘单位沟通，主动了解用人单位情况。他们表示，希望通过暑期兼职实习工作，锻炼自己、提升能力，实现自己的职业生涯规划目标。许多用人单位也表示，我校学生综合素质较高、接地气！欢迎更多的同学来本单位实习。",
            "次主题教育活动的开展，进一步增强了师生的安全意识，提高日常安全防范和自我保护意识。",
            "运动健儿们在场上飞驰，撒下晶莹的汗珠，留下一个个坚定的、拼搏的背影。观赛的其他运动员更是加油、呐喊、助威，当健儿们约过终点的那一霎那，掌声为你们响起，胜利和荣耀是属于你们的！"};
    private int[] picture = new int[]{R.drawable.basketball,R.drawable.solider,R.drawable.bg,R.drawable.news4,R.drawable.news5,R.drawable.news6,R.drawable.sports};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_news,null);
        listView = rootview.findViewById(R.id.listview);
        MyLVAdapter myLVAdapter = new MyLVAdapter();
        listView.setAdapter(myLVAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                FragmentNewsDetails fragmentNewsDetails = new FragmentNewsDetails();
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("item",i);
//                fragmentNewsDetails.setArguments(bundle);
//                transaction.add(R.id.drawlayout_content,fragmentNewsDetails);
//                transaction.addToBackStack(null).commit();
            }
        });

        return rootview;
    }

    class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rootview = inflater.inflate(R.layout.simplenewsitem , null);
            TextView title = rootview.findViewById(R.id.simple_title);
            TextView content = rootview.findViewById(R.id.simple_content);
            TextView time = rootview.findViewById(R.id.simple_time);
            ImageView imageView = rootview.findViewById(R.id.imageview00);
            Log.e("________","&*********"+i);
            title.setText(title1[i]);
            content.setText(content1[i]);
            time.setText(date[i]);
            imageView.setImageResource(picture[i]);
//            imageView.setImageResource(R.drawable.news1);


            return rootview;
        }
    }
}
