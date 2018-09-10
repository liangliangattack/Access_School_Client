package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.access_school_client.Util.Base64;
import java.security.MessageDigest;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.Fast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/5 11:36.
 */
public class FastActivity extends AppCompatActivity {

    String EBusinessID = "1378853";
    //加密私钥，由快递鸟提供
    String AppKey = "4214ffd1-43e5-45de-9d11-dff1a50e9a50";
    //请求地址
    String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    //示例单号：
    String singlenum ="3374493355336";
    String singlenum2 = "3902951658241";
    String singlenum3 = "638650888018";
    //2-json638650888018
//    String DataType = "2";
    //字符编码采用UTF-8
//    String charset = "UTF-8";
    //JSON字符串string
    String jsonStr = "";
    //请求报文参数
//    String PostStr = "RequestType=1002&EBusinessID=userID&RequestData=jsonStr&DataSign=datasign&DataType=DataType";

    private List<Fast> fasts = new ArrayList<Fast>();
    private Button trace;
    private ListView lvfast;
    private Spinner fastcompanysp;
    private String fastcompany = "";
    private EditText logisticcode_et;
    private TextView Shipper_company;
    private TextView fast_LogisticCode;
    private TextView State;
    private LinearLayout fastcontent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast);
        trace = findViewById(R.id.catchtrace);
        lvfast = findViewById(R.id.fast_lv);
        fastcompanysp = findViewById(R.id.fastcompany_sp);
        logisticcode_et = findViewById(R.id.logisticcode);
        Shipper_company = findViewById(R.id.Shipper_company);
        fast_LogisticCode = findViewById(R.id.fast_LogisticCode_tv);
        State = findViewById(R.id.state_tv);
        fastcontent = findViewById(R.id.fastcontent_ll);

        fastcompanysp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fastcompany1 = parent.getItemAtPosition(position).toString();

                Shipper_company.setText(fastcompany1);

                switch (fastcompany1){
                    default:break;
                    case "顺丰速运":
                        fastcompany = "SF";
                        break;
                    case "百世快递":
                        fastcompany = "HTKY";
                        break;
                    case "中通快递":
                        fastcompany = "ZTO";
                        break;
                    case "申通快递":
                        fastcompany = "STO";
                        break;
                    case "圆通速递":
                        fastcompany = "YTO";
                        break;
                    case "韵达速递":
                        fastcompany = "YD";
                        break;
                    case "邮政快递包裹":
                        fastcompany = "YZPY";
                        break;
                    case "EMS":
                        fastcompany = "EMS";
                        break;
                    case "天天快递":
                        fastcompany = "HHTT";
                        break;
                    case "京东快递":
                        fastcompany = "JD";
                        break;
                    case "优速快递":
                        fastcompany = "UC";
                        break;
                    case "德邦快递":
                        fastcompany = "DBL";
                        break;
                    case "宅急送":
                        fastcompany = "ZJS";
                        break;
                    case "TNT快递":
                        fastcompany = "TNT";
                        break;
                    case "UPS":
                        fastcompany = "UPS";
                        break;
                    case "DHL":
                        fastcompany = "DHL";
                        break;
                    case "FEDEX联邦(国内件）":
                        fastcompany = "FEDEX";
                        break;
                    case "FEDEX联邦(国际件）":
                        fastcompany = "FEDEX_GJ";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fastcompany = "HTKY";
            }
        });

        trace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    orderTracesSubByJson();
                    fastcontent.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        logisticcode_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fastcontent.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Json方式  物流信息订阅
     * @throws Exception
     */
    public String orderTracesSubByJson() throws Exception{
//        Map<String, String> params = new HashMap<String, String>();
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("RequestType", "1002");
        params.put("EBusinessID", EBusinessID);
        //首先获取logisticcode
        String logisticcode = logisticcode_et.getText().toString();
        //拼接jsonStr
        jsonStr = "{\"OrderCode\":\"\",\"ShipperCode\":\""+fastcompany+"\",\"LogisticCode\":\""+ logisticcode +"\"}";
        String urljsonStr = urlEncoder(jsonStr, "UTF-8");
        params.put("RequestData", urljsonStr);
        Log.e("requestdata",jsonStr);
        String dataSign = base64(MD5(jsonStr + AppKey, "UTF-8"), "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        Log.e("url","http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx?RequestType=1002&EBusinessID=1378853&RequestData="+urljsonStr+"&DataSign="+dataSign+"&DataType=2");

        //http操作
        http.post(ReqURL,params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                handleKd(o);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);

            }
        });
