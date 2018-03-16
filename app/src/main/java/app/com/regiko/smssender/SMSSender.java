package app.com.regiko.smssender;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SMSSender extends AppCompatActivity implements View.OnClickListener{
    Button phone, database, send;
    EditText massage;
    TextView recievers;
    ArrayList<String> sendlist = new ArrayList<String>();
    public ListView myListView;
    public String[] Contacts = {};
    RecieverAdapter mAdapter;
    String contactList;
    ArrayList<String> result;
SharedPreferences mSharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 1;
    final static int CONTACT_PICKER_RESULT = 1;
    public int[] to = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smssender);
        phone = (Button) findViewById(R.id.phone);
        database = (Button) findViewById(R.id.database);
        send = (Button) findViewById(R.id.send);
        massage = (EditText) findViewById(R.id.massage);
        recievers = (TextView)findViewById(R.id.recievers);
        // myListView = (ListView)findViewById(R.id.recievers);
        phone.setOnClickListener(this);
        database.setOnClickListener(this);
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);


        send.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, PermissionChecker.PERMISSION_GRANTED);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

        public void onActivityResult(int reqCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                switch (reqCode) {
                    case CONTACT_PICKER_RESULT:
                        result = data.getStringArrayListExtra("selectedItems");
                        Log.d("my1", result + ", ");

                            recievers.setText(result.toString());
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int i = 0; i < result.size(); i++) {

                                    if (massage.getText().length() > 0) {
                                        Log.d("my2", result.get(i).toString() + ", ");
                                        sendSMS(result.get(i).toString(), massage.getText().toString().trim());
                                    }
                                }
                            }
                        });
//                    if (cursor.moveToFirst()) {
//                        while(cursor.moveToNext()){
//                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                        phoneNo = cursor.getString(phoneIndex);
//                        phone_number.add(phoneNo);
//                            recievers.setText(phoneNo);}
//                    }
//
//                    cursor.close();
//            }
                        super.onActivityResult(reqCode, resultCode, data);
                }
            }
        }
    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case R.id.phone:

                  Intent list =new Intent(this, ContactList.class);
                    startActivityForResult(list, CONTACT_PICKER_RESULT);

                break;
                case R.id.database:
                    Intent j = new Intent(this, ReciverData.class );
                    startActivityForResult(j, CONTACT_PICKER_RESULT);

                    break;


        }
    }
    private void sendSMS(String phoneNumber, String message)
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
//    @Override
//    public void onDBChanged() {
//
//        ReciverData f = (ReciverData) this.getSupportFragmentManager().findFragmentByTag("tab1");
//        f.refreshListViewWithSelectedData();

}

