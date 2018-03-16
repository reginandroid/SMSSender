package app.com.regiko.smssender;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Ковтун on 26.02.2018.
 */

public class ContactList extends AppCompatActivity {
    private ArrayList<Reciever> mRecievers;
    ListView lv;
    //SparseBooleanArray mCheckStates;
   String [] selectedItems;
    RecieverAdapter mAdapter;
    ProgressBar mProgressBar;
    Button select;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_phone_list);

        lv = (ListView)findViewById(R.id.lv);
        mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setVisibility(View.VISIBLE);
        select = (Button)findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for ( int i=0; i< mRecievers.size(); i++ ) {
                   mAdapter.setChecked(i, true);
                    mAdapter.phones.add(mRecievers.get(i).getPhone());
                    mAdapter.notifyDataSetChanged();
}

            }
        });

        ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
        listViewContactsLoader.execute();

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

    private class ListViewContactsLoader extends AsyncTask<Void, Void, ArrayList<Reciever>> {

        @Override
        protected ArrayList<Reciever> doInBackground(Void... voids) {

            mRecievers = new ArrayList<>();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mRecievers.add(new Reciever(name, phoneNumber));

            }

            phones.close();
            return mRecievers;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Reciever> recievers) {
            for (Reciever r :recievers){
                Log.d("my", r.getName() + " " + r.getPhone());
            }
            mProgressBar.setVisibility(View.GONE);
            mAdapter = new RecieverAdapter(getApplicationContext(), R.layout.item_reciever, recievers);
            lv.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }

}

