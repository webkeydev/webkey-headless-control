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

### Update settings
You can set custom settings. The parameters are optional.
- fleet id
- server address
- custom, external serial id
```java
public void updateSettings() {
    Intent updateSettingsIntent = new Intent(
       "com.webkey.intent.action.UPDATE_APP_SETTINGS");
    updateSettingsIntent.putExtra(“fleet_id”,”<UUID_FLEET_ID>”);
    updateSettingsIntent.putExtra(“harbor_address”,”<SERVER_ADDRESS>”);
    updateSettingsIntent.putExtra(“serial_id”,”<CUSTOM_EXTERNAL_SERIAL_ID>”);
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

### Request the connection status and serial id from the Webkey service.
The Webkey service send response with pending intent. You must implement an intent receiver.
The pending intent key in the broadcast intent is the **receiver**.

If no intent is coming the Webkey service is inactive so the connection is offline.
```java
public void getConnectionStatus() {
    Intent getConnectionStatusIntent = new Intent(MainActivity.this, ExampleReceiver.class);
    getConnectionStatusIntent.setAction(ExampleReceiver.WEBKEY_INTENT_ACTION_CONNECTION_STATUS);

    PendingIntent pi = PendingIntent.getBroadcast(context, 10, getConnectionStatusIntent, 0);

    Bundle bundle = new Bundle();
    bundle.putParcelable("receiver", pi);

    Intent serviceIntent = new Intent("com.webkey.intent.action.GET_CONNECTION_STATUS");
    serviceIntent.putExtras(bundle);
    sendBroadcast(serviceIntent);
}
```

### Receive the connection status
To receive the connection status you have to implement an intent receiver. The Webkey client
application will send response intent with the given action  after the service has received the
connection status request. The intent contains two extas.
- Type: bool, value name: **connected**. The true value means that the service has been connected to the server.
- Type: String, value name: **serial_id**. It represent the serial id of the device. With this id 
you can refer to this device on the Webkey Dashboard. The serial has been validated by "^[0-9A-Za-z:]{1,50}$" expression

Example of the broadcast receiver is in "ExampleReceiver.java" file.
