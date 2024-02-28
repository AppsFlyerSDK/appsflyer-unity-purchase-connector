#!/bin/bash

echo "Start build for appsflyer-unity-purchase-connector.unitypackage"

DEPLOY_PATH=output
UNITY_PATH="/Applications/Unity/Unity.app/Contents/MacOS/Unity"
PACKAGE_NAME="appsflyer-unity-purchase-connector-2.0.1.unitypackage"
mkdir -p $DEPLOY_PATH

#move external dependency manager
echo "moving the external dependency manager to root"
mv external-dependency-manager-1.2.144.unitypackage ..

# Build the .unitypackage
 /Applications/Unity/Hub/Editor/2020.3.41f1/Unity.app/Contents/MacOS/Unity \
-gvh_disable \
-batchmode \
-importPackage external-dependency-manager-1.2.144.unitypackage \
-nographics \
-logFile create_unity_core.log \
-projectPath $PWD/../ \
-exportPackage \
Assets \
$PWD/$DEPLOY_PATH/$PACKAGE_NAME \
-quit \
&& echo "Package exported successfully to output/appsflyer-unity-purchase-connector-2.0.1.unitypackage" \
|| echo "Failed to export package. See create_unity_core.log for more info"

if [ "$1" == "-p" ]; then
echo "moving the External Dependency Manager file back to deploy"
mv ../external-dependency-manager-1.2.144.unitypackage .
echo "removing ./Library"
rm -rf ../Library
echo "removing ./Logs"
rm -rf ../Logs
echo "removing ./Packages"
rm -rf ../Packages
echo "removing ./ProjectSettings"
rm -rf ../ProjectSettings
echo "removing ./deploy/create_unity_core.log"
rm ./create_unity_core.log
echo "Moving $DEPLOY_PATH/$PACKAGE_NAME to root"
mv ./output/$PACKAGE_NAME ..
echo "removing ./deploy/output"
rm -rf ./output
else
echo "dev mode. No files removed. Run with -p flag for production build"
fi