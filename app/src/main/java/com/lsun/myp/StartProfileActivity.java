package com.lsun.myp;

import android.Manifest;
import android.content.ContentResolver;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    CircleImageView startProfileCircleImage;
    public static final int REQ_STARTPICIMAGE = 1001;
    public static Uri startProfileImage;
    EditText username;
    TextView userEmail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference profileRef;
    public static String startusernickname;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    public static String profileImg;
    boolean ok =false;
    public static boolean img = false;
    ItemChat itemChat;
    ItemProfile itemProfile;
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_start_profile);
        getSupportActionBar().setTitle("설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startProfileCircleImage = findViewById(R.id.start_profile_iv);
        startProfileCircleImage.setImageResource(R.drawable.personmen);
        startProfileCircleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, REQ_STARTPICIMAGE);
            }
        });
        username = findViewById(R.id.profile_et_userenickname);
        userEmail = findViewById(R.id.profile_et_useremail);
        userEmail.setText(SelectLoginActivity.startEmail);
        firebaseDatabase = FirebaseDatabase.getInstance();
        profileRef = firebaseDatabase.getReference("profiles");
        itemProfile=new ItemProfile();



    }
    //requestPermissions()메소드로 인해 보여지는 다이얼로그에서 [허가/거부]선택 후 결과콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "외부메모리 기능 사용 제한", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "외부메모리 사용 가능", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                    startActivityForResult(intent, REQ_STARTPICIMAGE);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_STARTPICIMAGE:
                if (resultCode == RESULT_OK) {
                    img = true;
                    startProfileImage = data.getData();
                    profileImg = getRealPathFromUri(startProfileImage);
                    Glide.with(this).load(startProfileImage).into(startProfileCircleImage);
                }
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("userName", MODE_PRIVATE);
        String checkUserName = sp.getString("userNickname", null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkedPrmission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐
            if (checkedPrmission == PackageManager.PERMISSION_DENIED) {//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }

        }
        if (checkUserName != null) {
            startusernickname=checkUserName;
            startActivity(new Intent(StartProfileActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkbtn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(ok ==true){
        switch (item.getItemId()) {
            case R.id.check_write:
                new AlertDialog.Builder(this).setTitle("작성 완료").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (img == false) {
                            startProfileImage = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.personmen));

                        }
                        if (username.length() >= 2) {
                            SharedPreferences sp = getSharedPreferences("userName", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            startusernickname = username.getText().toString();
                            itemChat.setNickName(username.getText().toString());
                            itemChat.setUrlstring(profileImg);
                            editor.putString("userNickname", itemChat.getNickName());
                            editor.commit();
                            Intent intent = getIntent();
                            intent.putExtra("circleUri", startProfileImage);
                            setResult(RESULT_OK, intent);
                            //일단 firebase storage 에 이미지 저장
                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            Log.i("mojing",itemChat.getNickName());
                            final StorageReference imgRef = firebaseStorage.getReference("profileImages/"+itemChat.getNickName()+"/" + "first.png");
                            imgRef.putFile(startProfileImage);
                            UploadTask uploadTask = imgRef.putFile(startProfileImage);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //이미지 업로드가 성공되었으므로...곧바로 Firebase storage 에 이미지 파일 다운로드 URL을 얻어오겠습니다.
                                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //파라미터로 firebase 저장소에 저장되어 있는 다운로드주소 (URL)을 문자열로 얻어오기

                                            ItemChat.Urlstring=uri.toString();
                                            itemProfile.setImguri(uri.toString());
                                            profileRef.child(itemChat.getNickName()).setValue(itemProfile);

                                        }
                                    });
                                }
                            });
                            itemProfile.setName(username.getText().toString());
                            sp=getSharedPreferences("token",MODE_PRIVATE);
                            String token=sp.getString("token",null);
                            Log.i("tokenemail","Token: "+token);
                            itemProfile.setUuid(token);
                            sp=getSharedPreferences("email",MODE_PRIVATE);
                            String email=sp.getString("Email",null);
                            Log.i("tokenemail","Email: "+email);

                            itemProfile.setEmail(email);
                            profileRef.child(itemChat.getNickName()).setValue(itemProfile);
                            startActivity(new Intent(StartProfileActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(StartProfileActivity.this, "닉네임을 2자 이상 작성해 주세요.", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }

                    }

                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                break;
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                new AlertDialog.Builder(this).setTitle("뒤로 가기").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.getInstance().signOut();
                        startActivity(new Intent(StartProfileActivity.this, SelectLoginActivity.class));
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
        }else {
            Toast.makeText(this, "중복 확인이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

    public void clickCheck(View view) {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot t: dataSnapshot.getChildren()){
                    String n=t.getKey();

//                    ItemProfile get=t.getValue(ItemProfile.class);
//                    String[] info={get.name,get.email,get.uuid,get.imguri};
//                    String Result=setTextLength(info[0],10)+setTextLength(info[1],30)+setTextLength(info[1],200)+setTextLength(info[1],100);
//                    arrayData.add(Result);
//                    arrayIndex.add(n);
//                    Log.d("getFirebaseDatabase", "key: " + n);
//                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3]);
//
                    if(n.equals(username.getText().toString())){
                        Toast.makeText(StartProfileActivity.this, "닉네임 중복, 사용 불가", Toast.LENGTH_SHORT).show();
                        ok=false;
                        return;
                    }
                    ok=true;
                }
                if(ok==true){
                    if(username.length()>=2){
                        Toast.makeText(StartProfileActivity.this, "닉네임 사용가능", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(StartProfileActivity.this, "닉네임을 2자 이상 작성해 주세요.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

}
