package com.example.android.sixcalendar.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sixcalendar.activity.SixCalendarApp;

import static com.example.android.sixcalendar.database.SixMarkContract.TABLE_SIX_MARK;

public class MySQLLiteHelper extends SQLiteOpenHelper {
    private static String db_name = "sixcalendar.db";
    private static int db_version = 1;

    public MySQLLiteHelper() {
        super(SixCalendarApp.getAppContext(), db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateSixMarkTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String getCreateSixMarkTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_SIX_MARK + "(" +
                SixMarkContract.COLUMN_IDAY  + " TEXT not NULL," +
                SixMarkContract.COLUMN_ISSUE + " TEXT not NULL," +
                SixMarkContract.COLUMN_COLOR + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM1   + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM2   + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM3   + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM4   + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM5   + " TEXT not NULL," +
                SixMarkContract.COLUMN_PM6   + " TEXT not NULL," +
                SixMarkContract.COLUMN_TM    + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX1 + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX2 + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX3 + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX4 + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX5 + " TEXT not NULL," +
                SixMarkContract.COLUMN_PMSX6 + " TEXT not NULL," +
                SixMarkContract.COLUMN_TMSX  + " TEXT not NULL," +
                "unique (" + SixMarkContract.COLUMN_IDAY + "," + SixMarkContract.COLUMN_ISSUE + ")" +
                ")";
        return sql;
    }
}