//        String result=sendPost(ReqURL, params);
        String result = "null";
        //根据公司业务处理返回的信息......

        return result;
    }

    private void handleKd(Object o) {
        try{
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(o.toString(), JsonObject.class);
            //保存相关信息
            String ShipperCode = String.valueOf(jsonObject.get("ShipperCode"));
            String LogisticCode2 = String.valueOf(jsonObject.get("LogisticCode"));
            fast_LogisticCode.setText(LogisticCode2);

            String state = String.valueOf(jsonObject.get("State"));
            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "\"3\"", Toast.LENGTH_SHORT).show();
            StateChange(state);

            String Reason = "";
            if(jsonObject.get("Reason")!=null) {
                Reason = String.valueOf(jsonObject.get("Reason"));
            }
            else{ }

            if(jsonObject.get("Traces").toString()!=null) {
                JSONArray array1 = new JSONArray(jsonObject.get("Traces").toString());
                fasts.removeAll(fasts);
                for (int i = array1.length()-1; i >=0 ; i--) {
                    JSONObject json1 = array1.getJSONObject(i);
                    Fast fast = new Fast(
                            "快递编码：" + ShipperCode,
                            "快递单号：" + LogisticCode2,
                            "时间：" + json1.getString("AcceptTime"),
                            json1.getString("AcceptStation"),
                            state,
                            Reason);
                    Log.e("handleKd", "time" + fast.getTracetime() + "\ntrace:" + fast.getTraceinfo());
                    fasts.add(fast);
                }
                lvfast.setAdapter(new MyLVAdapter());
            }
            else{

            }
        } catch (JSONException e) {
            Log.e("exception","dsdddddddd");
            e.printStackTrace();
            Log.e("exception","dsdddddddd");
        }
    }

    void StateChange(String state){
        switch (state){
            default:break;
            case "\"0\"":
                State.setText("无轨迹状态.....");
                break;
            case "\"1\"":
                State.setText("已揽收");
                break;
            case "\"2\"":
                State.setText("快递不日便可抵达");
                break;
            case "\"3\"":
                State.setText("已签收");
                break;
        }
    }


    class MyLVAdapter extends BaseAdapter {

        private Boolean isState = false;
        private Boolean isEBusinessID = false;
        private Boolean isReason = false;

        @Override
        public int getCount() {
            return fasts.size();
        }

        @Override
        public Object getItem(int position) {
            return fasts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(FastActivity.this, R.layout.fastitem, null);
            Log.e("baseadapter","time"+fasts.get(position).getTracetime()+"\ntrace:"+fasts.get(position).getTraceinfo());
            TextView datetime = (TextView) convertView.findViewById(R.id.tv_dateTimeExpress);
            TextView remark = (TextView) convertView.findViewById(R.id.tv_remarkExpress);
            datetime.setText(fasts.get(position).getTracetime());
            remark.setText(fasts.get(position).getTraceinfo());
            return convertView;
        }
    }


    /**
     * XML方式  物流信息订阅
     * @throws Exception
     */
    public String orderTracesSubByXml() throws Exception{
        String requestData="<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+
                "<Content>"+
                "<Code>SF</Code>"+
                "<Items>"+
                "<Item>"+
                "<No>909261024507</No>"+
                "<Bk>test</Bk>"+
                "</Item>"+
                "<Item>"+
                "<No>909261024507</No>"+
                "<Bk>test</Bk>"+
                "</Item>"+
                "</Items>"+
                "</Content>";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1005");
        String dataSign=encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "1");

        String result=sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * @param str 内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * @param content 内容
     * @param keyValue Appkey
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
    {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
