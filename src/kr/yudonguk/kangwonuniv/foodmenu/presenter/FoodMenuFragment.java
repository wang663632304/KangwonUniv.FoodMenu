package kr.yudonguk.kangwonuniv.foodmenu.presenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import kr.yudonguk.kangwonuniv.foodmenu.view.FoodMenuUiView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FoodMenuFragment extends Fragment
{
	public static final String ARG_RESTAURANT_NAME = "restaurant_name";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	FoodMenuUiView mUiView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mUiView = new FoodMenuUiView(container, savedInstanceState,
				mSectionsPagerAdapter, getActivity());

		setHasOptionsMenu(true);

		return mUiView.getView();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		mUiView.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mUiView.onOptionsItemSelected(item) ? true : super
				.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd E");

		// ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);

			/*
			 * for (int i = 0; i < 4; i++) { mFragmentList.add(new
			 * DummySectionFragment(getArguments()
			 * .getString(ARG_RESTAURANT_NAME))); }
			 */
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			Fragment fragment = new DummySectionFragment(getArguments()
					.getString(ARG_RESTAURANT_NAME));

			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount()
		{
			return Integer.MAX_VALUE;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(TimeUnit.DAYS.toMillis(position));

			return mDateFormat.format(calendar.getTime());
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment
	{
		String mRestaurantName;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment(String restaurantName)
		{
			mRestaurantName = restaurantName;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);

			textView.setText(mRestaurantName
					+ Integer.toString(getArguments()
							.getInt(ARG_SECTION_NUMBER)));

			return textView;
		}
	}
}
