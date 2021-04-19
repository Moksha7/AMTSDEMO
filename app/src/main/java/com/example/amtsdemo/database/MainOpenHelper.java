package com.example.amtsdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amtsdemo.model.Collage;
import com.example.amtsdemo.model.EmployeePass;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.model.SeniorCitizenPass;
import com.example.amtsdemo.model.StudentPass;
import com.example.amtsdemo.model.User;

import java.util.ArrayList;
import java.util.List;


public class MainOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = MainOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String USER_DATA_TABLE = "User_Data";
    private static final String STUDENTPASS_DATA_TABLE = "STUDENT_PASS_DATA";
    private static final String COLLAGE_DATA_TABLE = "Collage_Data";
    private static final String DATABASE_NAME = "AMTSDB";
    private static final String EMPLOYEE_PASS_DATA_TABLE = "EMPLOYEE_PASS_DATA" ;
    private static final String SENIOR_CITIZEN_PASS_DATA_TABLE = "SENIOR_CITIZEN_PASS_DATA" ;

    // Column name
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "User_Name";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_MOBILE = "Mobile";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_BIRTHDAY = "Birth_Date";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_PASSWORD = "Password";


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
    public static final String KEY_STATUS = "Status";

    public static final String KEY_EID = "_id";
    public static final String KEY_ECATEGORY = "Category";
    public static final String KEY_ESTARTDATE = "StartDate";
    public static final String KEY_EENDDATE= "EndDate";
    public static final String KEY_ESOURCE = "Source";
    public static final String KEY_EDESTINATION = "Destination";
    public static final String KEY_EJOBPROFILE = "JobProfile";
    public static final String KEY_EJOBADDRESS = "JobAddress";
    public static final String KEY_ESTATUS = "Status";

    public static final String KEY_SSID = "_id";
    public static final String KEY_SSCATEGORY = "Category";
    public static final String KEY_SSSOURCE = "Source";
    public static final String KEY_SSDESTINATION = "Destination";
    public static final String KEY_SSSTATUS = "Status";



    // Column name
    public static final String KEY_CID = "_id";
    public static final String KEY_CNAME = "Collage_Name";

    // ... and a string array of columns.
    private static final String[] USER_COLUMNS =
            {KEY_SID, KEY_SCATEGORY ,KEY_COLLAGE , KEY_DEPARTMENT , KEY_ENROLLMENT , KEY_YEAR , KEY_SOURCE , KEY_DESTINATION , KEY_STARTDATE , KEY_ENDDATE};

    // Build the SQL query that creates the table.

    // ... and a string array of columns.
    private static final String[] STUDENT_COLUMNS =
            {KEY_ID, KEY_NAME,KEY_EMAIL,KEY_MOBILE,KEY_GENDER,KEY_BIRTHDAY,KEY_CATEGORY,KEY_PASSWORD};


    private static final String[] EMPLOYEE_COLUMNS =
            {KEY_EID, KEY_ECATEGORY , KEY_EJOBPROFILE,KEY_EJOBADDRESS,KEY_ESOURCE,KEY_EDESTINATION,KEY_ESTARTDATE,KEY_EENDDATE,KEY_ESTATUS};


    private static final String[] SENIORCITIZEN_COLUMNS =
            {KEY_SSID, KEY_SSCATEGORY ,KEY_SSSOURCE,KEY_SSDESTINATION,KEY_SSSTATUS};



    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_CID, KEY_CNAME};

    // Build the SQL query that creates the table.


    // Build the SQL query that creates the table.
    private static final String USER_LIST_TABLE_CREATE =
            "CREATE TABLE " + USER_DATA_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_NAME + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_MOBILE + " TEXT, " +
                    KEY_GENDER + " TEXT, " +
                    KEY_BIRTHDAY + " TEXT, " +
                    KEY_CATEGORY + " TEXT, " +
                    KEY_PASSWORD + " TEXT );";

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
                    KEY_STATUS + " TEXT, " +
                    "FOREIGN KEY ("+KEY_CATEGORY+") REFERENCES "+USER_DATA_TABLE+" ("+ UserOpenHelper.KEY_CATEGORY+"));";
    //private static final String STUDENT_TABLE_QUERY = "CREATE TABLE Student (id integer , name text , phone text);";

    private static final String COLLAGE_LIST_TABLE_CREATE =
            "CREATE TABLE " + COLLAGE_DATA_TABLE + " (" +
                    KEY_CID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_CNAME + " TEXT );";

    private static final String EMPLOYEE_PASS_LIST_TABLE_CREATE =
            "CREATE TABLE " + EMPLOYEE_PASS_DATA_TABLE + " (" +
                    KEY_EID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_ECATEGORY + " TEXT, " +
                    KEY_EJOBPROFILE + " TEXT, " +
                    KEY_EJOBADDRESS + " TEXT, " +
                    KEY_ESOURCE + " TEXT, " +
                    KEY_EDESTINATION + " TEXT, " +
                    KEY_ESTARTDATE + " TEXT, " +
                    KEY_EENDDATE + " TEXT, " +
                    KEY_ESTATUS + " TEXT, " +
                    "FOREIGN KEY ("+KEY_CATEGORY+") REFERENCES "+USER_DATA_TABLE+" ("+ UserOpenHelper.KEY_CATEGORY+"));";

    private static final String SENIOR_CITIZEN_PASS_LIST_TABLE_CREATE =
            "CREATE TABLE " + SENIOR_CITIZEN_PASS_DATA_TABLE + " (" +
                    KEY_SSID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_SSCATEGORY + " TEXT, " +
                    KEY_SSSOURCE + " TEXT, " +
                    KEY_SSDESTINATION + " TEXT, " +
                    KEY_SSSTATUS + " TEXT, " +
                    "FOREIGN KEY ("+KEY_CATEGORY+") REFERENCES "+USER_DATA_TABLE+" ("+ UserOpenHelper.KEY_CATEGORY+"));";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public MainOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct MainOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(USER_LIST_TABLE_CREATE);
       db.execSQL(COLLAGE_LIST_TABLE_CREATE);
       db.execSQL(STUDENT_PASS_LIST_TABLE_CREATE);
       db.execSQL(EMPLOYEE_PASS_LIST_TABLE_CREATE);
       db.execSQL(SENIOR_CITIZEN_PASS_LIST_TABLE_CREATE);
       // db.execSQL(STUDENT_TABLE_QUERY);
        Log.d(TAG, "Table Created");
       // fillDatabaseWithData(db);
    }

    public User user_Query(int position) {
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
            Log.i(TAG,entry.getName());
            Log.i(TAG,entry.getEmail());
            Log.i(TAG,entry.getMobile());
            Log.i(TAG,entry.getGender());
            Log.i(TAG,entry.getBirthDate());
            Log.i(TAG,entry.getCategory());
            Log.i(TAG,entry.getPassWord());
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    public User user_Select(String Email , String Password) {
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

    public long user_Count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, USER_DATA_TABLE);
    }

    public long user_Insert(String Name,String Email,String Mobile,String Gender,String BirthDate,String Category,String Password) {
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
            Log.d(TAG,"user table entry inserted");
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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }


    public boolean checkStudent(String email, String password){
        String[] columns = {
                KEY_ID
        };
        String category = "Student";
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?" + " AND " + KEY_CATEGORY + " = ? ";
        String[] selectionArgs = { email, password, category };

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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }

    public boolean checkEmployee(String email, String password){
        String[] columns = {
                KEY_ID
        };
        String category = "Employee";
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?" + " AND " + KEY_CATEGORY + " = ? ";
        String[] selectionArgs = { email, password, category };

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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }

    public boolean checkSeniorCitizen(String email, String password){
        String[] columns = {
                KEY_ID
        };
        String category = "Senior Citizen";
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?" + " AND " + KEY_CATEGORY + " = ? ";
        String[] selectionArgs = { email, password, category };

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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }

    public boolean checkCollageAuthority(String email, String password){
        String[] columns = {
                KEY_ID
        };
        String category = "Collage Authority";
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?" + " AND " + KEY_CATEGORY + " = ? ";
        String[] selectionArgs = { email, password, category };

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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }

    public boolean checkAmtsManager(String email, String password){
        String[] columns = {
                KEY_ID
        };
        String category = "AMTS Manager";
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?" + " AND " + KEY_CATEGORY + " = ? ";
        String[] selectionArgs = { email, password, category };

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
            Log.d(TAG,"Login Successful");
            return true;
        }
        return false;
    }



    public int user_update(int id, String Name,String Email,String Mobile,String Gender,String BirthDate,String Category,String Password) {
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

    public int user_Delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(USER_DATA_TABLE, //table name
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor user_Search(String searchString) {
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


    public StudentPass studentPass_query(int position) {
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
            entry.setsStatus(cursor.getColumnName(cursor.getColumnIndex(KEY_STATUS)));
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
                " WHERE "+ StudentPassOpenHelper.KEY_SCATEGORY + "=" + UserOpenHelper.KEY_CATEGORY ;

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
    public long student_count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, STUDENTPASS_DATA_TABLE);
    }

    public long studentPass_insert(String Category,int CollageName,String Department , String Enno , String Year, String Source , String Destination , String StartDate , String EndDate) {
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
        values.put(KEY_STATUS,"INVALID");
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(STUDENTPASS_DATA_TABLE, null, values);
            Log.d(TAG, "student pass entry insert");
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public List<PassStudent>  makeStudentList(String collageName){
        List<PassStudent> list=new ArrayList<>();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+STUDENTPASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+STUDENTPASS_DATA_TABLE+"."+KEY_SID+
                " LEFT JOIN "+COLLAGE_DATA_TABLE+
                " ON "+STUDENTPASS_DATA_TABLE+"."+KEY_SID+"="+COLLAGE_DATA_TABLE+"."+KEY_CID+
                " WHERE "+COLLAGE_DATA_TABLE+"."+KEY_CNAME+"='"+collageName+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        int cursorCount = c.getCount();
        if (cursorCount > 0){
            while (c.moveToNext()) {
                //int index = cursor.getColumnIndex(DataBaseHelper.UID);

                int uid = c.getColumnIndex(MainOpenHelper.KEY_ID);
                int Name = c.getColumnIndex(MainOpenHelper.KEY_NAME);
                int Email = c.getColumnIndex(MainOpenHelper.KEY_EMAIL);
                int Mobile = c.getColumnIndex(MainOpenHelper.KEY_MOBILE);
                int BirthDate = c.getColumnIndex(MainOpenHelper.KEY_BIRTHDAY);
                int sid = c.getColumnIndex(MainOpenHelper.KEY_SID);
                int cid = c.getColumnIndex(MainOpenHelper.KEY_COLLAGE);
                int collage = c.getColumnIndex(MainOpenHelper.KEY_CNAME);
                int Department = c.getColumnIndex(MainOpenHelper.KEY_DEPARTMENT);
                int year = c.getColumnIndex(MainOpenHelper.KEY_YEAR);
                int source = c.getColumnIndex(MainOpenHelper.KEY_SOURCE);
                int destination = c.getColumnIndex(MainOpenHelper.KEY_DESTINATION);
                int startDate = c.getColumnIndex(MainOpenHelper.KEY_STARTDATE);
                int endDate = c.getColumnIndex(MainOpenHelper.KEY_ENDDATE);
                int enrollment = c.getColumnIndex(MainOpenHelper.KEY_ENROLLMENT);
                int s = c.getColumnIndex(MainOpenHelper.KEY_STATUS);
                //int cid = cursor.getInt(index);
                int id = c.getInt(uid);
                String name = c.getString(Name);
                String email = c.getString(Email);
                String mobile = c.getString(Mobile);
                String birthDate = c.getString(BirthDate);
                int Sid = c.getInt(sid);
                int Cid = c.getInt(cid);
                String Collage = c.getString(collage);
                String department = c.getString(Department);
                String enroll = c.getString(enrollment);
                String Year = c.getString(year);
                String Source = c.getString(source);
                String Destination = c.getString(destination);
                String StartDate = c.getString(startDate);
                String EndDate = c.getString(endDate);
                String status = c.getString(s);
                PassStudent bean = new PassStudent(id,Sid,Cid,name,email,mobile,birthDate,Collage,department,enroll,Year,Source,Destination,StartDate,EndDate);
                list.add(bean);
            }
            c.close();
            db.close();
            Log.d(TAG,"Name Found");
            return list;
        }
                return list;
    }

    public List<PassStudent>  makeStudentRequest(){
        String status = "Valid";
        List<PassStudent> list=new ArrayList<>();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+STUDENTPASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+STUDENTPASS_DATA_TABLE+"."+KEY_SID+
                " LEFT JOIN "+COLLAGE_DATA_TABLE+
                " ON "+STUDENTPASS_DATA_TABLE+"."+KEY_SID+"="+COLLAGE_DATA_TABLE+"."+KEY_CID+
                " WHERE "+STUDENTPASS_DATA_TABLE+"."+KEY_STATUS+"='"+status+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        int cursorCount = c.getCount();
        if (cursorCount > 0){
            while (c.moveToNext()) {
                //int index = cursor.getColumnIndex(DataBaseHelper.UID);

                int uid = c.getColumnIndex(MainOpenHelper.KEY_ID);
                int Name = c.getColumnIndex(MainOpenHelper.KEY_NAME);
                int Email = c.getColumnIndex(MainOpenHelper.KEY_EMAIL);
                int Mobile = c.getColumnIndex(MainOpenHelper.KEY_MOBILE);
                int BirthDate = c.getColumnIndex(MainOpenHelper.KEY_BIRTHDAY);
                int sid = c.getColumnIndex(MainOpenHelper.KEY_SID);
                int cid = c.getColumnIndex(MainOpenHelper.KEY_COLLAGE);
                int collage = c.getColumnIndex(MainOpenHelper.KEY_CNAME);
                int Department = c.getColumnIndex(MainOpenHelper.KEY_DEPARTMENT);
                int year = c.getColumnIndex(MainOpenHelper.KEY_YEAR);
                int source = c.getColumnIndex(MainOpenHelper.KEY_SOURCE);
                int destination = c.getColumnIndex(MainOpenHelper.KEY_DESTINATION);
                int startDate = c.getColumnIndex(MainOpenHelper.KEY_STARTDATE);
                int endDate = c.getColumnIndex(MainOpenHelper.KEY_ENDDATE);
                int enrollment = c.getColumnIndex(MainOpenHelper.KEY_ENROLLMENT);
                int s = c.getColumnIndex(MainOpenHelper.KEY_STATUS);
                //int cid = cursor.getInt(index);
                int id = c.getInt(uid);
                String name = c.getString(Name);
                String email = c.getString(Email);
                String mobile = c.getString(Mobile);
                String birthDate = c.getString(BirthDate);
                int Sid = c.getInt(sid);
                int Cid = c.getInt(cid);
                String Collage = c.getString(collage);
                String department = c.getString(Department);
                String enroll = c.getString(enrollment);
                String Year = c.getString(year);
                String Source = c.getString(source);
                String Destination = c.getString(destination);
                String StartDate = c.getString(startDate);
                String EndDate = c.getString(endDate);
               // String status = c.getString(s);
                PassStudent bean = new PassStudent(id,Sid,Cid,name,email,mobile,birthDate,Collage,department,enroll,Year,Source,Destination,StartDate,EndDate);
                list.add(bean);
            }
            c.close();
            db.close();
            Log.d(TAG,"Name Found");
            return list;
        }
        return list;
    }

    public PassStudent  getStudentData(int pos){
        //PassStudent list=new PassStudent();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+STUDENTPASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+STUDENTPASS_DATA_TABLE+"."+KEY_SID+
                " LEFT JOIN "+COLLAGE_DATA_TABLE+
                " ON "+STUDENTPASS_DATA_TABLE+"."+KEY_SID+"="+COLLAGE_DATA_TABLE+"."+KEY_CID+
                " WHERE "+STUDENTPASS_DATA_TABLE+"."+KEY_SID+"="+pos+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        PassStudent s = new PassStudent();
            while (c.moveToNext()) {
                s.setSid(c.getInt(c.getColumnIndex(KEY_SID)));
                s.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                s.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                s.setMobileNo(c.getString(c.getColumnIndex(KEY_MOBILE)));
                s.setCollageName(c.getString(c.getColumnIndex(KEY_CNAME)));
                s.setDepartment(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));
                s.setEnrollment(c.getString(c.getColumnIndex(KEY_ENROLLMENT)));
                s.setYear(c.getString(c.getColumnIndex(KEY_YEAR)));
                s.setStartDate(c.getString(c.getColumnIndex(KEY_STARTDATE)));
                s.setEndDate(c.getString(c.getColumnIndex(KEY_ENDDATE)));
            }
            c.close();
            db.close();
            Log.d(TAG,"Name Found");
            return s;
    }


    public List<EmployeePass>  makeEmployeeList(){
        String Employee = "Employee";
        List<EmployeePass> list=new ArrayList<>();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+EMPLOYEE_PASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+EMPLOYEE_PASS_DATA_TABLE+"."+KEY_EID+
                " WHERE "+USER_DATA_TABLE+"."+KEY_CATEGORY+"='"+Employee+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        int cursorCount = c.getCount();
        if (cursorCount > 0){
            while (c.moveToNext()) {
                //int index = cursor.getColumnIndex(DataBaseHelper.UID);

                int uid = c.getColumnIndex(MainOpenHelper.KEY_EID);
                int Name = c.getColumnIndex(MainOpenHelper.KEY_NAME);
                int Email = c.getColumnIndex(MainOpenHelper.KEY_EMAIL);
                int Mobile = c.getColumnIndex(MainOpenHelper.KEY_MOBILE);
                int BirthDate = c.getColumnIndex(MainOpenHelper.KEY_BIRTHDAY);
                int gender = c.getColumnIndex(MainOpenHelper.KEY_GENDER);
                int eid = c.getColumnIndex(MainOpenHelper.KEY_EID);
                int jobProfile = c.getColumnIndex(MainOpenHelper.KEY_EJOBPROFILE);
                int jobAddress = c.getColumnIndex(MainOpenHelper.KEY_EJOBADDRESS);
                int source = c.getColumnIndex(MainOpenHelper.KEY_ESOURCE);
                int destination = c.getColumnIndex(MainOpenHelper.KEY_EDESTINATION);
                int startDate = c.getColumnIndex(MainOpenHelper.KEY_ESTARTDATE);
                int endDate = c.getColumnIndex(MainOpenHelper.KEY_EENDDATE);
                int s = c.getColumnIndex(MainOpenHelper.KEY_ESTATUS);
                //int cid = cursor.getInt(index);
                int id = c.getInt(uid);
                String name = c.getString(Name);
                String email = c.getString(Email);
                String mobile = c.getString(Mobile);
                String birthDate = c.getString(BirthDate);
                String Gender = c.getString(gender);
                String JobProfile = c.getString(jobProfile);
                String JobAddress = c.getString(jobAddress);
                String Source = c.getString(source);
                String Destination = c.getString(destination);
                String StartDate = c.getString(startDate);
                String EndDate = c.getString(endDate);
                String status = c.getString(s);
                EmployeePass bean = new EmployeePass(id,name,email,mobile,birthDate,Gender,JobProfile,JobAddress,Source,Destination,StartDate,EndDate);
                list.add(bean);
            }
            c.close();
            db.close();
            Log.d(TAG,"Employee List Found");
            return list;
        }
        return list;
    }

    public EmployeePass getEmployeeData(int pos){
        //PassStudent list=new PassStudent();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+EMPLOYEE_PASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+EMPLOYEE_PASS_DATA_TABLE+"."+KEY_EID+
                " WHERE "+EMPLOYEE_PASS_DATA_TABLE+"."+KEY_EID+"="+pos+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        EmployeePass s = new EmployeePass();
        while (c.moveToNext()) {
            s.setEid(c.getInt(c.getColumnIndex(KEY_SSID)));
            s.setEname(c.getString(c.getColumnIndex(KEY_NAME)));
            s.seteEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            s.seteMobile(c.getString(c.getColumnIndex(KEY_MOBILE)));
            s.seteBirthdate(c.getString(c.getColumnIndex(KEY_BIRTHDAY)));
            s.seteJobProfile(c.getString(c.getColumnIndex(KEY_EJOBPROFILE)));
            s.seteJobAddress(c.getString(c.getColumnIndex(KEY_EJOBADDRESS)));
            s.seteSource(c.getString(c.getColumnIndex(KEY_ESOURCE)));
            s.seteDestination(c.getString(c.getColumnIndex(KEY_EDESTINATION)));
        }
        c.close();
        db.close();
        Log.d(TAG,"Employee data found");
        return s;
    }
    public List<SeniorCitizenPass>  makeSeniorCitizenList(){
        String SeniorCitizen = "Senior Citizen";
        List<SeniorCitizenPass> list=new ArrayList<>();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+SENIOR_CITIZEN_PASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+SENIOR_CITIZEN_PASS_DATA_TABLE+"."+KEY_SSID+
                " WHERE "+USER_DATA_TABLE+"."+KEY_CATEGORY+"='"+SeniorCitizen+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        int cursorCount = c.getCount();
        if (cursorCount > 0){
            while (c.moveToNext()) {
                //int index = cursor.getColumnIndex(DataBaseHelper.UID);

                int uid = c.getColumnIndex(MainOpenHelper.KEY_SSID);
                int Name = c.getColumnIndex(MainOpenHelper.KEY_NAME);
                int Email = c.getColumnIndex(MainOpenHelper.KEY_EMAIL);
                int Mobile = c.getColumnIndex(MainOpenHelper.KEY_MOBILE);
                int BirthDate = c.getColumnIndex(MainOpenHelper.KEY_BIRTHDAY);
                int gender = c.getColumnIndex(MainOpenHelper.KEY_GENDER);
                int source = c.getColumnIndex(MainOpenHelper.KEY_SSSOURCE);
                int destination = c.getColumnIndex(MainOpenHelper.KEY_SSDESTINATION);
                int s = c.getColumnIndex(MainOpenHelper.KEY_SSSTATUS);
                //int cid = cursor.getInt(index);
                int id = c.getInt(uid);
                String name = c.getString(Name);
                String email = c.getString(Email);
                String mobile = c.getString(Mobile);
                String birthDate = c.getString(BirthDate);
                String Gender = c.getString(gender);
                String Source = c.getString(source);
                String Destination = c.getString(destination);
                String status = c.getString(s);
                SeniorCitizenPass bean = new SeniorCitizenPass(id,name,email,mobile,Gender,birthDate,Source,Destination);
                list.add(bean);
            }
            c.close();
            db.close();
            Log.d(TAG,"Senior Citizen List Found");
            return list;
        }
        return list;
    }

    public SeniorCitizenPass getSeniorCitizenData(int pos){
        //PassStudent list=new PassStudent();
        String Query = "SELECT * FROM "+USER_DATA_TABLE+
                " LEFT JOIN "+SENIOR_CITIZEN_PASS_DATA_TABLE+
                " ON "+USER_DATA_TABLE+"."+KEY_ID+"="+SENIOR_CITIZEN_PASS_DATA_TABLE+"."+KEY_SSID+
                " WHERE "+SENIOR_CITIZEN_PASS_DATA_TABLE+"."+KEY_SSID+"="+pos+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);
        SeniorCitizenPass s = new SeniorCitizenPass();
        while (c.moveToNext()) {
            s.setId(c.getInt(c.getColumnIndex(KEY_SSID)));
            s.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            s.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            s.setMobile(c.getString(c.getColumnIndex(KEY_MOBILE)));
            s.setSource(c.getString(c.getColumnIndex(KEY_SSSOURCE)));
            s.setDestination(c.getString(c.getColumnIndex(KEY_SSDESTINATION)));
        }
        c.close();
        db.close();
        Log.d(TAG,"Senior citizen data found");
        return s;
    }


    public int studentPass_update(int id,String Category,String CollageName,String Department,String Enno,String Year,String Source,String Destination,String StartDate,String EndDate) {
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

    public int studentPass_updateStatus(int id) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_STATUS, "Valid");
            
            mNumberOfRowsUpdated = mWritableDB.update(STUDENTPASS_DATA_TABLE, //table to change
                    values, // new values to insert
                    KEY_SID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)});
            //selection args; the actual value of the id
            Log.d(TAG,"Status updated");

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    public int studentPass_delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(STUDENTPASS_DATA_TABLE, //table name
                    KEY_SID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor studentPass_search(String searchString) {
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


    public long EmployeePass_insert(String Category,String JobProfile , String JobAddress , String Source , String Destination , String StartDate , String EndDate) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_ECATEGORY, Category);
        values.put(KEY_EJOBPROFILE, JobProfile);
        values.put(KEY_EJOBADDRESS, JobAddress);
        values.put(KEY_ESOURCE, Source);
        values.put(KEY_EDESTINATION, Destination);
        values.put(KEY_ESTARTDATE, StartDate);
        values.put(KEY_EENDDATE, EndDate);
        values.put(KEY_ESTATUS,"INVALID");
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(EMPLOYEE_PASS_DATA_TABLE, null, values);
            Log.d(TAG, "Employee pass entry insert");
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }


    public long seniorCitizenPass_insert(String Category,String Source , String Destination) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_SSCATEGORY, Category);
        values.put(KEY_SSSOURCE, Source);
        values.put(KEY_SSDESTINATION, Destination);
        values.put(KEY_SSSTATUS,"INVALID");
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(SENIOR_CITIZEN_PASS_DATA_TABLE, null, values);
            Log.d(TAG, "Senior Citizen  pass entry insert");
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }


   /* public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, USER_DATA_TABLE);
    }*/



    public Collage Collage_query(int position) {
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


    public long collage_count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, COLLAGE_DATA_TABLE);
    }

    public long collage_insert(String Name) {
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

    public int collage_update(int id, String Name) {
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

    public int collage_delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(COLLAGE_DATA_TABLE, //table name
                    KEY_CID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    public Cursor collage_search(String searchString) {
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
        Log.w(MainOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USER_DATA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTPASS_DATA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COLLAGE_DATA_TABLE);

        //  onCreate(db);
    }
}