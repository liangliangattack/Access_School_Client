package com.example.administrator.access_school_client.Fragments.FragmentSQ;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;

import static android.app.Activity.RESULT_OK;

public class FragmentBX extends Fragment {
    ImageView bx_img;
    private Button bx_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bx,container,false);
        bx_img=view.findViewById(R.id.bx_img);
        bx_button = view.findViewById(R.id.bx_button);
        bx_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "保修申请已提交", Toast.LENGTH_SHORT).show();
            }
        });
        bx_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择方式");
                builder.setPositiveButton("从本地选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);
                    }
                });
                builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 2);
                    }
                });

                builder.create().show();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (requestCode==1&&data != null) {
                bx_img.setImageURI(data.getData());

            }
            if(requestCode==2&&data!=null){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                bx_img.setImageBitmap(photo);
            }
        }

    }
}
