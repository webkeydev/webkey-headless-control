package com.webkey.servicestarter;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String WEBKEY_INTENT_UPDATE_APP_SETTINGS = "com.webkey.intent.action.UPDATE_APP_SETTINGS";
    private static final String INTENT_KEY_HARBOR_ADDRESS = "harbor_address";
    private static final String INTENT_KEY_FLEET_ID = "fleet_id";
    private static final String TEST_FLEET_ID = "ba8df2f3-b53a-4ee7-ad26-f74b4e669d14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startService = findViewById(R.id.start);
        Button activateService = findViewById(R.id.activate);
        Button deactivateService = findViewById(R.id.deactivate);
        Button killService = findViewById(R.id.kill);
        Button forceKillService = findViewById(R.id.force_kill);
        Button updateSettings = findViewById(R.id.update_settings);

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
        deactivateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deactivateIntent = new Intent("com.webkey.intent.action.STOP_SERVICE");
                sendBroadcast(deactivateIntent);
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
                Intent updateSettingsIntent = new Intent(WEBKEY_INTENT_UPDATE_APP_SETTINGS);
                updateSettingsIntent.putExtra(INTENT_KEY_HARBOR_ADDRESS, "connect.webkey.cc");
                updateSettingsIntent.putExtra(INTENT_KEY_FLEET_ID, TEST_FLEET_ID);
                sendBroadcast(updateSettingsIntent);
            }
        });

    }

}
