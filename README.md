# Control Webkey Application with intents
Android application components can connect to other Android applications by sending intents. An intent is an abstract description of an operation to be performed.

Pre requirements:
1. The applications signed by the same key can send intents to each other.
2. The intent sending feature requires permission of the sending application.

## Permission configuration
The required permission is in the AndroidManifest.xml file of the sender application: com.webkey.permission.CONTROL
Example AndroidManifes.xml:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.example.snazzyapp">
    <uses-permission android:name="com.webkey.permission.CONTROL"/>
    <application ...>
        ...
    </application>
</manifest>
```
## Intent sending
Java examples for intents
### Start the Webkey service
```java
public void startWebkeyService() {
    Intent startServiceIntent = new Intent();

    startServiceIntent.setClassName("com.webkey",
        "com.webkey.service.BackgroundService" );

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(startServiceIntent);
    } else {
        startService(startServiceIntent);
    }
}
```

### Update server address and fleet id
```java
public void updateSettings() {
    Intent updateSettingsIntent = new Intent(
       "com.webkey.intent.action.UPDATE_APP_SETTINGS");
    updateSettingsIntent.putExtra(“fleet_id”,”<UUID_FLEET_ID>”);
    updateSettingsIntent.putExtra(“harbor_address”,”<SERVER_ADDRESS>”);
    sendBroadcast(updateSettingsIntent);
}
```

### Connect to Webkey server
```java
public void enableService() {
    Intent activateIntent = new Intent(
       "com.webkey.intent.action.START_SERVICE");
    sendBroadcast(activateIntent);
}
```

### Disconnect from the Webkey server and stop the service
```java
public void stopService() {
    Intent stopIntent = new Intent(
	"com.webkey.intent.action.STOP_SERVICE");
    sendBroadcast(stopIntent);
}
```

### Disconnect from the Webkey server and enforce to kill the service process.
```java
public void stopService() {
    Intent stopIntent = new Intent(
	"com.webkey.intent.action.FORCE_STOP_SERVICE");
    sendBroadcast(stopIntent);
}
```