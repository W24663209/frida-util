cd app/build/outputs/apk/debug
rm -rf app-debug
rm -rf app-debug.zip
mv app-debug.apk app-debug.zip
unzip app-debug.zip
cd app-debug
rm classes.dex
mv classes2.dex classes.dex
adb push classes.dex /data/local/tmp