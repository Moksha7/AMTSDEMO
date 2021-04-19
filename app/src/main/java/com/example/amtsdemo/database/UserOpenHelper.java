package com.example.amtsdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amtsdemo.model.User;


public class UserOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = UserOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String USER_DATA_TABLE = "User_Data";
    private static final String DATABASE_NAME = "AMTSDB";

    // Column name
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "User_Name";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_MOBILE = "Mobile";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_BIRTHDAY = "Birth_Date";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_PASSWORD = "Password";

    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_ID, KEY_NAME,KEY_EMAIL,KEY_MOBILE,KEY_GENDER,KEY_BIRTHDAY,KEY_CATEGORY,KEY_PASSWORD};

    // Build the SQL query that creates the table.
    private static final String USER_LIST_TABLE_CREATE =
            "CREATE TABLE " + USER_DATA_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_NAME + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_MOBILE + " TEXT, " +
                    KEY_GENDER + " TEXT, " +
                    KEY_BIRTHDAY + " TEXT, " +
                    KEY_CATEGORY + " TEXT PRIMARY KEY, " +
                    KEY_PASSWORD + " TEXT );";

    //private static final String STUDENT_TABLE_QUERY = "CREATE TABLE Student (id integer , name text , phone text);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct UserListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /*db.execSQL(USER_LIST_TABLE_CREATE);
       // db.execSQL(STUDENT_TABLE_QUERY);
        Log.d(TAG, "Table Created");*/
       // fillDatabaseWithData(db);
    }
/*
    public void fillDatabaseWithData(SQLiteDatabase db) {

        String[] words = {"ABC","ABC"};

        // Create a container for the data.
        ContentValues values = new ContentValues();

        for (int i=0; i < words.length;i++) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(KEY_NAME, words[i]);
            values.put(KEY_Phone,words[i]);
            values.put(KEY_Email,words[i]);
            values.put(KEY_Department,words[i]);
            values.put(KEY_Gender,words[i]);
            values.put(KEY_BirthDate,words[i]);
            values.put(KEY_Technology,words[i]);
            db.insert(STUDENT_DATA_TABLE, null, values);
        }
    }*/

    public User query(int position) {
        String query = "SELECT  * FROM " + USER_DATA_TABLE +
                " ORDER BY " + KEY_NAME + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        User entry = new User();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            entry.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            entry.setMobile(cursor.getString(cursor.getColumnIndex(KEY_MOBILE)));
            entry.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
            entry.setBirthDate(cursor.getColumnName(cursor.getColumnIndex(KEY_BIRTHDAY)));
            entry.setCategory(cursor.getColumnName(cursor.getColumnIndex(KEY_CATEGORY)));
            entry.setPassWord(cursor.getColumnName(cursor.getColumnIndex(KEY_PASSWORD)));
            Log.i(TAG,entry.getName().toString());
            Log.i(TAG,entry.getEmail().toString());
            Log.i(TAG,entry.getMobile().toString());
            Log.i(TAG,entry.getGender().toString());
            Log.i(TAG,entry.getBirthDate().toString());
            Log.i(TAG,entry.getCategory().toString());
            Log.i(TAG,entry.getPassWord().toString());
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    public User select(String Email , String Password) {
        String query = " SELECT  * FROM " + USER_DATA_TABLE +
                " WHERE " + KEY_EMAIL + " = ? " + "AND " + KEY_PASSWORD +" = ? "  ;
        String[] selectionArgs = {Email,Password};
        Cursor cursor = null;
        User entry = new User();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query,selectionArgs);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            entry.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            entry.setMobile(cursor.getString(cursor.getColumnIndex(KEY_MOBILE)));
            entry.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
            entry.setBirthDate(cursor.getColumnName(cursor.getColumnIndex(KEY_BIRTHDAY)));
            entry.setCategory(cursor.getColumnName(cursor.getColumnIndex(KEY_CATEGORY)));
            entry.setPassWord(cursor.getColumnName(cursor.getColumnIndex(KEY_PASSWORD)));
            Log.i(TAG,entry.getName().toString());
            Log.i(TAG,entry.getEmail().toString());
            Log.i(TAG,entry.getMobile().toString());
            Log.i(TAG,entry.getGender().toString());
            Log.i(TAG,entry.getBirthDate().toString());
            Log.i(TAG,entry.getCategory().toString());
            Log.i(TAG,entry.getPassWord().toString());
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
        return DatabaseUtils.queryNumEntries(mReadableDB, USER_DATA_TABLE);
    }

    public long insert(String Name,String Email,String Mobile,String Gender,String BirthDate,String Category,String Password) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name);
        values.put(KEY_EMAIL, Email);
        values.put(KEY_MOBILE, Mobile);
        values.put(KEY_GENDER, Gender);
        values.put(KEY_BIRTHDAY, BirthDate);
        values.put(KEY_CATEGORY, Category);
        values.put(KEY_PASSWORD, Password);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(USER_DATA_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(USER_DATA_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }
    /*public void insert(){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put("id",1);
        values.put("name", "MOksha");
        values.put("phone","12222222");
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert("Student", null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
      //  return newId;

    }
*/
    public int update(int id, String Name,String Email,String Mobile,String Gender,String BirthDate,String Category,String Password) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, Name);
            values.put(KEY_EMAIL, Email);
            values.put(KEY_MOBILE, Mobile);
            values.put(KEY_GENDER, Gender);
            values.put(KEY_BIRTHDAY, BirthDate);
            values.put(KEY_CATEGORY, Category);
            values.put(KEY_PASSWORD, Password);

            mNumberOfRowsUpdated = mWritableDB.update(USER_DATA_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
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
            deleted = mWritableDB.delete(USER_DATA_TABLE, //table name
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor search(String searchString) {
        String[] columns = new String[]{KEY_NAME};
        String where =  KEY_NAME + " LIKE ?";
        searchString = "%" + searchString + "%";
        String[] whereArgs = new String[]{searchString};

        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.query(USER_DATA_TABLE, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e); // Just log the exception
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USER_DATA_TABLE);
      //  onCreate(db);
    }
}