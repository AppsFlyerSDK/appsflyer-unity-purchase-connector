public interface IAppsFlyerPurchaseClientBuilder {
    AppsFlyerPurchaseClientBuilder setIsSandbox(bool isSandbox);
    AppsFlyerPurchaseClientBuilder setAutoLogSubscriptions(bool logSubscriptions);
    AppsFlyerPurchaseClientBuilder setAutoLogInApps(bool autoLogInApps);
    AppsFlyerPurchaseClientBuilder setSubscriptionPurchaseEventDataSource(bool enableSubscriptionPurchaseEventDataSource);
    AppsFlyerPurchaseClientBuilder setInAppPurchaseEventDataSource(bool enableInAppPurchaseEventDataSource);
    AppsFlyerPurchaseClientBuilder setSubscriptionValidationResultListener(bool enableSubscriptionValidation);
    AppsFlyerPurchaseClientBuilder setInAppValidationResultListener(bool enableInAppValidation);
    AppsFlyerPurchaseClientBuilder setStore(Store store);
}