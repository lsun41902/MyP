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
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class PostActivity extends AppCompatActivity {
    EditText etTitle, etText;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    Uri writeImage1, writeImage2, writeImage3;
    ImageView iv1, iv2, iv3;
    public static final int REQ_WRITEIMAGE1 = 1004;
    public static final int REQ_WRITEIMAGE2 = 1005;
    public static final int REQ_WRITEIMAGE3 = 1006;
    Date date = new Date(System.currentTimeMillis());
    String img1;
    String img2;
    String img3;
    int number;
    ArrayList<MyMember> members = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //number=Integer.parseInt(AdapterMember.numbuer.toString());
        etTitle = findViewById(R.id.write_et_title);
        etText = findViewById(R.id.write_et_text);
        getSupportActionBar().setTitle("수정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //퍼미션
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkedPrmission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐
            if (checkedPrmission == PackageManager.PERMISSION_DENIED) {//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }

        iv1 = findViewById(R.id.write_iv_1);
        //Glide.with(this).load().into(iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, REQ_WRITEIMAGE1);
            }
        });
        iv2 = findViewById(R.id.write_iv_2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, REQ_WRITEIMAGE2);
            }
        });
        iv3 = findViewById(R.id.write_iv_3);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, REQ_WRITEIMAGE3);
            }
        });

        //loadDB();
        Bundle bundle=getIntent().getExtras();
        String title=bundle.getString("title");
        String text=bundle.getString("text");
        etText.setText(text);
        etTitle.setText(title);

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {

            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "수정을 취소하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_WRITEIMAGE1:
                if (resultCode == RESULT_OK) {
                    writeImage1 = data.getData();
                    img1 = getRealPathFromUri(writeImage1);
                    Glide.with(PostActivity.this).load(writeImage1).into(iv1);
                }
                break;
            case REQ_WRITEIMAGE2:
                if (resultCode == RESULT_OK) {
                    writeImage2 = data.getData();
                    img2 = getRealPathFromUri(writeImage2);
                    Glide.with(PostActivity.this).load(writeImage2).into(iv2);
                }
                break;
            case REQ_WRITEIMAGE3:
                if (resultCode == RESULT_OK) {
                    writeImage3 = data.getData();
                    img3 = getRealPathFromUri(writeImage3);
                    Glide.with(PostActivity.this).load(writeImage3).into(iv3);
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_write:
                if (etText.length()<=20){

                    new AlertDialog.Builder(this).setPositiveButton("나가기", new DialogInterface.OnClickListener() {
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
                            Intent intent = getIntent();
                            if (title.equals("")) {
                                title = "제목없음";
                            }
                            intent.putExtra("Title", title);
                            intent.putExtra("Text",text);
                            intent.putExtra("Image1",writeImage1);
                            intent.putExtra("Image2",writeImage2);
                            intent.putExtra("Image3",writeImage3);
                            String userId=SelectLoginActivity.startEmail;
                            intent.putExtra("userID",userId);
                            setResult(RESULT_OK, intent);

                            String serverUri="http://lsun41902.dothome.co.kr/GotoWork/Board/gotoworkDB.php";
                            SimpleMultiPartRequest simpleMultiPartRequest=new SimpleMultiPartRequest(Request.Method.POST, serverUri, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("moyang",response);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(PostActivity.this, "에러", Toast.LENGTH_SHORT).show();
                                    Log.i("moyang",String.valueOf(error));
                                }
                            });
                            simpleMultiPartRequest.addStringParam("title",title);
                            simpleMultiPartRequest.addStringParam("text",text);
                            simpleMultiPartRequest.addStringParam("userID",SelectLoginActivity.startEmail);
                            simpleMultiPartRequest.addStringParam("nickName",ItemChat.nickName);
                            simpleMultiPartRequest.addFile("img1",img1);
                            simpleMultiPartRequest.addFile("img2",img2);
                            simpleMultiPartRequest.addFile("img3",img3);
                            simpleMultiPartRequest.addFile("profileImg",StartProfileActivity.profileImg);
                            RequestQueue requestQueue= Volley.newRequestQueue(PostActivity.this);
                            requestQueue.add(simpleMultiPartRequest);
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
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
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

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "외부메모리 기능 사용 제한", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "외부메모리 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkbtn, menu);
        return true;
    }

    String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    void loadDB() {

        //서버의 loadDBtoJson.php 가져오기
        //접속하여 DB데이터들 결과받기
        String serverUrl="http://lsun41902.dothome.co.kr/GotoWork/Board/gotoworkloadDB.php";
        //JsonObjectRequest 하나짜리를 할때는 이거
        //결과를 배열로 받을 것이므로 JsonArrayRequest 를 이용할것임
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //파라미터로 응답받은 결과 JsonArray 를 분석
                try {
                    Log.i("moyang",response+"");

                    for(int i=0;i<response.length();i++){
                        Log.i("moyang","여기는 왓냐?");
                        JSONObject jsonObject=response.getJSONObject(number-1);
                        Log.i("moyang1",number+"");
                            Log.i("moyang","if");
                        int no=Integer.parseInt(jsonObject.getString("no"));
                            String title=jsonObject.getString("title");
                            String nickname=jsonObject.getString("nickname");
                            String date=jsonObject.getString("date");
                            //String imgPath=jsonObject.getString("image");
                            String text=jsonObject.getString("text");
                            String userid=jsonObject.getString("userid");
                            //이미지는 ip가 제외된 주소이므로 바로 사용불가
                            //imgPath="http://lsun41902.dothome.co.kr/Android/"+imgPath;
                            etTitle.setText(title);
                            etText.setText(text);
                            Log.i("moyang",number+"");
                            Log.i("moyang",title);
                            Log.i("moyang",text);


                    }


                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });//Volley 에서는 POST 권장 GET방식은 먼가 이상함
        //실제 요청작업을 수행해주는 요청큐 객체 생성
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }


}
