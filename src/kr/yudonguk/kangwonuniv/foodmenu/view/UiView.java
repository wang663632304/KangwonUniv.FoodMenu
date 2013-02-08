package kr.yudonguk.kangwonuniv.foodmenu.view;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class UiView
{
	public abstract View getView();

	public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		return false;
	}

	public void onCreateContextMenu(ContextMenu menu, MenuInflater inflater,
			View view, ContextMenuInfo menuInfo)
	{}

	public boolean onContextItemSelected(MenuItem item)
	{
		return false;
	}
}
