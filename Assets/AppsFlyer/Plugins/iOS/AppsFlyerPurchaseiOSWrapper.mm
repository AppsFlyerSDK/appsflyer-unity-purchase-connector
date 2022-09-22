#import "AppsFlyerPurchaseiOSWrapper.h"

static void unityCallBack(NSString* objectName, const char* method, const char* msg) {
    if(objectName){
        UnitySendMessage([objectName UTF8String], method, msg);
    }
}

extern "C" {

    const void _startObservingTransactions() {
        [[PurchaseConnector shared] startObservingTransactions];
    }

    const void _stopObservingTransactions() {
        [[PurchaseConnector shared] stopObservingTransactions];
    }

    const void _setIsSandbox(bool isSandBox) {
        [[PurchaseConnector shared] setIsSandbox:isSandBox];
    }

    const void _setPurchaseRevenueDelegate() {
        if (_AppsFlyerPurchasedelegate == nil) {
            _AppsFlyerPurchasedelegate = [[AppsFlyerPurchaseiOSWrapper alloc] init];
               }
        [[PurchaseConnector shared] setPurchaseRevenueDelegate:_AppsFlyerPurchasedelegate];
    }

    const void _setPurchaseRevenueDataSource() {
        if (_AppsFlyerPurchasedelegate == nil) {
            _AppsFlyerPurchasedelegate = [[AppsFlyerPurchaseiOSWrapper alloc] init];
               }
        [[PurchaseConnector shared] setPurchaseRevenueDataSource:_AppsFlyerPurchasedelegate];
    }

    const void _setAutoLogPurchaseRevenue() {
//            [[PurchaseConnector shared] setAutoLogPurchaseRevenue:AFSDKAutoLogPurchaseRevenueOptionsRenewable];

    }

    const void _build() {
        
    }
}

@implementation AppsFlyerPurchaseiOSWrapper

- (void)didReceivePurchaseRevenueValidationInfo:(NSDictionary *)validationInfo error:(NSError *)error {
    NSLog(@"Validation info: %@", validationInfo);
    NSLog(@"Error: %@", error);
//    unityCallBack()
    // Process validation info
}

- (NSDictionary *)purchaseRevenueAdditionalParametersForProducts:(NSSet<SKProduct *> *)products transactions:(NSSet<SKPaymentTransaction *> *)transactions {
    return @{@"key1" : @"param1"};
}

@end
