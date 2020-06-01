package com.example.dkuqa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class QuestionDatabaseManager {
    private static final String DB_PATH = "/data/data/com.example.dkuqa/databases/";
    private static final String DB_NAME = "dku_QA.db";

    private String myPath = DB_PATH + DB_NAME;

    Context myContext = null;

    private static QuestionDatabaseManager QuestionDBManager = null;
    private SQLiteDatabase database = null;

    public static synchronized QuestionDatabaseManager getInstance(Context context) {
        if (QuestionDBManager == null) {
            QuestionDBManager = new QuestionDatabaseManager(context);
        }
        return QuestionDBManager;
    }

    private QuestionDatabaseManager(Context context) {
        myContext = context;

        try {
            createDatabase();
            database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (IOException e) {
            println("DB 파일을 생성할 수 없습니다.");
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (checkDB != null) { checkDB.close(); }
        if (checkDB != null) return true;
        else return false;
    }   // 해당 경로에 DB파일이 있는지 확인 (정상 작동함)

    private void copyDatabase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0 , length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }   // DB파일 복사

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            try {
                myContext.openOrCreateDatabase(DB_NAME, myContext.MODE_PRIVATE, null);  // DB파일 생성 후
                copyDatabase(); // 복사
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }   // DB파일 생성

    public Cursor query(String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
    String orderby)
    {
        return database.query("Question",
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderby);
    }   // DB 조회

    private void println(String data) {
        Toast.makeText(myContext, data, Toast.LENGTH_LONG).show();
    }
}
