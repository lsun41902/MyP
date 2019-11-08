package com.lsun.myp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;

public class GoogleLogout {
    private static GoogleApiClient mGoogleApiClient;
    Context context;

    public GoogleLogout(GoogleApiClient mGoogleApiClient, Context context) {
        this.mGoogleApiClient = mGoogleApiClient;
        this.context = context;
    }

    public void logout() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if(mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d("TAG", "User Logged out");
                                Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "아무일도 없었다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("TAG", "Google API Client Connection Suspended");
            }
        });
    }

}

