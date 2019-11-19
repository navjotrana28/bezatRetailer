package com.bezatretailer.bezat.FCM;//package com.taxiayo.provider.FCM;
//
//import android.util.Log;
//
//import com.taxiayo.provider.Helper.SharedHelper;
//import com.taxiayo.provider.Utilities.Utilities;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//
//
//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
//    Utilities utils = new Utilities();
//
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        SharedHelper.putKey(getApplicationContext(),"device_token",""+refreshedToken);
//        Log.d(TAG, "onTokenRefresh: ");
//        utils.print(TAG, "onTokenRefresh" + refreshedToken);
//    }
//}
