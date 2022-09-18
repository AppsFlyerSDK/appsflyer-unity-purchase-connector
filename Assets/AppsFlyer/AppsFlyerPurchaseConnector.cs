using System.Collections.Generic;
using UnityEngine;
using System;

namespace AppsFlyerConnector
{
    
    public class AppsFlyerPurchaseConnector : MonoBehaviour {

        public static readonly string kAppsFlyerPurchaseConnectorVersion = "6.8.3";

#if UNITY_ANDROID && !UNITY_EDITOR
        private static AndroidJavaClass appsFlyerAndroidARS = new AndroidJavaClass("com.appsflyer.unity.afunitypurchaseconnector.AppsFlyerAndroidWrapper");
#endif

        public void build(AppsFlyerPurchaseClient client, MonoBehaviour unityObject) {
#if UNITY_IOS && !UNITY_EDITOR
        _build();

#elif UNITY_ANDROID && !UNITY_EDITOR

        // using(AndroidJavaClass cls_UnityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer")) {
        //     using(AndroidJavaObject cls_Activity = cls_UnityPlayer.GetStatic<AndroidJavaObject>("currentActivity")) {
        //         AndroidJavaObject cls_Application = cls_Activity.Call<AndroidJavaObject>("getApplication");
        //         appsFlyerAndroidARS.CallStatic("build", cls_Application);
        //     }
        // }

        appsFlyerAndroidARS.CallStatic("build", client.storeType, unityObject ? unityObject.name : null, client.isSandbox, client.logSubscriptions, client.autoLogInApps, client.subscriptionExtraParameters, client.inAppExtraParameters, client.listenSubscriptionValidation, client.listenInAppValidation);

#else

#endif
        }


        public static void startObservingTransactions() {

#if UNITY_IOS && !UNITY_EDITOR
                _startObservingTransactions();
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidARS.CallStatic("startObservingTransactions");
#else 
#endif
        }

        public static void stopObservingTransactions() {

#if UNITY_IOS && !UNITY_EDITOR
                _stopObservingTransactions();
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidARS.CallStatic("stopObservingTransactions");
#else
#endif
        }

        public static void setIsSandBox(bool isSandbox) {

#if UNITY_IOS && !UNITY_EDITOR
                _setIsSandbox(isSandbox);
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidARS.CallStatic("setSandbox", isSandbox);
#else
#endif
        }

        public static void setPurchaseRevenueDelegate() {

#if UNITY_IOS && !UNITY_EDITOR
                // delegate implementation
#endif
        }

        public static void setPurchaseRevenueDataSource() {

#if UNITY_IOS && !UNITY_EDITOR
                // delegate implementation
#endif
        }

        public static void setAutoLogPurchaseRevenue() {

#if UNITY_IOS && !UNITY_EDITOR
                // delegate implementation
#endif                
        }

#if UNITY_IOS && !UNITY_EDITOR

    [DllImport("__Internal")]
    private static extern void _startObservingTransactions();
    [DllImport("__Internal")]
    private static extern void _stopObservingTransactions();
    [DllImport("__Internal")]
    private static extern void _setIsSandbox(bool isSandbox);
    [DllImport("__Internal")]
    private static extern void _setPurchaseRevenueDelegate();
    [DllImport("__Internal")]
    private static extern void _setPurchaseRevenueDataSource();
    [DllImport("__Internal")]
    private static extern void _setAutoLogPurchaseRevenue();
    [DllImport("__Internal")]
    private static extern void _build();

#endif
    }
    public enum Store {
    GOOGLE
}
}