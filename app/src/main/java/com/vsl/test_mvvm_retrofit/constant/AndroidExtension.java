package com.vsl.test_mvvm_retrofit.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public interface AndroidExtension {

    static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }
}
