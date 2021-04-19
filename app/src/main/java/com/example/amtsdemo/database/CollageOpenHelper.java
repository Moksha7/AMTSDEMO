package com.example.amtsdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amtsdemo.model.Collage;


public class CollageOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = CollageOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String COLLAGE_DATA_TABLE = "Collage_Data";
    private static final String DATABASE_NAME = "AMTSDB";

    // Column name
    public static final String KEY_CID = "_id";
    public static final String KEY_CNAME = "Collage_Name";

    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_CID, KEY_CNAME};

    // Build the SQL query that creates the table.
    private static final String COLLAGE_LIST_TABLE_CREATE =
            "CREATE TABLE " + COLLAGE_DATA_TABLE + " (" +
                    KEY_CID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_CNAME + " TEXT );";

    //private static final String STUDENT_TABLE_QUERY = "CREATE TABLE Student (id integer , name text , phone text);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public CollageOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct CollageListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /* db.execSQL(COLLAGE_LIST_TABLE_CREATE);
       // db.execSQL(STUDENT_TABLE_QUERY);
        Log.d(TAG, "Table Created");*/
       // fillDatabaseWithData(db);
    }
    public Collage query(int position) {
        String query = "SELECT  * FROM " + COLLAGE_DATA_TABLE +
                " ORDER BY " + KEY_CNAME + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        Collage entry = new Collage();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_CID)));
            entry.setcName(cursor.getString(cursor.getColumnIndex(KEY_CNAME)));
            Log.i(TAG,entry.getcName().toString());
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, COLLAGE_DATA_TABLE);
    }

    public long insert(String Name) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, Name);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(COLLAGE_DATA_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public int update(int id, String Name) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_CNAME, Name);

            mNumberOfRowsUpdated = mWritableDB.update(COLLAGE_DATA_TABLE, //table to change
                    values, // new values to insert
                    KEY_CID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(COLLAGE_DATA_TABLE, //table name
                    KEY_CID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor search(String searchString) {
        String[] columns = new String[]{KEY_CNAME};
        String where =  KEY_CNAME + " LIKE ?";
        searchString = "%" + searchString + "%";
        String[] whereArgs = new String[]{searchString};

        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.query(COLLAGE_DATA_TABLE, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e); // Just log the exception
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CollageOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + COLLAGE_DATA_TABLE);
      //  onCreate(db);
    }
}