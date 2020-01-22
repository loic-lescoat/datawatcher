package com.example.datawatcher2019;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        SmsMessage smsMessage = null;



        if (bundle != null) {
            String qteDonneesUtilisees = "Regex not found";
            Object[] smsObj = (Object[]) bundle.get("pdus");

            for (Object object : smsObj) {
                smsMessage = SmsMessage.createFromPdu((byte[]) object);
                String sender = smsMessage.getOriginatingAddress();

                if (sender.equals("555")) {

//                    Pattern pattern = Pattern.compile("(?<=Données : ).*?(?=\\(pour\\b)");
//                    Pattern pattern = Pattern.compile("Données :(.*?)pour");
//                    Pattern p = Pattern.compile("Données.*\\(pour"); // for when response is not split into two parts
                    Pattern p = Pattern.compile("Données.*(K|M|G)"); // for when response is split in two; it gets split right in middle of "Mo"
                    String smsBody = smsMessage.getDisplayMessageBody().toString();
                    Matcher m = p.matcher(smsBody);

                    Boolean b = m.find();
                    if (b) {
                        String match = m.group(0);
//                        qteDonneesUtilisees = match.substring(10, match.length() - 6); // for when response is not split
                        qteDonneesUtilisees = match.substring(10,match.length()) + "o "; // for when response is split
                        // assumes data used is measured in Mo, not Go or Ko
                    }
                    String dispText = context.getString(R.string.dataUsed) + qteDonneesUtilisees + context.getString(R.string.outOf);
                    Toast.makeText(context, dispText, Toast.LENGTH_SHORT).show(); // TODO montre 2 fois?
                    Log.e("iii", smsBody);
                }
            }
        }
    }
}
