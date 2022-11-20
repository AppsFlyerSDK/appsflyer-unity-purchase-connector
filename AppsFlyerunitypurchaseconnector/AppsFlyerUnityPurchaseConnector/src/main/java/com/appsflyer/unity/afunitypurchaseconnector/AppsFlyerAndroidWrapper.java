package com.appsflyer.unity.afunitypurchaseconnector;

import android.util.Log;

import androidx.annotation.NonNull;

import android.util.Log;
import com.appsflyer.api.InAppPurchaseEvent;
import com.appsflyer.api.PurchaseClient;
import com.appsflyer.api.Store;
import com.appsflyer.api.SubscriptionPurchaseEvent;
import com.appsflyer.internal.models.InAppPurchaseValidationResult;
import com.appsflyer.internal.models.SubscriptionValidationResult;
import com.unity3d.player.UnityPlayer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppsFlyerAndroidWrapper {

    private static final String VALIDATION_CALLBACK = "didReceivePurchaseRevenueValidationInfo";
    private static final String ERROR_CALLBACK = "didReceivePurchaseRevenueError";

    private static PurchaseClient purchaseClientInstance;
    private static PurchaseClient.Builder builder;

    private static String unityObjectName;
    // private static String unityCallbackResult;

    public static void build() {
        if (builder != null) {
            purchaseClientInstance = builder.build();
        } else {
            Log.w("AppsFlyer_Connector", "[PurchaseConnector]: Initialization is required prior to building.");
        }
    }

    public static void init(String objectName, int store) {
        unityObjectName = objectName;
        Store s = mappingEnum(store);
        if (s != null) {
            builder = new PurchaseClient.Builder(UnityPlayer.currentActivity, s);
        } else {
            Log.w("AppsFlyer_Connector", "[PurchaseConnector]: Please choose a valid store.");
        }
    }

    public static void setIsSandbox(boolean isSandbox) {
        if (builder != null) {
            builder.setSandbox(isSandbox);
        }
    }

    public static void setAutoLogSubscriptions(boolean logSubscriptions) {
        if (builder != null) {
            builder.logSubscriptions(logSubscriptions);
        }
    }

    public static void setAutoLogInApps(boolean autoLogInApps) {
        if (builder != null) {
            builder.autoLogInApps(autoLogInApps);
         }
    }

    public static void setPurchaseRevenueValidationListeners(boolean enableCallbacks) {
        if (builder != null && enableCallbacks) {
            builder.setSubscriptionValidationResultListener(new PurchaseClient.SubscriptionPurchaseValidationResultListener() {
                @Override
                public void onResponse(@Nullable Map<String, ? extends SubscriptionValidationResult> result) {
                    if (unityObjectName != null) {
                        Map<String,String> map = new HashMap<>();
                        for (Map.Entry<String, ? extends SubscriptionValidationResult> entry : result.entrySet()) {
                            map.put(entry.getKey(), entry.getValue().toString());
                        }
                        JSONObject jsonObject = new JSONObject(map);
                        UnityPlayer.UnitySendMessage(unityObjectName, VALIDATION_CALLBACK, jsonObject.toString());
                    }
                }
    
                @Override
                public void onFailure(@NonNull String result, @Nullable Throwable error) {
                    if (unityObjectName != null) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("result", result);
                        map.put("errorDescription", error.toString());
                        JSONObject jsonObject = new JSONObject(map);
                        UnityPlayer.UnitySendMessage(unityObjectName, ERROR_CALLBACK, jsonObject.toString());
                    }
                }
            });
    
            builder.setInAppValidationResultListener(new PurchaseClient.InAppPurchaseValidationResultListener() {
                @Override
                public void onResponse(@Nullable Map<String, ? extends InAppPurchaseValidationResult> result) {
                    if (unityObjectName != null) {
                        Map<String,String> map = new HashMap<>();
                        for (Map.Entry<String, ? extends InAppPurchaseValidationResult> entry : result.entrySet()) {
                            map.put(entry.getKey(), entry.getValue().toString());
                        }
                        JSONObject jsonObject = new JSONObject(map);
                        UnityPlayer.UnitySendMessage(unityObjectName, VALIDATION_CALLBACK, jsonObject.toString());
                    }
                }
    
                @Override
                public void onFailure(@NonNull String result, @Nullable Throwable error) {
                    if (unityObjectName != null) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("result", result);
                        map.put("errorDescription", error.toString());
                        JSONObject jsonObject = new JSONObject(map);
                        UnityPlayer.UnitySendMessage(unityObjectName, ERROR_CALLBACK, jsonObject.toString());
                    }
                }
            });
        }
    }

    
    public static void startObservingTransactions() {
        if (purchaseClientInstance != null) {
            purchaseClientInstance.startObservingTransactions();
        } else {
            Log.w("AppsFlyer_Connector", "[PurchaseConnector]: startObservingTransactions was not called because the purchase client instance is null, please call build() prior to this function.");
        }
    }

    public static void stopObservingTransactions() {
        if (purchaseClientInstance != null) {
            purchaseClientInstance.stopObservingTransactions();
        } else {
            Log.w("AppsFlyer_Connector", "[PurchaseConnector]: stopObservingTransactions was not called because the purchase client instance is null, please call build() prior to this function.");
        }
    }

    // public static void setUnityObjectName(String objectName) {
    //     unityObjectName = objectName;
    // }

//     public static String unitySendMessageExtended(String gameObject, String functionName, String funcParam) {
//         UnityPlayer.UnitySendMessage(gameObject, functionName, funcParam);
//         String result = unityCallbackResult;
//         return result;
//     }

//     public static void receiveResult(String str) {
// //        unityCallbackResult = "";
//         unityCallbackResult = str;
//     }

    private static Store mappingEnum(int storeEnum) {
        Store s;
        switch(storeEnum) {
            case 0:
                s = Store.GOOGLE;
                break;
            default:
               return null;
        }
        return s;
    }
}