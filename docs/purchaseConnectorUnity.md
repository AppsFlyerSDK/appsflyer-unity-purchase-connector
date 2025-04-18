---
title: Purchase Connector
category: 5f9705393c689a065c409b23
parentDoc: 6370c9e2441a4504d6bca3bd
order: 13
hidden: false
---

## Prerequisites

- iOS version 9 and higher.
- Unity AppsFlyer plugin **6.15.3**.
- Unity version **2020.3** and higher.
- Google Billing Play version **5.x.x** and **6.x.x**

To use the module with earlier Unity AppsFlyer plugin versions, check the previous versions of this module, for instance, **v1.0.0** supports versions **6.8.1** and higher.

## Adding The Connector To Your Project

1. Clone / download [Purchase Connector repository](https://github.com/AppsFlyerSDK/appsflyer-unity-purchase-connector).
2. [Import](https://docs.unity3d.com/Manual/AssetPackages.html) appsflyer-unity-purchase-connector-x.x.x.unitypackage  into your Unity project.
    - Go to Assets >> Import Package >> Custom Package
    - Select appsflyer-unity-adrepurchase-connector-x.x.x.unitypackage.

**Note:** You must have the [AppsFlyer Unity plugin](https://github.com/AppsFlyerSDK/appsflyer-unity-plugin) already in your project. In addition, make sure to init AppsFlyer SDK before Purchase Connector.

## ProGuard Rules

*Android Only* - If you are using ProGuard, add the following keep rules to your `proguard-rules.pro` file:

```groovy
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.Intrinsics{ *; }
-keep class kotlin.collections.**{ *; }
```
## Strict Mode
The module supports a Strict Mode which completely removes the IDFA collection functionality and AdSupport framework dependencies. Use the Strict Mode when developing apps for kids, for example.
Make sure to use strict mode module with AppsFlyer Unity strict mode plugin.

## Basic Integration

> *Note: before the implementation of the Purchase connector, please make sure to set up AppsFlyer `appId` and `devKey`*

### Set up Purchase Connector

> Notes:
>
> - The **AppsFlyerPurchaseConnector.init** api initialized the connector for both iOS and Android. However, the store parameter is only for Android stores.
> - You only need to call the API **AppsFlyerPurchaseConnector.init** once with the android store as parameter and it will work for both platforms.
> - For now, the only Android store available is Google.

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

### Log Auto-Renewable Subscriptions and In-App Purchases

Enables automatic logging of In-App purchases and Auto-renewable subscriptions.

```c#
AppsFlyerPurchaseConnector.setAutoLogPurchaseRevenue(AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsAutoRenewableSubscriptions, AppsFlyerAutoLogPurchaseRevenueOptions.AppsFlyerAutoLogPurchaseRevenueOptionsInAppPurchases);
```

> *Note: if `autoLogPurchaseRevenue` has not been set, it is disabled by default. The value is an option set, so you can choose what kind of user purchases you want to observe.*

### Get purchase validation callback

- In order to receive purchase validation event callbacks, you should conform to the purchase validation listener and implement the callback

```c#
AppsFlyerPurchaseConnector.setPurchaseRevenueValidationListeners(true);
/*  ... */

public void didReceivePurchaseRevenueValidationInfo(string validationInfo)
{
    AppsFlyer.AFLog("didReceivePurchaseRevenueValidationInfo", validationInfo);
    // deserialize the string as a dictionnary, easy to manipulate
    Dictionary<string, object> dictionary = AFMiniJSON.Json.Deserialize(validationInfo) as Dictionary<string, object>;

    // if the platform is Android, you can create an object from the dictionnary 
#if UNITY_ANDROID
    if (dictionary.ContainsKey("productPurchase") && dictionary["productPurchase"] != null)
    {
            // Create an object from the JSON string.
            InAppPurchaseValidationResult iapObject = JsonUtility.FromJson<InAppPurchaseValidationResult>(validationInfo);
    } elif (dictionary.ContainsKey("subscriptionPurchase") && dictionary["subscriptionPurchase"] != null) {
            SubscriptionValidationResult iapObject = JsonUtility.FromJson<SubscriptionValidationResult>(validationInfo);
    #endif

}
```

### Start Observing Transactions

`startObservingTransactions` should be called to start observing transactions.

```c#
    AppsFlyerPurchaseConnector.startObservingTransactions();
```

### Stop Observing Transactions

To stop observing transactions, you need to call `stopObservingTransactions`.

```c#
    AppsFlyerPurchaseConnector.stopObservingTransactions();
```

> *Note: if you called `stopObservingTransactions` API, you should set `autoLogPurchaseRevenue` value before you call `startObservingTransactions` next time.* 
> *Note: The Purchase Connector for Unity currently does not support adding Custom Parameters to purchase events*

## Testing the implementation in Sandbox

To set the sandbox environnment, you need to set `isSandbox` to true. </br>
For iOS, it will allow you to test in Xcode environment on a real device with TestFlight sandbox account. </br>
And for Android, it should be used while testing your [Google Play Billing Library integration](https://developer.android.com/google/play/billing/test). 

```c#
    AppsFlyerPurchaseConnector.setIsSandbox(true);
```

> *IMPORTANT NOTE: Before releasing your app to production please be sure to remove `isSandbox` or set it to `false`. If the production purchase event will be sent in sandbox mode, your event will not be validated properly! *

***

## Full Code Example

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
