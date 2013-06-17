package kr.yudonguk.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class UiView<Data, Identifier>
{
	public abstract View getView();

	public abstract View onEnabled(LayoutInflater inflater,
			UiPresenter<Data, Identifier> presenter);

	public abstract void onDisabled();

	public abstract void onError(UpdateResult result);

	public abstract void update();

	public abstract void restoreState(Bundle savedState);

	public abstract void saveState(Bundle outState);

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
	{
	}

	public boolean onContextItemSelected(MenuItem item)
	{
		return false;
	}
}
