package com.example.wallpaper.utility;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class Networklivedata extends LiveData<Boolean> {
    private final ConnectivityManager connectivityManager;


    public Networklivedata(Application application) {
        connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private final ConnectivityManager.NetworkCallback networkCallback=new ConnectivityManager.NetworkCallback(){
        @Override
        public void onAvailable(Network network){
            postValue(true);
            Log.e(utility.tag,"post value is true");
        }

        @Override
        public void onLost(Network network){
            postValue(false);
            utility.log("postvalue false");
        }
    };
    @Override
    protected void onActive(){
        NetworkRequest networkRequest=new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(networkRequest,networkCallback);
        checkCurrentNetworkState();
    }

    @Override
    protected void onInactive(){
        connectivityManager.unregisterNetworkCallback(networkCallback);
        checkCurrentNetworkState();
    }

    // Optional: Manually check the current network state when LiveData becomes active
    private void checkCurrentNetworkState() {
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        postValue(isConnected);
    }

}
