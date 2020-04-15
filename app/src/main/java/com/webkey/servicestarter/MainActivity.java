package com.webkey.servicestarter;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String INTENT_KEY_HARBOR_ADDRESS = "harbor_address";
    private static final String INTENT_KEY_FLEET_ID = "fleet_id";
    private static final String TEST_FLEET_ID = "ba8df2f3-b53a-4ee7-ad26-f74b4e669d14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startService = findViewById(R.id.start);
        Button activateService = findViewById(R.id.activate);
        Button killService = findViewById(R.id.kill);
        Button forceKillService = findViewById(R.id.force_kill);
        Button updateSettings = findViewById(R.id.update_settings);
        Button getConnectionStatus = findViewById(R.id.get_connection_status);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startServiceIntent = new Intent();

                startServiceIntent.setClassName("com.webkey",
                        "com.webkey.service.BackgroundService" );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(startServiceIntent);
                } else {
                    startService(startServiceIntent);
                }
            }
        });

        activateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activateIntent = new Intent("com.webkey.intent.action.START_SERVICE");
                sendBroadcast(activateIntent);
            }
        });
        killService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deactivateIntent = new Intent("com.webkey.intent.action.STOP_SERVICE");
                sendBroadcast(deactivateIntent);
            }
        });
        forceKillService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deactivateIntent = new Intent("com.webkey.intent.action.FORCE_STOP_SERVICE");
                sendBroadcast(deactivateIntent);
            }
        });
        updateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateSettingsIntent = new Intent("com.webkey.intent.action.UPDATE_APP_SETTINGS");
                updateSettingsIntent.putExtra(INTENT_KEY_HARBOR_ADDRESS, "connect.webkey.cc");
                updateSettingsIntent.putExtra(INTENT_KEY_FLEET_ID, TEST_FLEET_ID);
                sendBroadcast(updateSettingsIntent);
            }
        });
        getConnectionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getConnectionStatusIntent = new Intent(MainActivity.this, ExampleReceiver.class);
                getConnectionStatusIntent.setAction(ExampleReceiver.WEBKEY_INTENT_ACTION_CONNECTION_STATUS);

                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 10, getConnectionStatusIntent, 0);

                Bundle bundle = new Bundle();
                bundle.putParcelable("receiver", pi);

                Intent serviceIntent = new Intent("com.webkey.intent.action.GET_CONNECTION_STATUS");
                serviceIntent.putExtras(bundle);
                sendBroadcast(serviceIntent);
            }
        });
    }

}
