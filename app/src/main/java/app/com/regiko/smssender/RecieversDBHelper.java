package app.com.regiko.smssender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ковтун on 26.02.2018.
 */

public class RecieversDBHelper {
    RecieverDatabase openHelper;
    Reciever item;
    private SQLiteDatabase db;
    private String [] columnItemName = {Reciever_Contract._ID, Reciever_Contract.COLUMN_NAME,
            Reciever_Contract.COLUMN_PHONE, Reciever_Contract.COLUMN_EMAIL};
    public RecieversDBHelper(Context context) {
        openHelper = new RecieverDatabase(context);
        db = openHelper.getWritableDatabase();
    }

    public void saveRecieverItem(String name, String phone){


        ContentValues cv = new ContentValues();
        cv.put(Reciever_Contract.COLUMN_NAME, name);
        cv.put(Reciever_Contract.COLUMN_PHONE, phone);


        db.insert(Reciever_Contract.TABLE_NAME, null, cv);
    }

    public boolean close() {
        db.close();
        return true;
    }

    public ArrayList<Reciever> getPointerList() {

        ArrayList<Reciever> items = new ArrayList<Reciever>();

        db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(Reciever_Contract.TABLE_NAME,
                columnItemName, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{

                Reciever item = new Reciever();
                item.setName(cursor.getString(1));
                item.setPhone(cursor.getString(2));


                items.add(item);

            } while( cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
    public Reciever getRecieverItem() {

        Reciever item = new Reciever();

        db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(Reciever_Contract.TABLE_NAME,
                columnItemName, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{


                item.setName(cursor.getString(1));
                item.setPhone(cursor.getString(2));
                item.setEmail(cursor.getString(3));



            } while( cursor.moveToNext());
        }
        cursor.close();
        return item;
    }
    public Cursor queryDB(){
        db = openHelper.getWritableDatabase();
        Cursor c = db.query(Reciever_Contract.TABLE_NAME, null, null,null,null,null,null);
        return c;
    }

    public boolean isHasRow(){
        db = openHelper.getWritableDatabase();
        boolean result = false;
        Cursor c = db.query(Reciever_Contract.TABLE_NAME,
                null, null, null, null, null, null);
        if (c.getCount()>0) result = true;
        c.close();
        db.close();
        return result;
    }
    public void deleteAllrows( ){
        db.delete(Reciever_Contract.TABLE_NAME, null, null);

    }
    public void deleteDB() {

        db = openHelper.getWritableDatabase();
        db.delete(Reciever_Contract.TABLE_NAME, null, null);
    }



    static class RecieverDatabase extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "recievers.db";

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_INTEGER = " INTEGER";
        private static final String TYPE_REAL = " REAL";
        private static final String TYPE_BLOB = " BLOB NOT NULL";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_RECIEVERS =
                "CREATE TABLE " + Reciever_Contract.TABLE_NAME + " (" +
                        Reciever_Contract._ID + " INTEGER PRIMARY KEY," +
                        Reciever_Contract.COLUMN_NAME    + TYPE_TEXT + COMMA_SEP +
                        Reciever_Contract.COLUMN_PHONE + TYPE_TEXT + COMMA_SEP +
                        Reciever_Contract.COLUMN_EMAIL + TYPE_TEXT  +
                        ")";

        private static final String SQL_DELETE_RECIEVERS =
                "DROP TABLE IF EXISTS " + Reciever_Contract.TABLE_NAME;

        public RecieverDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_RECIEVERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_RECIEVERS);
            onCreate(db);
        }
    }

}
