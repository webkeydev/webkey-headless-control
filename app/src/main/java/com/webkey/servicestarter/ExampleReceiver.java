package com.webkey.servicestarter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ExampleReceiver extends BroadcastReceiver {

    public static final String WEBKEY_INTENT_ACTION_CONNECTION_STATUS = "com.webkey.servicestarter.intent.action.CONNECTION_STATUS";
    private static final String INTENT_PARAM_CONNECTION_STATUS = "connected";
    private static final String INTENT_PARAM_CONNECTION_SERIAL_ID= "serial_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getAction() == null) {
            return;
        }

        if (!intent.getAction().equals(WEBKEY_INTENT_ACTION_CONNECTION_STATUS)) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            Toast.makeText(context, "Empty connection status intent", Toast.LENGTH_LONG).show();
            return;
        }

        String state;
        if (bundle.getBoolean(INTENT_PARAM_CONNECTION_STATUS)) {
            state="connected";
        } else {
            state="disconnected";
        }

        String serial = bundle.getString(INTENT_PARAM_CONNECTION_SERIAL_ID);

        String msg=String.format("Webkey is %s to the server. Serial: %s", state, serial);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
