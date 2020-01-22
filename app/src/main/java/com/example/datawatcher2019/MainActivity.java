package com.example.datawatcher2019;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsManager.sendTextMessage("555", null, getString(R.string.sentFrom), null, null);
        Toast.makeText(this, R.string.sending, Toast.LENGTH_SHORT).show();
        finish();

    }
}
