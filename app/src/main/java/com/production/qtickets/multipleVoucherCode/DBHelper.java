package com.production.qtickets.multipleVoucherCode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.production.qtickets.model.CodesModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //database helper
    public static final String DATABASE_NAME_ = "voucherCodes.db";
    public static final int DATABASE_VERSION = 1;

    //User table
    public static final String TABLE_CODES = "codes";

    //users table rows
    public static final String CODES_COLUMN_ID = "code_id";
    public static final String CODES_COLUMN_NAME = "code_name";
    public static final String CODES_COLUMN_VALUE = "code_value";

    //quarries
    //create
    public static final String CREATE_TABLES_CODES = "create table " + TABLE_CODES +
            "(" +
            CODES_COLUMN_ID + " integer primary key," +
            CODES_COLUMN_NAME + " text," +
            CODES_COLUMN_VALUE + " text)";

    //drop
    public static final String DROP_TABLES_CODES = "DROP TABLE IF EXISTS " + TABLE_CODES;

    //select rows
    public static final String SELECT_ALL_CODES = "SELECT * FROM " + TABLE_CODES;

    //array of columns
    private static String[] CODE_COLUMNS = {CODES_COLUMN_ID,CODES_COLUMN_NAME, CODES_COLUMN_VALUE};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME_, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES_CODES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete old tables
        db.execSQL(DROP_TABLES_CODES);
        //creating tables again
        onCreate(db);
    }

    /**
     * it inserts user to table
     *
     * @param sqLiteDatabase writable db
     * @param codename
     * @param codevalue
     */
    public void addCode(SQLiteDatabase sqLiteDatabase, String codename, String codevalue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODES_COLUMN_NAME, codename);
        contentValues.put(CODES_COLUMN_VALUE, codevalue);
        //insert
        sqLiteDatabase.insert(TABLE_CODES, null, contentValues);
    }

    /**
     * returns all users
     */
    public List<CodesModel> getAllCodes(SQLiteDatabase sqLiteDatabase) {
        List<CodesModel> codesModels = new ArrayList<>();
        //get cur
        Cursor cursor = sqLiteDatabase.query(TABLE_CODES, CODE_COLUMNS, null, null, null, null, CODES_COLUMN_ID + " ASC");
        while (cursor.moveToNext()) {
            CodesModel codesModel = new CodesModel();
            codesModel.setId(cursor.getInt(cursor.getColumnIndex(CODES_COLUMN_ID)));
            codesModel.setCodename(cursor.getString(cursor.getColumnIndex(CODES_COLUMN_NAME)));
            codesModel.setCodevalue(cursor.getString(cursor.getColumnIndex(CODES_COLUMN_VALUE)));

            codesModels.add(codesModel);

        }

        return codesModels;
    }

    public void deleteAllCodes(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(TABLE_CODES, null, null);
    }
}
