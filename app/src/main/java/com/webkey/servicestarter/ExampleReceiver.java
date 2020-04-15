package com.webkey.servicestarter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ExampleReceiver extends BroadcastReceiver {

    public static final String WEBKEY_INTENT_ACTION_CONNECTION_STATUS = "com.webkey.servicestarter.intent.action.CONNECTION_STATUS";
    private static final String INTENT_PARAM_CONNECTION_STATUS = "connected";

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

        if (bundle.getBoolean(INTENT_PARAM_CONNECTION_STATUS)) {
            Toast.makeText(context, "Webkey is connected to the server", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Webkey is disconnected from the server", Toast.LENGTH_LONG).show();
        }
    }
}
