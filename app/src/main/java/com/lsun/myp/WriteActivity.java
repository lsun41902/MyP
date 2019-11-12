package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteActivity extends AppCompatActivity {

    EditText etTitle, etText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        etTitle = findViewById(R.id.wirte_et_title);
        etText = findViewById(R.id.wirte_et_text);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkedPrmission= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐
            if(checkedPrmission== PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }

    }

    //requestPermissions()메소드로 인해 보여지는 다이얼로그에서 [허가/거부]선택 후 결과콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 10:
                if( grantResults[0]==PackageManager.PERMISSION_DENIED ){
                    Toast.makeText(this, "외부메모리 기능 사용 제한", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "외부메모리 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void clickOK(View view) {
        if (etText.length()<=20){

            new AlertDialog.Builder(this).setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setTitle("최소 20자 이상").create().show();
            return;
        }else {
            new AlertDialog.Builder(this).setTitle("작성 하기").setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String title = etTitle.getText().toString();
                    Intent intent = getIntent();
                    if (title.equals("")) {
                        title = "제목없음";
                    }
                    intent.putExtra("Title", title);
                    setResult(RESULT_OK, intent);
                    dialogInterface.dismiss();
                    finish();


                }
            }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();

        }

    }

    public void clickCANCEL(View view) {
        new AlertDialog.Builder(this).setTitle("작성 취소").setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        new AlertDialog.Builder(this).setTitle("작성 취소").setPositiveButton("네", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                finish();
//            }
//        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        }).create().show();
//
//    }
}
