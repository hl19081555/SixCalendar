package com.example.android.sixcalendar.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.android.sixcalendar.entries.HistorySixMark;

import java.util.ArrayList;
import java.util.List;

public class SixMarkManager {

    private static SixMarkManager sInstance;
    private MySQLLiteHelper mDB;

    private static final int PAGE_COUNT = 50;

    private SixMarkManager() {
        mDB = new MySQLLiteHelper();
    }

    public static synchronized SixMarkManager getInstance() {
        if (sInstance == null) {
            sInstance = new SixMarkManager();
        }
        return sInstance;
    }

    public List<HistorySixMark> getHistorySixMark(int page) {
        Log.e("SixMarkManager", "getHistorySixMark enter");
        List<HistorySixMark> list = new ArrayList<>();
        SQLiteDatabase fSD = mDB.getWritableDatabase();
        if (fSD == null) {
            new Throwable(new SQLException());
        }
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = SixMarkContract.COLUMN_IDAY + " desc," + SixMarkContract.COLUMN_ISSUE + " desc";
        String limit = ((page - 1) * PAGE_COUNT) + "," + PAGE_COUNT;

        Cursor cursor = fSD.query(SixMarkContract.TABLE_SIX_MARK, columns,
                selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.e("SixMarkManager", "count = " + cursor.getCount());
                do {
                    HistorySixMark item = new HistorySixMark();
                    item.setPreDrawDate(cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_IDAY)));
                    item.setIssue(cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_ISSUE)));
                    item.setColor(cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_COLOR)));
                    String pm1 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM1));
                    String pm2 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM2));
                    String pm3 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM3));
                    String pm4 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM4));
                    String pm5 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM5));
                    String pm6 = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_PM6));
                    String tm = cursor.getString(cursor.getColumnIndex(SixMarkContract.COLUMN_TM));
                    item.setPreDrawCode(pm1 + "," + pm2 + "," + pm3 + "," + pm4 + "," + pm5 + "," + pm6 + "," + tm);
                    list.add(item);
                    // Log.e("SixMarkManager", "item = " + item);
                } while (cursor.moveToNext());
            } else {
                Log.e("SixMarkManager", "getHistorySixMark cursor no data");
            }
            cursor.close();
            cursor = null;
        } else {
            Log.e("SixMarkManager", "cursor is null");
        }
        return list;
    }

    public int insertHistorySixMark(HistorySixMark item) {
        SQLiteDatabase fSD = mDB.getWritableDatabase();
        if (fSD == null) {
            new Throwable(new SQLException());
        }
        Cursor cursor = fSD.query(SixMarkContract.TABLE_SIX_MARK, null,
                SixMarkContract.COLUMN_IDAY + " = ? and " + SixMarkContract.COLUMN_ISSUE + " = ?",
                new String[]{item.getPreDrawDate(), item.getIssue()}, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                Log.e("SixMarkManager", "该条记录已经存在，无需重新插入.");
                return 0;
            }
            cursor.close();
            cursor = null;
        }
        long count = fSD.insert(SixMarkContract.TABLE_SIX_MARK, null, toContentValues(item));
        return (int) count;
    }

    public int bulkInsertHistorySixMark(List<HistorySixMark> list) {
        int count = 0;
        if (list != null && list.size() > 0) {

            SQLiteDatabase fSD = mDB.getWritableDatabase();
            if (fSD == null) {
                new Throwable(new SQLException());
            }
            try {
                fSD.beginTransaction();

                for (HistorySixMark item : list) {
                    fSD.insert(SixMarkContract.TABLE_SIX_MARK, null, toContentValues(item));
                    count++;
                }
                fSD.setTransactionSuccessful();
                fSD.endTransaction();
                fSD.close();
                fSD = null;
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                if (fSD != null) {
                    fSD.close();
                    fSD = null;
                }
            }
        }
        Log.e("SixMarkManager", "count = " + count);
        return count;
    }

    public ContentValues toContentValues(HistorySixMark item) {
        ContentValues c = new ContentValues();
        c.put(SixMarkContract.COLUMN_IDAY, item.getPreDrawDate());
        c.put(SixMarkContract.COLUMN_ISSUE, item.getIssue());
        c.put(SixMarkContract.COLUMN_COLOR, item.getColor());

        c.put(SixMarkContract.COLUMN_PM1, item.getPM1());
        c.put(SixMarkContract.COLUMN_PM2, item.getPM2());
        c.put(SixMarkContract.COLUMN_PM3, item.getPM3());
        c.put(SixMarkContract.COLUMN_PM4, item.getPM4());
        c.put(SixMarkContract.COLUMN_PM5, item.getPM5());
        c.put(SixMarkContract.COLUMN_PM6, item.getPM6());
        c.put(SixMarkContract.COLUMN_TM, item.getTM());

        c.put(SixMarkContract.COLUMN_PMSX1, item.getPMSX1());
        c.put(SixMarkContract.COLUMN_PMSX2, item.getPMSX2());
        c.put(SixMarkContract.COLUMN_PMSX3, item.getPMSX3());
        c.put(SixMarkContract.COLUMN_PMSX4, item.getPMSX4());
        c.put(SixMarkContract.COLUMN_PMSX5, item.getPMSX5());
        c.put(SixMarkContract.COLUMN_PMSX6, item.getPMSX6());
        c.put(SixMarkContract.COLUMN_TMSX, item.getTMSX());
        return c;
    }

    public int clearData() {
        SQLiteDatabase fSD = mDB.getWritableDatabase();
        if (fSD == null) {
            new Throwable(new SQLException());
        }
        int count = fSD.delete(SixMarkContract.TABLE_SIX_MARK, null, null);
        Log.d("SixMarkManager", "clearData : count = " + count);
        return count;
    }

    public int deleteLastTwoYear(int year) {
        SQLiteDatabase fSD = mDB.getWritableDatabase();
        if (fSD == null) {
            new Throwable(new SQLException());
        }
        int count = fSD.delete(SixMarkContract.TABLE_SIX_MARK,
                SixMarkContract.COLUMN_IDAY + " like '" + year + "%' or " +
                        SixMarkContract.COLUMN_IDAY + " like '" + (year - 1) + "%'", null);
        Log.d("SixMarkManager", "deleteLastTwoYear : count = " + count);
        return count;
    }
}
