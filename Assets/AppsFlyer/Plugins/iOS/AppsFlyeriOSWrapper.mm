#import "AppsFlyeriOSWrapper.h"

static void unityCallBack(NSString* objectName, const char* method, const char* msg) {
    if(objectName){
        UnitySendMessage([objectName UTF8String], method, msg);
    }
}

extern "C" {

    const void _startObservingTransactions() {

    }

    const void _stopObservingTransactions() {

    }

    const void _setIsSandbox(bool isSandBox) {

    }

    const void _setPurchaseRevenueDelegate() {

    }

    const void _setPurchaseRevenueDataSource() {

    }

    const void _setAutoLogPurchaseRevenue() {

    }

    const void _build() {
        
    }
}