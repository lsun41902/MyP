package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SelectLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    
    SignInButton signInButton;
    public static String startEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selcet_login);
        signInButton = findViewById(R.id.signinbutton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Googlelogin();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

    }

    void Googlelogin() {
        // 파이어베이스 인증 객체 선언
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
    }
public void  updateUI(FirebaseUser account){
    if(account != null){
        Toast.makeText(this,"로그인 성공",Toast.LENGTH_LONG).show();
        startEmail= account.getEmail();
        startActivity(new Intent(this,StartProfileActivity.class));
    }else {
        Toast.makeText(this,"로그인을 선택해주세요",Toast.LENGTH_LONG).show();
    }
}

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SelectLoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SelectLoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(SelectLoginActivity.this, StartProfileActivity.class));
//                            finish();
//                        } else {
//                            Toast.makeText(SelectLoginActivity.this, "에러가 발생 했습니다", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(SelectLoginActivity.this, "구글 인증 실패", Toast.LENGTH_SHORT).show();

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                GoogleSignInAccount account = result.getSignInAccount();
//                firebaseAuthWithGoogle(account);
////                Log.d("TAG", "이름 =" + account.getDisplayName());
//                startEmail= account.getEmail();
////                Log.d("TAG", "getId()=" + account.getId());
////                Log.d("TAG", "getAccount()=" + account.getAccount());
////                Log.d("TAG", "getIdToken()=" + account.getIdToken());
//            } else {
//                Toast.makeText(SelectLoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }
//    public void onStart()
//    { // 사용자가 현재 로그인되어 있는지 확인
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null)
//        { // 만약 로그인이 되어있으면 다음 액티비티 실행
//            Intent intent = new Intent(SelectLoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    public void Signout() {
        mAuth.getInstance().signOut();

    }


    public void clickLogout(View view) {
        //new GoogleLogout(mGoogleApiClient,this).logout();
        new AlertDialog.Builder(view.getContext()).setMessage("로그아웃 하시겠습니까?").setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 네 클릭
                        // 로그아웃 함수 call
                        Signout();
                        Toast.makeText(SelectLoginActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // 아니오 클릭. dialog 닫기.
                dialog.cancel();
            }
        }).create().show();
    }


}