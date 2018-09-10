package com.example.administrator.access_school_client.UI;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import net.coobird.thumbnailator.Thumbnails;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.Base64;
import com.example.administrator.access_school_client.Util.ImageViewTool;
import com.example.administrator.access_school_client.Util.PermissionUtil;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;
import com.example.administrator.access_school_client.bean.TransObj;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static com.example.administrator.access_school_client.Util.GauseBulrHelper.blur;

public class TransImgAct extends AppCompatActivity implements View.OnClickListener{

    private ImageView transiv;
    private EditText display;
    private Button uploadbtn;
    private Button uploadalbumbtn;
    private String mImagePath = null;
    private String tempFilePath = null;
    private List<TransObj> transObjs = new ArrayList<TransObj>();
    private String to = "zh-CHS";
    private Spinner sp;
    private ImageView bar;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:break;
            case R.id.upload_btn:
                //相机
                if(PermissionUtil.hasCameraPermission(this)) {
                    takePhoto();
                    Toast.makeText(this,"打开相机",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"无拍照权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.upload_album_btn:
                //相册
                if(PermissionUtil.hasReadExternalStoragePermission(this)) {
                    pickPhoto();
                }
                else{
                    Toast.makeText(this,"无查看权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        display.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
//        generateTemImgPath();
    }

    public static void compressImageToFile(Bitmap bmp,File file) {
        // 0-100 100为不压缩
        int options = 5;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  generateTemImgPath(){
//        tempFilePath = null;
        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss",Locale.CHINA);
//        String tem = "/"+sdf.format(new Date())+".png";
//        File file = new File(path,tem);
//        tempFilePath = file.getAbsolutePath();
        tempFilePath = path;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_transimg);
        transiv = findViewById(R.id.trans_iv);
        uploadbtn = findViewById(R.id.upload_btn);
        uploadalbumbtn = findViewById(R.id.upload_album_btn);
        display = findViewById(R.id.multitext);
        sp = findViewById(R.id.style);
        bar = findViewById(R.id.progressbar);

        //图片生成操作：
        uploadbtn.setOnClickListener(this);
        uploadalbumbtn.setOnClickListener(this);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String style = parent.getItemAtPosition(position).toString();
                switch (style){
                    case "中文":to = "zh-CHS";break;
                    case "英文":to = "en";break;
                    case "韩文":to = "ko";break;
                    case "法文":to = "fr";break;
                    case "俄文":to = "ru";break;
                    case "葡萄牙文":to = "pt";break;
                    case "西班牙文":to = "es";break;
                    case "越南文":to = "vi";break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                to = "zh-CHS";
            }
        });

    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, (int)0.6, bos);
        bos.flush();
        bos.close();
    }


    /**
     *
     * @param appKey 应用ID
     * @param appSecret 应用密钥
     * @param filePath 图片路径
     * @param tmpFilePath 压缩后文件临时保存路径
     */
    public void ocrtrans(String appKey,String appSecret,String filePath,String tmpFilePath) throws IOException {
        /** 图片翻译接口地址 */
        String url = "http://openapi.youdao.com/ocrtransapi";

        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();

        File file = new File(filePath);
        if(!file.exists()){
            Log.e("ocrtrans","文件不存在！");
            return;
        }
        /** 压缩图片 */
        long maxSize = 1 * 1024 * 1024;
//        float quality = 0.7f;
//        if(file.length() > maxSize){
            /** 设置图片大小和质量 */
//            Thumbnails.of(filePath).scale(1f).outputQuality(quality).toFile(new File(tmpFilePath));

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitmap =  BitmapFactory.decodeFile(filePath, opts);
//            bitmap = ImageViewTool.compressImage(bitmap);

        compressImageToFile(bitmap,file);
//            String picname = file.getName();
//            saveFile(bitmap,picname);

//            filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+picname+".png";

//            File tmpFile = new File(tmpFilePath);
//            filePath = tmpFilePath;
            /** 连续压缩 */
//            while(tmpFile.length() > maxSize){
//                quality -= 0.2;
//                Thumbnails.of(filePath).scale(1f).outputQuality(quality).toFile(tmpFile);
//                tmpFile = new File(tmpFilePath);
//            }
//        }
//        System.out.println(file.length());
        Log.e("TransImgAct","file.length()"+file.length());

        String salt = String.valueOf(System.currentTimeMillis());
        String from = "auto";
//        String to = "zh-CHS";
        String type = "1";
        String sign = null;
        params.put("appKey",appKey);
        params.put("salt",salt);
        params.put("from",from);
        params.put("to",to);
        params.put("type",type);

        /** 请求图片翻译 */
        File imgFile = new File(filePath);
        String result = null;
        String q = getBase64OfFile(imgFile);
        params.put("q", q);
        try {
            sign = MD5(appKey + q + salt +appSecret,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("sign",sign);

//        result = requestForHttp(url,params);
        Log.e("TransImgAct",""+to);
        http.post(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                getR(o);
                Log.e("TransImgAct","onsuccess");
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("TransImgAct","failer");
            }
        });
        /** 处理结果 */
//        System.out.println(result);
    }

