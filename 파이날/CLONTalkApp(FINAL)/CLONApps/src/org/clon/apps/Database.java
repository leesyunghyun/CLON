package org.clon.apps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	private SQLiteDatabase db;
	static Database data;
	private Context context;
	private openhelper openhelper;

	public Database(Context context) {
		this.context = context;

	}

	public static Database getData(Context context) {
		if (data == null) {
			data = new Database(context);
		}
		return data;
	}

	public boolean open() {
		openhelper = new openhelper(context);
		db = openhelper.getWritableDatabase();
		return true;
	}

	public void close() {
		{
			db.close();
			data = null;
		}
	}

	public Cursor rawQuery(String sql) {
		Cursor c1 = null;
		c1 = db.rawQuery(sql, null);

		return c1;
	}

	public boolean exec(String sql) {
		db.execSQL(sql);
		return true;
	}

	class openhelper extends SQLiteOpenHelper {

		public openhelper(Context context) {
			super(context, "setting", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "create table setting(ip varchar(30) ,port int);";
			db.execSQL(sql);
			sql = "create table saved(id varchar(30), pw varchar(30), flag varchar(10));";
			db.execSQL(sql);
			sql = "insert into setting values('203.237.140.115',8282);";
			db.execSQL(sql);
			sql = "insert into saved values('CLON','1234','x');";
			db.execSQL(sql);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}
}
