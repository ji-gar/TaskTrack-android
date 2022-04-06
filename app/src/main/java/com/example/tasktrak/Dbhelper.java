package com.example.tasktrak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class Dbhelper extends SQLiteOpenHelper {
   private static String dbname = "tasktabel.db";

   public Dbhelper(@Nullable Context context) {
      super(context, dbname, null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {
      String q = "create table mydata(id  integer primary key autoincrement,taskname text,mincount integer,maxcount integer)";
      sqLiteDatabase.execSQL(q);
   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mydata");
      onCreate(sqLiteDatabase);
   }

   public String addData(String taskname, int mincount, int maxcount) {
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put("taskname", taskname);
      cv.put("mincount", mincount);
      cv.put("maxcount", maxcount);
      long res = sqLiteDatabase.insert("mydata", null, cv);
      if (res == -1) {
         return "failed";
      } else {
         return "sucessfully";
      }

   }

   public Cursor fetch() {
      SQLiteDatabase db = this.getWritableDatabase();
      String query = "select * from mydata";
      Cursor cursor = db.rawQuery(query, null);
      return cursor;
   }

   public boolean delete(String titlename) {
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery("select * from mydata where taskname=?", new String[]{titlename});
      if (cursor.getCount() > 0) {
         long r = db.delete("mydata", "taskname=?", new String[]{titlename});
         if (r == -1) {
            return false;
         } else {
            return true;
         }
      }
      return true;
   }
   public  boolean update(String titlename,int mincount)
   {
      SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put("taskname",titlename);
      cv.put("mincount", mincount);
      sqLiteDatabase.update("mydata",cv,"taskname"+ "= ?",new String[]{titlename});
      sqLiteDatabase.close();
      return true;
   }

   }

