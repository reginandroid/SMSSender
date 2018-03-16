package app.com.regiko.smssender;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPhoneNumber extends AppCompatActivity implements View.OnClickListener  {
Context mContext;
    Button  btn_save, btn_cancel;
    EditText name, phone;
    private  OnDBUpdatedListener mCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_new_phone_number);
        btn_save = (Button)findViewById(R.id.btnSave);
        btn_cancel = (Button)findViewById(R.id.btnCancel);
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone_number);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == btn_save) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

            String name_reciever = name.getText().toString();
            String phone_number = phone.getText().toString();;
           Reciever item = new Reciever(name_reciever, phone_number);
            RecieversDBHelper dbHelper= new RecieversDBHelper(this);

            dbHelper.saveRecieverItem(name_reciever, phone_number);

            Log.d("my",item.getName() + " "+ item.getPhone() );

            mCallback.onDBChanged();

        }
        if (v == btn_cancel) {

        }

    }

    public interface OnDBUpdatedListener{
        public void onDBChanged();
    }
    }

