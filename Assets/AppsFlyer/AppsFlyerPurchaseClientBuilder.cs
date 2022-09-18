using UnityEngine;
using AppsFlyerConnector;

public class AppsFlyerPurchaseClientBuilder: IAppsFlyerPurchaseClientBuilder {
    
    private AppsFlyerPurchaseClient _client = new AppsFlyerPurchaseClient();
    
    public AppsFlyerConnector.AppsFlyerPurchaseConnector build(MonoBehaviour unityObject) {
        AppsFlyerConnector.AppsFlyerPurchaseConnector appsFlyerPurchaseConnector = new AppsFlyerConnector.AppsFlyerPurchaseConnector();
        appsFlyerPurchaseConnector.build(_client, unityObject);
        return appsFlyerPurchaseConnector;
    }

    public AppsFlyerPurchaseClientBuilder setIsSandbox(bool isSandbox) {
        _client.isSandbox = isSandbox;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setAutoLogSubscriptions(bool logSubscriptions) {
        _client.logSubscriptions = logSubscriptions;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setAutoLogInApps(bool autoLogInApps) {
        _client.autoLogInApps = autoLogInApps;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setSubscriptionPurchaseEventDataSource(bool enableSubscriptionPurchaseEventDataSource) {
        _client.enableSubscriptionPurchaseEventDataSource = enableSubscriptionPurchaseEventDataSource;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setInAppPurchaseEventDataSource(bool enableInAppPurchaseEventDataSource) {
        _client.enableInAppPurchaseEventDataSource = enableInAppPurchaseEventDataSource;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setSubscriptionValidationResultListener(bool enableSubscriptionValidation) {
        _client.enableSubscriptionValidationCallback = enableSubscriptionValidation;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setInAppValidationResultListener(bool enableInAppValidation) {
        _client.enableInAppValidationCallback = enableInAppValidation;
        return this;
    }
    public AppsFlyerPurchaseClientBuilder setStore (Store store) {
        _client.storeType = store;
        return this;
    }
}