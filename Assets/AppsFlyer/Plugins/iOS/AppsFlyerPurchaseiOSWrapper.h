//  AppsFlyerPurchaseiOSWarpper.h
//  Unity-iPhone


#import "UnityAppController.h"
#if __has_include(<PurchaseConnector/PurchaseConnector.h>)
#import  <PurchaseConnector/PurchaseConnector.h>
#else
#import "PurchaseConnector.h"
#endif



@interface AppsFlyerPurchaseiOSWrapper : NSObject <AppsFlyerPurchaseRevenueDelegate, AppsFlyerPurchaseRevenueDataSource>
@end

static AppsFlyerPurchaseiOSWrapper *_AppsFlyerPurchasedelegate;

static const char* PURCHASE_REVENUE_VALIDATION_CALLBACK = "didReceivePurchaseRevenueValidationInfo";
