using System.Collections.Generic;
using UnityEngine;
using System;

namespace AppsFlyerConnector
{
    
    public class AppsFlyerPurchaseConnector : MonoBehaviour {

        public static readonly string kAppsFlyerPurchaseConnectorVersion = "6.8.3.5";

#if UNITY_ANDROID && !UNITY_EDITOR
        private static AndroidJavaClass appsFlyerAndroidConnector = new AndroidJavaClass("com.appsflyer.unity.afunitypurchaseconnector.AppsFlyerAndroidWrapper");
#endif

        public static void build() {
#if UNITY_IOS && !UNITY_EDITOR
        _build();
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidConnector.CallStatic("build");

#else
#endif
        }

        public static void init(MonoBehaviour unityObject, Store s) {
#if UNITY_ANDROID && !UNITY_EDITOR
                int store = mapStoreToInt(s);
                appsFlyerAndroidConnector.CallStatic("init", unityObject ? unityObject.name : null, store);
#else
#endif
        }
        public static void startObservingTransactions() {
#if UNITY_IOS && !UNITY_EDITOR
                _startObservingTransactions();
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidConnector.CallStatic("startObservingTransactions");
#else 
#endif
        }

        public static void stopObservingTransactions() {
#if UNITY_IOS && !UNITY_EDITOR
                _stopObservingTransactions();
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidConnector.CallStatic("stopObservingTransactions");
#else
#endif
        }

        public static void setIsSandbox(bool isSandbox) {
#if UNITY_IOS && !UNITY_EDITOR
                _setIsSandbox(isSandbox);
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidConnector.CallStatic("setIsSandbox", isSandbox);
#else
#endif
        }

        public static void setPurchaseRevenueValidationListeners(bool enableCallbacks) {
#if UNITY_IOS && !UNITY_EDITOR
                //ios
#elif UNITY_ANDROID && !UNITY_EDITOR
                appsFlyerAndroidConnector.CallStatic("setPurchaseRevenueValidationListeners", enableCallbacks);
#else
#endif
        }

        public static void setAutoLogPurchaseRevenue(AppsFlyerAutoLogPurchaseRevenueOptions[] options) {
#if UNITY_IOS && !UNITY_EDITOR
                _setAutoLogPurchaseRevenue(options);
#elif UNITY_ANDROID && !UNITY_EDITOR
                if (options.Length == 0) {
                        return;
                }
                foreach (AppsFlyerAutoLogPurchaseRevenueOptions op in options) {
                        switch(op) {
                                case AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsDisabled:
                                        break;
                                case AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions:
                                        appsFlyerAndroidConnector.CallStatic("setAutoLogSubscriptions", true);
                                        break;
                                case AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases:
                                        appsFlyerAndroidConnector.CallStatic("setAutoLogInApps", true);
                                        break;
                                default:
                                        break;
                        }
                }
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

        private static int mapStoreToInt(Store s) {
                switch(s) {
                        case(Store.GOOGLE):
                                return 0;
                        default:
                                return -1;
                }
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
    GOOGLE = 0
    }
    public enum AppsFlyerAutoLogPurchaseRevenueOptions
    {
        AppsFlyerAutoLogPurchaseRevenueOptionsDisabled = 0,
        AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions = 1,
        AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases = 2
    }
}