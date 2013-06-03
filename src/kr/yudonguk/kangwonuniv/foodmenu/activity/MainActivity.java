package kr.yudonguk.kangwonuniv.foodmenu.activity;

import kr.yudonguk.kangwonuniv.foodmenu.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements
		OnNavigationListener
{
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	String[] mRestaurantNames = {};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		mRestaurantNames = getResources().getStringArray(
				R.array.restaurant_list);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActionBarThemedContextCompat(),
				android.R.layout.simple_dropdown_item_1line,
				android.R.id.text1, mRestaurantNames);

		actionBar.setListNavigationCallbacks(arrayAdapter, this);

		String defaultRestaurant = preferences.getString("default_restaurant",
				mRestaurantNames[0]);

		int index = 0;
		for (String restaurant : mRestaurantNames)
		{
			if (defaultRestaurant.equals(restaurant))
				break;
			index++;
		}
		actionBar.setSelectedNavigationItem(index);
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			return getActionBar().getThemedContext();
		}
		else
		{
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
		{
			getSupportActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id)
	{
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new FoodMenuFragment();
		Bundle args = new Bundle();
		args.putString(FoodMenuFragment.ARG_RESTAURANT_NAME,
				mRestaurantNames[position]);
		fragment.setArguments(args);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();

		return true;
	}
}