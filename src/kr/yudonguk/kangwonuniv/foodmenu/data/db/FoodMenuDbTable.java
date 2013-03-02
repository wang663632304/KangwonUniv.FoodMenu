package kr.yudonguk.kangwonuniv.foodmenu.data.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodMenuDbTable
{
	private final String TABLE_NAME;
	private final FoodMenuDbOpenHelper mDbOpenHelper;

	public FoodMenuDbTable(String name, Context context)
	{
		TABLE_NAME = name;
		mDbOpenHelper = FoodMenuDbOpenHelper.getInstance(context);
	}

	public FoodMenu get(int id)
	{
		SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

		Column idColumn = FoodMenuDbOpenHelper.ID_COLUMN;
		Column dataColumn = FoodMenuDbOpenHelper.DATA_COLUMN;

		String[] columns = { idColumn.name, dataColumn.name };

		final String selection = idColumn.name + "=" + id;
		Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null,
				null, null);

		if (!cursor.moveToFirst())
			return null;

		byte[] objectRaw = cursor.getBlob(dataColumn.index);
		ObjectInputStream objectInputStream = null;
		try
		{
			objectInputStream = new ObjectInputStream(new ByteArrayInputStream(
					objectRaw));
			return (FoodMenu) objectInputStream.readObject();
		} catch (StreamCorruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (objectInputStream != null)
				try
				{
					objectInputStream.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return null;
	}

	public void set(int id, FoodMenu foodMenu) throws IOException
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(
				byteArrayOutputStream);

		outputStream.writeObject(foodMenu);

		outputStream.close();

		SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

		Column idColumn = FoodMenuDbOpenHelper.ID_COLUMN;
		Column dataColumn = FoodMenuDbOpenHelper.DATA_COLUMN;

		ContentValues contentValues = new ContentValues();
		contentValues.put(idColumn.name, id);
		contentValues.put(dataColumn.name, byteArrayOutputStream.toByteArray());

		final String whereClause = idColumn.name + "=" + id;

		if (db.update(TABLE_NAME, contentValues, whereClause, null) == 0)
		{
			db.insert(TABLE_NAME, "", contentValues);
		}
	}
}
