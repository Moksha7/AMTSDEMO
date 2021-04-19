package com.example.amtsdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amtsdemo.model.StudentPass;
import com.example.amtsdemo.model.User;


public class StudentPassOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = StudentPassOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String STUDENTPASS_DATA_TABLE = "STUDENT_PASS_DATA";
    private static final String DATABASE_NAME = "AMTSDB";

    // Column name
    public static final String KEY_SID = "_id";
    public static final String KEY_SCATEGORY = "Category";
    public static final String KEY_STARTDATE = "StartDate";
    public static final String KEY_ENDDATE= "EndDate";
    public static final String KEY_SOURCE = "Source";
    public static final String KEY_DESTINATION = "Destination";
    public static final String KEY_ENROLLMENT = "Enrollment";
    public static final String KEY_COLLAGE = "Collage";
    public static final String KEY_DEPARTMENT = "Department";
    public static final String KEY_YEAR = "Year";
    private static final String USER_DATA_TABLE = "User_Data";


    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_SID, KEY_SCATEGORY ,KEY_COLLAGE , KEY_DEPARTMENT , KEY_ENROLLMENT , KEY_YEAR , KEY_SOURCE , KEY_DESTINATION , KEY_STARTDATE , KEY_ENDDATE};

    // Build the SQL query that creates the table.
    private static final String STUDENT_PASS_LIST_TABLE_CREATE =
            "CREATE TABLE " + STUDENTPASS_DATA_TABLE + " (" +
                    KEY_SID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_SCATEGORY + " TEXT, " +
                    KEY_COLLAGE + " INTEGER, " +
                    KEY_DEPARTMENT + " TEXT, " +
                    KEY_ENROLLMENT + " TEXT, " +
                    KEY_YEAR + " TEXT, " +
                    KEY_SOURCE + " TEXT, " +
                    KEY_DESTINATION + " TEXT, " +
                    KEY_STARTDATE + " TEXT, " +
                    KEY_ENDDATE + " TEXT, " +
                    "FOREIGN KEY ("+KEY_SCATEGORY+") REFERENCES "+USER_DATA_TABLE+" ("+UserOpenHelper.KEY_CATEGORY+"));";

    //private static final String STUDENT_TABLE_QUERY = "CREATE TABLE Student (id integer , name text , phone text);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public StudentPassOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct StudentListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"on Create");
       /*db.execSQL(STUDENT_PASS_LIST_TABLE_CREATE);
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

    public StudentPass query(int position) {
        String query = "SELECT  * FROM " + STUDENTPASS_DATA_TABLE +
                " ORDER BY " + KEY_COLLAGE + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        StudentPass entry = new StudentPass();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setsId(cursor.getInt(cursor.getColumnIndex(KEY_SID)));
            entry.setsCategory(cursor.getString(cursor.getColumnIndex(KEY_SCATEGORY)));
            entry.setsCollage(cursor.getString(cursor.getColumnIndex(KEY_COLLAGE)));
            entry.setsDepartment(cursor.getString(cursor.getColumnIndex(KEY_DEPARTMENT)));
            entry.setsEnrollmentNo(cursor.getString(cursor.getColumnIndex(KEY_ENROLLMENT)));
            entry.setsYear(cursor.getString(cursor.getColumnIndex(KEY_YEAR)));
            entry.setsSource(cursor.getColumnName(cursor.getColumnIndex(KEY_SOURCE)));
            entry.setsDestination(cursor.getColumnName(cursor.getColumnIndex(KEY_DESTINATION)));
            entry.setsStartingDate(cursor.getString(cursor.getColumnIndex(KEY_STARTDATE)));
            entry.setsEndDate(cursor.getColumnName(cursor.getColumnIndex(KEY_ENDDATE)));
            Log.i(TAG,entry.getsCategory().toString());
            Log.i(TAG,entry.getsCollage().toString());
            Log.i(TAG,entry.getsDepartment().toString());
            Log.i(TAG,entry.getsYear().toString());
            Log.i(TAG,entry.getsEnrollmentNo().toString());
            Log.i(TAG,entry.getsSource().toString());
            Log.i(TAG,entry.getsDestination().toString());
            Log.i(TAG,entry.getsStartingDate().toString());
            Log.i(TAG,entry.getsEndDate().toString());
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }


    public StudentPass ListStudent(int position) {
        String query = "SELECT  * FROM " + STUDENTPASS_DATA_TABLE + USER_DATA_TABLE +
                " WHERE "+ StudentPassOpenHelper.KEY_SCATEGORY + "=" +UserOpenHelper.KEY_CATEGORY ;

        Cursor cursor = null;
        StudentPass entry = new StudentPass();
        User user = new User();
        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setsId(cursor.getInt(cursor.getColumnIndex(KEY_SID)));
            entry.setsCategory(cursor.getString(cursor.getColumnIndex(KEY_SCATEGORY)));
            entry.setcId(cursor.getInt(cursor.getColumnIndex(KEY_COLLAGE)));
            entry.setsDepartment(cursor.getString(cursor.getColumnIndex(KEY_DEPARTMENT)));
            entry.setsEnrollmentNo(cursor.getString(cursor.getColumnIndex(KEY_ENROLLMENT)));
            entry.setsYear(cursor.getString(cursor.getColumnIndex(KEY_YEAR)));
            entry.setsSource(cursor.getColumnName(cursor.getColumnIndex(KEY_SOURCE)));
            entry.setsDestination(cursor.getColumnName(cursor.getColumnIndex(KEY_DESTINATION)));
            entry.setsStartingDate(cursor.getString(cursor.getColumnIndex(KEY_STARTDATE)));
            entry.setsEndDate(cursor.getColumnName(cursor.getColumnIndex(KEY_ENDDATE)));
            user.setName(cursor.getColumnName(cursor.getColumnIndex(UserOpenHelper.KEY_NAME)));
            user.setEmail(cursor.getColumnName(cursor.getColumnIndex(UserOpenHelper.KEY_EMAIL)));
            user.setMobile(cursor.getColumnName(cursor.getColumnIndex(UserOpenHelper.KEY_MOBILE)));
            user.setGender(cursor.getColumnName(cursor.getColumnIndex(UserOpenHelper.KEY_GENDER)));
            Log.i(TAG,entry.getsCategory().toString());
            Log.i(TAG,entry.getsCollage().toString());
            Log.i(TAG,entry.getsDepartment().toString());
            Log.i(TAG,entry.getsYear().toString());
            Log.i(TAG,entry.getsEnrollmentNo().toString());
            Log.i(TAG,entry.getsSource().toString());
            Log.i(TAG,entry.getsDestination().toString());
            Log.i(TAG,entry.getsStartingDate().toString());
            Log.i(TAG,entry.getsEndDate().toString());
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    /*public User select(String Email , String Password) {
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
*/
    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, USER_DATA_TABLE);
    }

    public long insert(String Category,int CollageName,String Department , String Enno , String Year, String Source , String Destination , String StartDate , String EndDate) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_SCATEGORY, Category);
        values.put(KEY_COLLAGE, CollageName);
        values.put(KEY_DEPARTMENT, Department);
        values.put(KEY_ENROLLMENT, Enno);
        values.put(KEY_YEAR, Year);
        values.put(KEY_SOURCE, Source);
        values.put(KEY_DESTINATION, Destination);
        values.put(KEY_STARTDATE, StartDate);
        values.put(KEY_ENDDATE, EndDate);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(STUDENTPASS_DATA_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
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
    public int update(int id,String Category,String CollageName,String Department,String Enno,String Year,String Source,String Destination,String StartDate,String EndDate) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_SCATEGORY, Category);
            values.put(KEY_COLLAGE, CollageName);
            values.put(KEY_DEPARTMENT, Department);
            values.put(KEY_ENROLLMENT, Enno);
            values.put(KEY_YEAR, Year);
            values.put(KEY_SOURCE, Source);
            values.put(KEY_DESTINATION, Destination);
            values.put(KEY_STARTDATE, StartDate);
            values.put(KEY_ENDDATE, EndDate);

            mNumberOfRowsUpdated = mWritableDB.update(STUDENTPASS_DATA_TABLE, //table to change
                    values, // new values to insert
                    KEY_SID + " = ?", // selection criteria for row (in this case, the _id column)
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
            deleted = mWritableDB.delete(STUDENTPASS_DATA_TABLE, //table name
                    KEY_SID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor search(String searchString) {
        String[] columns = new String[]{KEY_COLLAGE};
        String where =  KEY_SCATEGORY + " LIKE ?";
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
        Log.w(StudentPassOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTPASS_DATA_TABLE);
      //  onCreate(db);
    }
}