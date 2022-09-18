public class AppsFlyerPurchaseClient {
    public Store storeType { get; set; }
    public bool isSandbox { get; set; }
    public bool logSubscriptions { get; set; }
    public bool autoLogInApps { get; set; }
    public bool enableSubscriptionPurchaseEventDataSource { get; set; }
    public bool enableInAppPurchaseEventDataSource { get; set; }
    public bool enableSubscriptionValidationCallback { get; set; }
    public bool enableInAppValidationCallback { get; set; }
}