package kr.yudonguk.kangwonuniv.foodmenu.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kr.yudonguk.kangwonuniv.foodmenu.FoodMenuExpandableListAdapter;
import kr.yudonguk.kangwonuniv.foodmenu.R;
import kr.yudonguk.kangwonuniv.foodmenu.view.FoodMenuUiView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
	PagerAdapter mPagerAdapter;

	FoodMenuUiView mUiView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mUiView = new FoodMenuUiView(new TempPagerAdpater());

		setHasOptionsMenu(true);

		return mUiView.onLoaded(inflater, savedInstanceState);
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
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd E",
				Locale.KOREAN);

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			Fragment fragment = new DummySectionFragment();

			Bundle args = new Bundle();
			args.putString(
					DummySectionFragment.ARG_SELARG_SECTION_RESTAURANT,
					FoodMenuFragment.this.getArguments().getString(
							ARG_RESTAURANT_NAME));
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount()
		{
			// ViewPager의 페이지수를 무한대로 하기위해서
			// getCount() 값을 Integer.MAX_VALUE 으로 하게 될 경우
			// 다시 말해 getCount() 값이 클 경우
			// ViewPager 의 문제로 인해 setCurrentItem() 메소드를
			// 통한 페이지 이동에 많은 시간을 소요하게 되어 프로그램이 멈추게 된다.
			// 이러한 현상은 현재 페이지의 위치가 이동하려는 페이지의
			// 위치보다 높을 때 발생한다.
			// 따라서 getCount() 의 값을 적정 수준으로 설정해야한다.

			return 365 * 60;// 1970 부터 약 60년 까지를 전체 페이지의 수로 잡는다.
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(TimeUnit.DAYS.toMillis(position));

			return mDateFormat.format(calendar.getTime());
		}
	}

	public class TempPagerAdpater extends PagerAdapter
	{
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd E",
				Locale.KOREAN);

		ArrayList<View> mViewList = new ArrayList<View>();

		public TempPagerAdpater()
		{
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
			{

				@Override
				public void uncaughtException(Thread thread, Throwable ex)
				{

				}
			});
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			View view = null;

			for (View viewItem : mViewList)
			{
				if (container.findViewById(viewItem.getId()) == null)
				{
					view = viewItem;
					break;
				}
			}

			if (view == null)
			{
				view = createPageView(container);
				view.setId(view.hashCode());
				mViewList.add(view);
			}

			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		@Override
		public int getCount()
		{
			return 365 * 60;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(TimeUnit.DAYS.toMillis(position));

			return mDateFormat.format(calendar.getTime());
		}

		View createPageView(ViewGroup container)
		{
			View view = View.inflate(container.getContext(),
					R.layout.fragment_dormitory_menu, null);

			ExpandableListView listView = (ExpandableListView) view
					.findViewById(R.id.foodListView);
			listView.setAdapter(new FoodMenuExpandableListAdapter());

			return view;
		}
	}

}

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
class DummySectionFragment extends Fragment
{
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SELARG_SECTION_RESTAURANT = "section_restaurant";
	static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_dormitory_menu,
				container, false);

		ExpandableListView listView = (ExpandableListView) view
				.findViewById(R.id.foodListView);
		listView.setAdapter(new FoodMenuExpandableListAdapter());

		return view;
	}
}
