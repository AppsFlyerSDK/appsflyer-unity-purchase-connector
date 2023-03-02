<img src="https://massets.appsflyer.com/wp-content/uploads/2018/06/20092440/static-ziv_1TP.png"  width="400" >

# appsflyer-unity-purchase-connector

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![GitHub tag](https://img.shields.io/github/v/release/AppsFlyerSDK/appsflyer-apple-purchase-connector)](https://img.shields.io/github/v/release/AppsFlyerSDK/appsflyer-unity-plugin)


🛠 In order for us to provide optimal support, we would kindly ask you to submit any issues to support@appsflyer.com

> *When submitting an issue please specify your AppsFlyer sign-up (account) email , your app ID , production steps, logs, code snippets and any additional relevant information.*


## Table Of Content
  * [This Plugin is Built for](#plugin-build-for)
  * [Adding The Connector To Your Project](#install-connector)
  * [ProGuard Rules](#proguard-rules)
  * [Basic Integration Of The Connector](#basic-integration)
    + [Set up Purchase Connector](#create-instance)
    + [Log Auto-Renewable Subscriptions and In-App Purchases](#log-subscriptions)
    + [Conform to Purchase Connector Data Source and Delegate protocols](#conforming)
    + [Start Observing Transactions](#start)
    + [Stop Observing Transactions](#stop)
  * [Testing the implementation in Sandbox](#testing)
  * [Full Code Examples](#example)


## <a id="plugin-build-for"> This Module is Built for
- iOS version 9 and higher.
- unity appsflyer plugin **6.8.1** and higher.
- Unity version **2020.3** and higher
- Google Billing Play version **4.x.x**

## <a id="install-connector">  Adding The Connector To Your Project:

1. Clone / download this repository.
2. [Import](https://docs.unity3d.com/Manual/AssetPackages.html) appsflyer-unity-purchase-connector-x.x.x.unitypackage  into your Unity project.
    * Go to Assets >> Import Package >> Custom Package
    * Select appsflyer-unity-adrepurchase-connector-x.x.x.unitypackage.

**Note:** You must have the [AppsFlyer Unity plugin](https://github.com/AppsFlyerSDK/appsflyer-unity-plugin) already in your project. In addition, make sure to init AppsFlyer SDK before Purchase Connector.

## <a id="proguard-rules"> ProGuard Rules
Android Only - If you are using ProGuard, add the following keep rules to your `proguard-rules.pro` file:

```groovy
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.Intrinsics{ *; }
-keep class kotlin.collections.**{ *; }
```

## <a id="basic-integration"> Basic Integration Of The Connector
> *Note: before the implementation of the Purchase connector, please make sure to set up AppsFlyer `appId` and `devKey`*

### <a id="create-instance"> Set up Purchase Connector
 
```c#
    using AppsFlyerSDK;
    using AppsFlyerConnector;

// Default SDK Implementation
   AppsFlyer.initSDK(devKey, appID, getConversionData ? this : null);

// Purchase connector implementation 
    AppsFlyerPurchaseConnector.init(this, AppsFlyerConnector.Store.GOOGLE);
    AppsFlyerPurchaseConnector.setIsSandbox(true);
    AppsFlyerPurchaseConnector.setAutoLogPurchaseRevenue(AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions, AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases);
    AppsFlyerPurchaseConnector.build();

```


### <a id="log-subscriptions"> Log Auto-Renewable Subscriptions and In-App Purchases

Enables automatic logging of In-App purchases and Auto-renewable subscriptions.
 
```c#
AppsFlyerPurchaseConnector.setAutoLogPurchaseRevenue(AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions, AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases);
```

> *Note: if `autoLogPurchaseRevenue` has not been set, it is disabled by default. The value is an option set, so you can choose what kind of user purchases you want to observe.*

### <a id="conforming"> Get purchase validation callback

* In order to receive purchase validation event callbacks, you should conform to the purchase validation listenner and implement the callback 

 
```c#
AppsFlyerPurchaseConnector.setPurchaseRevenueValidationListeners(true);
/*  ... */

public void didReceivePurchaseRevenueValidationInfo(string validationInfo)
{
    AppsFlyer.AFLog("didReceivePurchaseRevenueValidationInfo", validationInfo);
}

```


### <a id="start"> Start Observing Transactions

`startObservingTransactions` should be called to start observing transactions.

```c#
    AppsFlyerPurchaseConnector.startObservingTransactions();
```


### <a id="stop"> Stop Observing Transactions

To stop observing transactions, you need to call `stopObservingTransactions`.
 
```c#
    AppsFlyerPurchaseConnector.stopObservingTransactions();
```

> *Note: if you called `stopObservingTransactions` API, you should set `autoLogPurchaseRevenue` value before you call `startObservingTransactions` next time.* 

> *Note: The Purchase Connector for Unity currently does not support adding Custom Parameters to purchase events*

## <a id="testing"> Testing the implementation in Sandbox
To set the sandbox environnment, you need to set `isSandbox` to true. </br>
For iOS, it will allow you to test in Xcode environment on a real device with TestFlight sandbox account. </br>
And for Android, it should be used while testing your [Google Play Billing Library integration](https://developer.android.com/google/play/billing/test). 

```c#
    AppsFlyerPurchaseConnector.setIsSandbox(true);
```


> *IMPORTANT NOTE: Before releasing your app to production please be sure to remove `isSandbox` or set it to `false`. If the production purchase event will be sent in sandbox mode, your event will not be validated properly! *

***

## <a id="example"> Full Code Example

### Example
```c#
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AppsFlyerSDK;
using UnityEngine.UI;
using AppsFlyerConnector;

void Start()
    { 
        AppsFlyer.initSDK(devKey, appID, getConversionData ? this : null);
        AppsFlyer.setIsDebug(isDebug);
        AppsFlyerPurchaseConnector.init(this, AppsFlyerConnector.Store.GOOGLE);
        AppsFlyerPurchaseConnector.setIsSandbox(true);
        AppsFlyerPurchaseConnector.setAutoLogPurchaseRevenue(AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions, AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases);
        AppsFlyerPurchaseConnector.setPurchaseRevenueValidationListeners(true);
        AppsFlyerPurchaseConnector.build();
        AppsFlyerPurchaseConnector.startObservingTransactions();
        AppsFlyer.startSDK();
    }

  public void didReceivePurchaseRevenueValidationInfo(string validationInfo)
    {
        AppsFlyer.AFLog("didReceivePurchaseRevenueValidationInfo", validationInfo);
    }

```