    void getR(Object o){
        Gson gson = new Gson();
        JsonObject jsonObject1 = gson.fromJson(o.toString(),JsonObject.class);
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                StringBuffer stringBuffer = new StringBuffer();
                JSONArray jsonArray = new JSONArray(jsonObject1.get("resRegions").toString());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    TransObj t = new TransObj(jsonObject.getString("context"),
                            jsonObject.getString("tranContent"));
                    transObjs.add(t);
                    String str = i+"."+t.getContext()+"\n"+t.getTranContent()+"\n"+"  ";
                    stringBuffer.append(str);
                }
                display.setText(stringBuffer);
                bar.setVisibility(View.GONE);
                display.setVisibility(View.VISIBLE);
            }


        } catch (JSONException e) {
            Log.e("TransImgAct","--------------------------------------------------");
            e.printStackTrace();
            Log.e("TransImgAct","--------------------------------------------------");
        }

    }

    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            //通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".png";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri

            //开始相机操作
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            //0表示相机
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "SD卡不存在!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath = "";
        if (0 == requestCode && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                //有数据返回直接使用返回的图片地址
                //imagePath = getFilePathByFileUri(getContext(), data.getData());
                imagePath = getPath(data.getData());
            } else {//无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            //得到图片imagepath的相关操作：。。。。

            //dataList.addFirst(imagePath);
            //adapter.update(dataList); // 刷新图片

        }
        else if(1 == requestCode && resultCode == RESULT_OK){
            Uri mPath = data.getData();
            imagePath = getPath(mPath);
            Log.d("TransImgAct","相册图片地址"+imagePath);

        }
        Log.e("TransImgAct","onActivityResult:"+"12222222222222");

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitmap =  BitmapFactory.decodeFile(imagePath, opts);
        transiv.setImageBitmap(bitmap);
//        blur(this,bitmap);

        String appKey = "3f2fd394a462345f";
        String appSecret = "9vcWJHrxW985AIp21CaR1TPYlgfXAOBs";
        String filePath = mImagePath;
//        generateTemImgPath();
        try {
            Log.e("TransImgAct","filePath："+filePath);
            ocrtrans(appKey,appSecret,filePath,tempFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String requestForHttp(String url,Map&lt;String,String&gt; params) throws IOException {
//        String result = &quot;&quot;;
//
//        /** 创建HttpClient */
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        /** httpPost */
//        HttpPost httpPost = new HttpPost(url);
//        List&lt;NameValuePair&gt; paramsList = new ArrayList&lt;NameValuePair&gt;();
//        Iterator&lt;Map.Entry&lt;String,String&gt;&gt; it = params.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry&lt;String,String&gt; en = it.next();
//            String key = en.getKey();
//            String value = en.getValue();
//            paramsList.add(new BasicNameValuePair(key,value));
//        }
//        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,&quot;UTF-8&quot;));
//        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//        try{
//            HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity,&quot;UTF-8&quot;);
//            EntityUtils.consume(httpEntity);
//        }finally {
//            try{
//                if(httpResponse!=null){
//                    httpResponse.close();
//                }
//            }catch(IOException e){
//                logger.info(&quot;## release resouce error ##&quot; + e);
//            }
//        }
//        return result;
//    }

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

    public static String getBase64OfFile(File file){
        byte[] data = null;
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(file));
            data = new byte[in.available()];
            in.read(data);

        }catch (Exception e){
            e.printStackTrace();
        }
        return Base64.encode(data);
    }

    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(this, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
