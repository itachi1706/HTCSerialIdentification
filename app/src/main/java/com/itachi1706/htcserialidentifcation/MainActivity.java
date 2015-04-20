package com.itachi1706.htcserialidentifcation;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Button search, serial;
    TextView resultList;
    EditText serialNumber;

    /**
     * Serial Number Format
     * HTYMDAABBBBB
     * HT = Vendor HTC--> Hsinchu, Taiwan or SH = Shanghai, China
     * Y = Year (3 = 2013)
     * M = Month (hex 1...C = 1 - 12 months)
     * D = Day (hex 1...9A..Z = 1 - 31 days)
     * YY = Part Code
     * ZZZZZ : Identification Number (decimal)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.btnSearch);
        serial = (Button) findViewById(R.id.btnSerial);
        resultList = (TextView) findViewById(R.id.tvResult);
        serialNumber = (EditText) findViewById(R.id.serialField);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkSerialNumberValid(serialNumber.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Invalid Serial Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String htmlFormattedSN = parseSerialNumber(serialNumber.getText().toString());
                resultList.setText(Html.fromHtml(htmlFormattedSN));
            }
        });

        serial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String uid = tManager.getDeviceId();
                serialNumber.setText(uid);
                search.performClick();
            }
        });

    }

    private String parseSerialNumber(String serial){
        serial = serial.toUpperCase();
        String manufacturerLocation = serial.substring(0,2);
        String date = serial.substring(2,5);
        StringBuilder html = new StringBuilder();

        html.append("Result for S/N <b>").append(serial).append("</b><br />");

        Manufacturer manufacturer = Manufacturer.fromCode(manufacturerLocation);
        if (manufacturer == Manufacturer.UNKNOWN)
            html.append("Manufactured At: ").append(manufacturerLocation).append("<br />");
        else
            html.append("Manufactured At: ").append(manufacturer.getLocation()).append("<br />");

        Date year = Date.getData(date.charAt(0) + "");
        Date month = Date.getData(date.charAt(1) + "");
        Date day = Date.getData(date.charAt(2) + "");
        html.append("Date of Manufacture: ").append(day.getDate()).append(" ").append(month.getMonth()).append(" ").append(year.getYear());
        return html.toString();
    }

    private boolean checkSerialNumberValid(String serial){
        return serial.length() == 12;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
