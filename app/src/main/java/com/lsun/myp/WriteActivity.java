package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lsun.myp.ItemChat;
import com.lsun.myp.MyMember;
import com.lsun.myp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    EditText etTitle, etText;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    Uri writeImage1,writeImage2,writeImage3;
    ImageView iv1,iv2,iv3;
    public static final int REQ_WRITEIMAGE1=1004;
    public static final int REQ_WRITEIMAGE2=1005;
    public static final int REQ_WRITEIMAGE3=1006;
    String img1;
    String img2;
    String img3;
    Date date=new Date(System.currentTimeMillis());
    FirebaseDatabase firebaseDatabase;
    DatabaseReference board;
    String username;
    DatabaseReference imgs;
    String down1;
    String down2;
    String down3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        etTitle = findViewById(R.id.write_et_title);
        etText = findViewById(R.id.write_et_text);
        firebaseDatabase = FirebaseDatabase.getInstance();
        board=firebaseDatabase.getReference("board");
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        username=sp.getString("userNickname",null);

        getSupportActionBar().setTitle("글쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //퍼미션
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkedPrmission= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐
            if(checkedPrmission== PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }

        iv1=findViewById(R.id.write_iv_1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent,REQ_WRITEIMAGE1);
            }
        });
        iv2=findViewById(R.id.write_iv_2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent,REQ_WRITEIMAGE2);
            }
        });
        iv3=findViewById(R.id.write_iv_3);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent,REQ_WRITEIMAGE3);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        imgs = firebaseDatabase.getReference("Image");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkbtn,menu);
        return true;
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


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "작성을 취소하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_WRITEIMAGE1:
                if(resultCode==RESULT_OK){
                    writeImage1=data.getData();
                    img1=getRealPathFromUri(writeImage1);
                    Glide.with(WriteActivity.this).load(writeImage1).into(iv1);
                }
                break;
            case REQ_WRITEIMAGE2:
                if(resultCode==RESULT_OK){
                    writeImage2=data.getData();
                    img2=getRealPathFromUri(writeImage2);
                    Glide.with(WriteActivity.this).load(writeImage2).into(iv2);
                }
                break;
            case REQ_WRITEIMAGE3:
                if(resultCode==RESULT_OK){
                    writeImage3=data.getData();
                    img3=getRealPathFromUri(writeImage3);
                    Glide.with(WriteActivity.this).load(writeImage3).into(iv3);
                }
                break;

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.check_write:
                if (etText.length()<=20){

                    new AlertDialog.Builder(this).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setTitle("최소 20자 이상").create().show();
                }else {
                    new AlertDialog.Builder(this).setTitle("작성 하기").setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String title = etTitle.getText().toString();
                            String text= etText.getText().toString();
//                            Intent intent = getIntent();
                            if (title.equals("")) {
                                title = "제목없음";
                            }
//                            intent.putExtra("Title", title);
//                            intent.putExtra("Text",text);
                            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd HH:mm", Locale.KOREA);
                            // nowDate 변수에 값을 저장한다.
                            final String formatDate = sdfNow.format(date);

                            String fileName = sdfNow.format(new Date()) + ".png";
                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            final StorageReference imgRef1 = firebaseStorage.getReference("board/"+title+"/" + "a"+fileName);
                            final StorageReference imgRef2 = firebaseStorage.getReference("board/"+title+"/" + "b"+fileName);
                            final StorageReference imgRef3 = firebaseStorage.getReference("board/"+title+"/" + "c"+fileName);
                            final MyMember myMember=new MyMember(ItemChat.getUrlstring(),username,title,text,down1,down2,down3,formatDate);
                            if(img1!=null){
                                imgRef1.putFile(writeImage1);
                                UploadTask uploadTask = imgRef1.putFile(writeImage1);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //이미지 업로드가 성공되었으므로...곧바로 Firebase storage 에 이미지 파일 다운로드 URL을 얻어오겠습니다.
                                        imgRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //파라미터로 firebase 저장소에 저장되어 있는 다운로드주소 (URL)을 문자열로 얻어오기
                                                down1=uri.toString();
                                                myMember.setImg1(down1);
                                                Log.i("imagedownuri",down1);
                                                board.child(formatDate).setValue(myMember);
                                            }
                                        });
                                    }
                                });
                            }
                            if(img2!=null){
                                imgRef2.putFile(writeImage2);
                                UploadTask uploadTask2 = imgRef2.putFile(writeImage2);
                                uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //이미지 업로드가 성공되었으므로...곧바로 Firebase storage 에 이미지 파일 다운로드 URL을 얻어오겠습니다.
                                        imgRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //파라미터로 firebase 저장소에 저장되어 있는 다운로드주소 (URL)을 문자열로 얻어오기
                                                down2=uri.toString();
                                                myMember.setImg2(down2);
                                                Log.i("imagedownuri",down2);
                                                board.child(formatDate).setValue(myMember);
                                            }
                                        });
                                    }
                                });
                            }
                            if(img3!=null){
                                imgRef3.putFile(writeImage3);
                                UploadTask uploadTask3 = imgRef3.putFile(writeImage3);
                                uploadTask3.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //이미지 업로드가 성공되었으므로...곧바로 Firebase storage 에 이미지 파일 다운로드 URL을 얻어오겠습니다.
                                        imgRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //파라미터로 firebase 저장소에 저장되어 있는 다운로드주소 (URL)을 문자열로 얻어오기
                                                down3=uri.toString();
                                                Log.i("imagedownuri",down3);
                                                myMember.setImg3(down3);
                                                board.child(formatDate).setValue(myMember);
                                            }
                                        });
                                    }
                                });
                            }
                            board.child(formatDate).setValue(myMember);
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
                break;
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
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
                return true;


            }
        }
        return super.onOptionsItemSelected(item);
    }
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

}