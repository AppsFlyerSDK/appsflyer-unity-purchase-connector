package com.appsflyer.unity.ars;

import com.unity3d.player.UnityPlayer;

public class AppsFlyerAndroidWrapper {

    private static final String SUBSCRIPTION_PURCHASE_CALLBACK = "setSubscriptionPurchaseEventDataSource";
    private static final String INAPP_PURCHASE_CALLBACK = "setInAppPurchaseEventDataSource";

    private static PurchaseClient purchaseClientInstance;

    public static void build(String store, boolean isSandbox, boolean logSubscriptions, boolean autoLogInApps, Map<String, Object> subscriptionExtraParameters, Map<String, Object> inAppExtraParameters) {
        
        purchaseClientInstance = PurchaseClient.Builder(UnityPlayer.currentActivity, store)
        .logSubscriptions(logSubscriptions)
        .autoLogInApps(autoLogInApps)
        .setSandbox(isSandbox)
        .setSubscriptionPurchaseEventDataSource(new PurchaseClient.SubscriptionPurchaseEventDataSource() {
            @NonNull
            @Override
            public Map<String, Object> onNewPurchases(@NonNull List<? extends SubscriptionPurchaseEvent> purchaseEvents) {
                return subscriptionExtraParameters;
            }
        })
        .setInAppPurchaseEventDataSource(new PurchaseClient.InAppPurchaseEventDataSource() {
            @NonNull
            @Override
            public Map<String, Object> onNewPurchases(@NonNull List<? extends InAppPurchaseEvent> purchaseEvents) {
                return inAppExtraParameters;
            }
        })
        .setSubscriptionValidationResultListener(new PurchaseClient.SubscriptionPurchaseValidationResultListener() {
            @Override
            public void onResponse(@Nullable Map<String, ? extends SubscriptionValidationResult> result) {

            }

            @Override
            public void onFailure(@NonNull String result, @Nullable Throwable error) {

            }
        })
        .setInAppValidationResultListener(new PurchaseClient.InAppPurchaseValidationResultListener() {
            @Override
            public void onResponse(@Nullable Map<String, ? extends InAppPurchaseValidationResult> result) {

            }

            @Override
            public void onFailure(@Nonnull String result, @Nullable Throwable error) {

            }
        })
        .build();
    }

    public static void setIsSandbox() {

    }

    public static void setAutoLogSubscriptions() {

    }

    public static void setAutoLogInApps() {

    }

    public static void setSubscriptionPurchaseEventDataSource() {
        
    }

    public static void setInAppPurchaseEventDataSource() {
        
    }

    public static void startObservingTransactions() {
        purchaseClientInstance.startObservingTransactions();
    }

    public static void stopObservingTransactions() {
        purchaseClientInstance.stopObservingTransactions();
    }

    // private static void Builder(enum store) {
    //     purchaseClientInstance = new PurchaseClient.Builder(UnityPlayer.currentActivity, store);
    // }
}