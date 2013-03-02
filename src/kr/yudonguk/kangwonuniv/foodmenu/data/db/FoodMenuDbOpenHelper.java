package kr.yudonguk.kangwonuniv.foodmenu.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodMenuDbOpenHelper extends SQLiteOpenHelper
{
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "FoodMenu.db";

	public static final Column ID_COLUMN = new Column("id", 0);
	public static final Column DATA_COLUMN = new Column("data", 1);

	private final String[] mTableList = { "BaekRok", "CheonJi", "TaeBaek",
			"DormitoryFirst", "DormitoryThird" };

	private static FoodMenuDbOpenHelper mInstance;

	public synchronized static FoodMenuDbOpenHelper getInstance(Context context)
	{
		if (mInstance == null)
			mInstance = new FoodMenuDbOpenHelper(context);
		return mInstance;
	}

	private FoodMenuDbOpenHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		for (String table : mTableList)
		{
			createTable(db, table);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		for (String table : mTableList)
		{
			dropTable(db, table);
		}
		onCreate(db);
	}

	private void createTable(SQLiteDatabase db, String tableName)
	{
		final String sqlCreateTable = "CREATE TABLE IF NOT EXISTS '"
				+ tableName + "' ('" + ID_COLUMN + "' INTEGER PRIMARY KEY, '"
				+ DATA_COLUMN + "' BLOB)";
		db.execSQL(sqlCreateTable);
	}

	private void dropTable(SQLiteDatabase db, String tableName)
	{
		final String sqlDeleteTable = "DROP TABLE IF EXISTS '" + tableName
				+ "'";
		db.execSQL(sqlDeleteTable);
	}
}
