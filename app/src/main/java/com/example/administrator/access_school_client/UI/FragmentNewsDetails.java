package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/8/31 19:22.
 */
public class FragmentNewsDetails extends Fragment {

    ImageView picture;
    TextView title;
    TextView content;
    TextView time;
    private String[] date = new String[]{"2018-09-13","2018-09-12","2018-09-10","2018-08-12","2018-08-11","2018-07-09","2018-06-15"};
    private String[] title1 = new String[]{"开学啦，同学们纷纷前去办网....",
            "开展大扫除活动检查。",
            "大扫除活动开展！！！",
            "校领导参观本校",
            "2018年暑期兼职实习专场招聘会举行",
            "我校选派创业导师赴香港理工大学参加师资培训活动",
            "我校第二届斯洛伐克夏令营活动举行 中国驻斯洛伐克大使林琳出席"};
    private String[] content1 = new String[]{"同学开始纷纷入驻校园并且办网",
            "今天将对学校通知的大扫除成功进行验收，请各位寝室长做好准备",
            "开学了，请同学们做好自己寝室的卫生工作，完善一个美好环境的校园",
            "考察团到校后，学校领导就我校的基本情况、办学理念、办学成绩等向考察团做了详细的介绍；随后，校领导依次参观了教学楼、餐厅食堂、操场、综合馆、体育馆等地。",
            "6月14日下午，2018年暑期兼职实习专场招聘会在信息大楼举行。本次招聘会由中国宁波人才市场宁波大红鹰学院分市场主办。中汇税务师事务所、太平鸟、招商证券、华润万家等60余家用人单位参加招聘活动，共提供600多个优质兼职实习岗位。\n" +
                    "    招聘会现场人头攒动、秩序井然，同学们积极与招聘单位沟通，主动了解用人单位情况。他们表示，希望通过暑期兼职实习工作，锻炼自己、提升能力，实现自己的职业生涯规划目标。许多用人单位也表示，我校学生综合素质较高、接地气！欢迎更多的同学来本单位实习。",
            "日前，学校选派校内创业导师赴香港理工大学参加创新创业教育师资培训活动。校长助理何秋叶带队并同来自各二级学院及相关职能部门的20余名教师代表参加了此次培训。据了解，选派创业导师赴香港理工大学师资培训项目是我校实施创新创业教育、加强创业导师师资队伍建设的系列活动之一，首批派出的学员全部顺利结业，并获得了由香港理工大学颁发的培训结业证书。",
            "林琳在开营仪式讲话上充分肯定了我校与斯方高开展合作以来取得的丰硕成果，并希望未来两校能够进一步深化教育领域的交流合作。斯方高校有关负责人对各位学员表示了诚挚的欢迎，并介绍了该校的发展概况以及活动安排。在为期1个月的暑期夏令营活动中，斯洛伐克高校为我校学生安排了“发现欧盟与斯洛伐克、项目管理、国际营销、电子商务”等丰富的学习课程与研讨，同时斯方还将为我校学生安排奥地利维也纳、匈牙利布达佩斯、捷克布拉格等周边城市的采风之旅活动。\n" +
                    "    据悉，为加深与中东欧的教育、经济和文化的交流，我校与斯洛伐克相关高校于2016年12月签署了合作协议。根据协议，从2017年起，我校每年将组织师生赴斯洛伐克参加夏令营活动。通过举办斯洛伐克夏令营活动，促进两校交流，丰富我校学子学习与文化体验，为今后的考研及访学深造搭建更多平台。"};
    private int[] pictures = new int[]{R.drawable.news1,R.drawable.news2,R.drawable.news3,R.drawable.news4,R.drawable.news5,R.drawable.news6,R.drawable.news7};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_news_details,null);
        picture = rootview.findViewById(R.id.iv_details);
        time = rootview.findViewById(R.id.time_detials);
        title = rootview.findViewById(R.id.title_detials);
        content = rootview.findViewById(R.id.content_detials);
        int position;
        if(getArguments()!=null){
            position = getArguments().getInt("item");
        }
        else{
            position = 0;
        }
        picture.setImageResource(pictures[position]);
        time.setText(date[position]);
        title.setText(title1[position]);
        content.setText(content1[position]);



        return rootview;
    }
}
