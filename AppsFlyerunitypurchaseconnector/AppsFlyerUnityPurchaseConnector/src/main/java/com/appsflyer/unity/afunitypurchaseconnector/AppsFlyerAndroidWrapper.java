package com.appsflyer.unity.afunitypurchaseconnector;

import android.util.Log;

import androidx.annotation.NonNull;

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

    private static final String SUBSCRIPTION_DATA_CALLBACK = "onSubscriptionPurchaseEventDataSourceSet";
    private static final String INAPP_DATA_CALLBACK = "onInAppPurchaseEventDataSourceSet";

    private static final String SUBSCRIPTION_VALIDATION_CALLBACK = "onSubscriptionValidation";
    private static final String INAPP_VALIDATION_CALLBACK = "onInAppValidation";

    private static PurchaseClient purchaseClientInstance;
    private static String unityObjectName;
    private static String unityCallbackResult;

    public static void build(Store store, String objectName, boolean isSandbox, boolean logSubscriptions, boolean autoLogInApps, boolean enableSubscriptionPurchaseEventDataSource, boolean enableInAppPurchaseEventDataSource, boolean enableSubValidationCallback, boolean enableInAppValidationCallback) {
        Log.d("PurchaseConnector", "build");
        unityObjectName = objectName;
        purchaseClientInstance = new PurchaseClient.Builder(UnityPlayer.currentActivity, store)
                .logSubscriptions(logSubscriptions)
                .autoLogInApps(autoLogInApps)
                .setSandbox(isSandbox)
                .setSubscriptionPurchaseEventDataSource(new PurchaseClient.SubscriptionPurchaseEventDataSource() {
                    @NotNull
                    @Override
                    public Map<String, Object> onNewPurchases(@NotNull List<? extends SubscriptionPurchaseEvent> purchaseEvents) {
                        Map<String, Object> map = new HashMap<>();
                        if (enableSubscriptionPurchaseEventDataSource && unityObjectName != null) {
                            JSONArray jsonArray = new JSONArray(purchaseEvents);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("list", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String str = unitySendMessageExtended(unityObjectName, SUBSCRIPTION_DATA_CALLBACK, jsonObject.toString());
                            Log.d("PurchaseConnector", str);

                            String[] pairs = str.split(",");
                            for (String pair : pairs) {
                                String[] keyValue = pair.split(":");
                                map.put(keyValue[0], Integer.valueOf(keyValue[1]));
                            }
                        }
                        return map;
                    }
                })
                .setInAppPurchaseEventDataSource(new PurchaseClient.InAppPurchaseEventDataSource() {
                    @NotNull
                    @Override
                    public Map<String, Object> onNewPurchases(@NotNull List<? extends InAppPurchaseEvent> purchaseEvents) {
                        Map<String, Object> map = new HashMap<>();
                        if (enableInAppPurchaseEventDataSource && unityObjectName != null) {
                            JSONArray jsonArray = new JSONArray(purchaseEvents);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("list", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String str = unitySendMessageExtended(unityObjectName, INAPP_DATA_CALLBACK, jsonObject.toString());
                            Log.d("PurchaseConnectory", str);

                            String[] pairs = str.split(",");
                            for (String pair : pairs) {
                                String[] keyValue = pair.split(":");
                                map.put(keyValue[0], Integer.valueOf(keyValue[1]));
                            }
                        }
                        return map;
                    }
                })
                .setSubscriptionValidationResultListener(new PurchaseClient.SubscriptionPurchaseValidationResultListener() {
                    @Override
                    public void onResponse(@Nullable Map<String, ? extends SubscriptionValidationResult> result) {
                        if (enableSubValidationCallback && unityObjectName != null) {
                            JSONObject jsonObject = new JSONObject(result);
                            UnityPlayer.UnitySendMessage(unityObjectName, SUBSCRIPTION_VALIDATION_CALLBACK, jsonObject.toString());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull String result, @Nullable Throwable error) {
                        if (enableSubValidationCallback && unityObjectName != null) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("result", result);
                            map.put("errorDescription", error);
                            JSONObject jsonObject = new JSONObject(map);
                            UnityPlayer.UnitySendMessage(unityObjectName, SUBSCRIPTION_VALIDATION_CALLBACK, jsonObject.toString());
                        }
                    }
                })
                .setInAppValidationResultListener(new PurchaseClient.InAppPurchaseValidationResultListener() {
                    @Override
                    public void onResponse(@Nullable Map<String, ? extends InAppPurchaseValidationResult> result) {
                        if (enableInAppValidationCallback && unityObjectName != null) {
                            JSONObject jsonObject = new JSONObject(result);
                            UnityPlayer.UnitySendMessage(unityObjectName, INAPP_VALIDATION_CALLBACK, jsonObject.toString());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull String result, @Nullable Throwable error) {
                        if (enableInAppValidationCallback && unityObjectName != null) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("result", result);
                            map.put("errorDescription", error);
                            JSONObject jsonObject = new JSONObject(map);
                            UnityPlayer.UnitySendMessage(unityObjectName, INAPP_VALIDATION_CALLBACK, jsonObject.toString());
                        }
                    }
                })
                .build();
    }

    // public static void setIsSandbox() {

    // }

    // public static void setAutoLogSubscriptions() {

    // }

    // public static void setAutoLogInApps() {

    // }

    // public static void setSubscriptionPurchaseEventDataSource() {
        
    // }

    // public static void setInAppPurchaseEventDataSource() {
        
    // }

    public static void startObservingTransactions() {
        purchaseClientInstance.startObservingTransactions();
    }

    public static void stopObservingTransactions() {
        purchaseClientInstance.stopObservingTransactions();
    }

    public static void setUnityObjectName(String objectName) {
        unityObjectName = objectName;
    }

    public static String unitySendMessageExtended(String gameObject, String functionName, String funcParam) {
        UnityPlayer.UnitySendMessage(gameObject, functionName, funcParam);
        String result = unityCallbackResult;
        return result;
    }

    public static void receiveResult(String str) {
//        unityCallbackResult = "";
        unityCallbackResult = str;
    }
}