package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    EditText etTitle, etText;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    Uri writeImage1,writeImage2,writeImage3;
    ImageView iv1,iv2,iv3;
    public static final int REQ_WRITEIMAGE1=1004;
    public static final int REQ_WRITEIMAGE2=1005;
    public static final int REQ_WRITEIMAGE3=1006;
    Date date=new Date(System.currentTimeMillis());
    String img1;
    String img2;
    String img3;
    ArrayList<MyMember> members = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Log.i("moyangmoyang",AdapterMember.numbuer+"");
        etTitle = findViewById(R.id.write_et_title);
        etText = findViewById(R.id.write_et_text);
        getSupportActionBar().setTitle("수정");
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
        //Glide.with(this).load().into(iv1);
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

        loadDB();

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
            Toast.makeText(getApplicationContext(), "수정을 취소하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
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
                    Glide.with(PostActivity.this).load(writeImage1).into(iv1);
                }
                break;
            case REQ_WRITEIMAGE2:
                if(resultCode==RESULT_OK){
                    writeImage2=data.getData();
                    img2=getRealPathFromUri(writeImage2);
                    Glide.with(PostActivity.this).load(writeImage2).into(iv2);
                }
                break;
            case REQ_WRITEIMAGE3:
                if(resultCode==RESULT_OK){
                    writeImage3=data.getData();
                    img3=getRealPathFromUri(writeImage3);
                    Glide.with(PostActivity.this).load(writeImage3).into(iv3);
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.check_write:
//                if (etText.length()<=20){
//
//                    new AlertDialog.Builder(this).setPositiveButton("나가기", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    }).setTitle("최소 20자 이상").create().show();
//                }else {
//                    new AlertDialog.Builder(this).setTitle("작성 하기").setPositiveButton("네", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            String title = etTitle.getText().toString();
//                            String text= etText.getText().toString();
//                            Intent intent = getIntent();
//                            if (title.equals("")) {
//                                title = "제목없음";
//                            }
//                            intent.putExtra("Title", title);
//                            intent.putExtra("Text",text);
//                            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.KOREA);
//                            // nowDate 변수에 값을 저장한다.
//                            String formatDate = sdfNow.format(date);
//                            intent.putExtra("Date",formatDate);
//                            intent.putExtra("Image1",writeImage1);
//                            intent.putExtra("Image2",writeImage2);
//                            intent.putExtra("Image3",writeImage3);
//                            String userId=SelectLoginActivity.startEmail;
//                            intent.putExtra("userID",userId);
//                            setResult(RESULT_OK, intent);
//
//                            String serverUri="http://lsun41902.dothome.co.kr/GotoWork/Board/gotoworkDB.php";
//                            SimpleMultiPartRequest simpleMultiPartRequest=new SimpleMultiPartRequest(Request.Method.POST, serverUri, new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    Log.i("moyang",response);
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(PostActivity.this, "에러", Toast.LENGTH_SHORT).show();
//                                    Log.i("moyang",String.valueOf(error));
//                                }
//                            });
//                            simpleMultiPartRequest.addStringParam("title",title);
//                            simpleMultiPartRequest.addStringParam("date",formatDate);
//                            simpleMultiPartRequest.addStringParam("text",text);
//                            simpleMultiPartRequest.addStringParam("userID",SelectLoginActivity.startEmail);
//                            simpleMultiPartRequest.addStringParam("nickName",StartProfileActivity.userNickname);
//                            simpleMultiPartRequest.addFile("img1",img1);
//                            simpleMultiPartRequest.addFile("img2",img2);
//                            simpleMultiPartRequest.addFile("img3",img3);
//                            simpleMultiPartRequest.addFile("profileImg",StartProfileActivity.profileImg);
//                            RequestQueue requestQueue= Volley.newRequestQueue(PostActivity.this);
//                            requestQueue.add(simpleMultiPartRequest);
//                            dialogInterface.dismiss();
//                            finish();
//                        }
//                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    }).create().show();
//
//                }
                Intent intent=getIntent();
                setResult(RESULT_OK,intent);
                finish();
                break;
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                new AlertDialog.Builder(this).setTitle("수정 취소").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etText.setText("");
                        etTitle.setText("");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkbtn,menu);
        return true;
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
    void loadDB(){
        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://lsun41902.dothome.co.kr/GotoWork/Board/gotoworkloadDB.php";
                try {
                    URL url=new URL(serverUrl);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    //connection.setDoOutput(true);//outputStream 필요하면 사용
                    connection.setUseCaches(false);//트래픽 잠식요소 있음
                    InputStream is=connection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(isr);
                    final StringBuffer buffer=new StringBuffer();
                    String line=reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line=reader.readLine();
                    }
                    //읽어오는게 작업이 성공했는지 확인 작업
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new AlertDialog.Builder(getActivity()).setMessage(buffer.toString()).create().show();
//                        }
//                    });

                    //대량의 데이터 초기화

                    //읽어온 데이터 문자열에서 row별로 분리하기
                    String[] rows=buffer.toString().split(";");
                    for(String row: rows){
                        //한줄 데이터 에서 한 칸씩 분리 시키기\
                        String[] datas=row.split("&");
                        if(datas.length!=10) continue;
                        Object no=Integer.parseInt(datas[0]);
                        if(no==AdapterMember.numbuer){
                            String profileimg="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[1];
                            String title=datas[2];
                            String nickname=datas[3];
                            String date=datas[4];
                            String img1="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[5];
                            String img2="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[6];
                            String img3="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[7];
                            String text=datas[8];
                            String user=datas[9];
                            members.add(new MyMember(no,null,title,nickname,date,null,null,null,null,null,null,text,user));

                            etTitle.setText(datas[2]);
                            etText.setText(datas[8]);
                            Log.i("moyang2",title);
                            Log.i("moyang2",text);
                        }else {
                            continue;
                        }

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
