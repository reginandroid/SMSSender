package app.com.regiko.smssender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ReciverData extends AppCompatActivity {
    Context mContext;
    Button btn_save, btn_cancel;
    EditText name, phone;
    LayoutInflater mInflater;
    ListView lv;
    RecieverAdapter mAdapter;
    LinearLayoutManager nwlinearLayoutManager;
    private ArrayList<Reciever> mRecievers;
    RecieversDBHelper mDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_reciver_data);

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//         setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.phone_list);

        refreshRV();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent i = new Intent(getApplicationContext(), NewPhoneNumber.class);
                                        startActivity(i);
                                   }

                               }
        );
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // back button
                Intent resultIntent = new Intent();

                resultIntent.putStringArrayListExtra("selectedItems", mAdapter.phones);

                Log.d("my1", mAdapter.phones.toString());
                //Add the bundle to the intent.

                setResult(Activity.RESULT_OK, resultIntent);

                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void refreshRV(){

        mDBHelper = new RecieversDBHelper(this);
        mRecievers = mDBHelper.getPointerList();
                mAdapter = new RecieverAdapter(getApplicationContext(), R.layout.item_reciever, mRecievers);

        lv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}