package com.example.dkuqa;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";
    protected static final String TABLE_NAME = "Question";

    private final Context mContext;
    private SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;

    public DataAdapter (Context context) {
        this.mContext = context;
        mDBHelper = new DatabaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException {
        try {
            mDBHelper.createDatabase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException {
        try {
            mDBHelper.openDatabase();
            mDBHelper.close();
            mDB = mDBHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDBHelper.close();
    }

    public List getTableData() {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            List questionList = new ArrayList();
            Question question = null;

            Cursor mCur = mDB.rawQuery(sql, null);
            if (mCur != null) {
                while (mCur.moveToNext()) {
                    question = new Question();

                    question.setQno(mCur.getInt(0));
                    question.setQtitle(mCur.getString(1));
                    question.setQcontent(mCur.getString(2));
                    question.setQcategory(mCur.getString(3));

                    questionList.add(question);
                }
            }
            return questionList;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
}
