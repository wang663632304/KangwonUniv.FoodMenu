package kr.yudonguk.kangwonuniv.foodmenu.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kr.yudonguk.kangwonuniv.foodmenu.FoodMenuExpandableListAdapter;
import kr.yudonguk.kangwonuniv.foodmenu.R;
import kr.yudonguk.kangwonuniv.foodmenu.activity.SettingsActivity;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiView;
import kr.yudonguk.ui.UpdateResult;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

public class FoodMenuView extends UiView
		implements OnClickListener, OnDateSetListener
{
	static final String CURRENT_PAGE = "CURRENT_PAGE";

	PagerAdapter mPagerAdapter;
	FoodMenuPresenter mPresenter;

	View mLayout;
	ViewPager mViewPager;
	PagerTitleStrip mPagerTitleStrip;
	DatePickerDialog mDatePickerDialog;

	public FoodMenuView(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
	}

	@Override
	public View getView()
	{
		return mLayout;
	}

	@Override
	public View onEnabled(LayoutInflater inflater)
	{
		if (mLayout == null)
		{
			mLayout = inflater.inflate(R.layout.fragment_food_menu, null);

			mViewPager = (ViewPager) mLayout.findViewById(R.id.pager);
			mPagerTitleStrip = (PagerTitleStrip) mLayout
					.findViewById(R.id.pager_title_strip);

			mPagerAdapter = new FoodMenuPagerAdapter(mPresenter);
			mViewPager.setAdapter(mPagerAdapter);
			mPagerTitleStrip.setOnClickListener(this);

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 12);

			int itemIndex = (int) TimeUnit.MILLISECONDS.toDays(calendar
					.getTimeInMillis());
			mViewPager.setCurrentItem(itemIndex);
		}

		return mLayout;
	}

	@Override
	public void restoreState(Bundle savedState)
	{
		if (savedState == null)
			return;

		int itemIndex = savedState.getInt(CURRENT_PAGE, -1);
		if (itemIndex < 0)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 12);

			itemIndex = (int) TimeUnit.MILLISECONDS.toDays(calendar
					.getTimeInMillis());
		}
		mViewPager.setCurrentItem(itemIndex);
	}

	@Override
	public void saveState(Bundle outState)
	{
		int currentItem = mViewPager.getCurrentItem();
		outState.putInt(CURRENT_PAGE, currentItem);
	}

	@Override
	public void onDisabled()
	{}

	@Override
	public void onError(UpdateResult result)
	{}

	@Override
	public void update()
	{
		mPagerAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.food_menu_ui, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_settings:
			{
				Context context = mLayout.getContext();
				Intent intent = new Intent(context, SettingsActivity.class);
				context.startActivity(intent);
				return true;
			}
		case R.id.menu_refresh:
			update();
			return true;

		case R.id.menu_today:
			{
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 12);

				int itemIndex = (int) TimeUnit.MILLISECONDS.toDays(calendar
						.getTimeInMillis());

				mViewPager.setCurrentItem(itemIndex);
			}
			return true;
		case R.id.menu_sharing:
			{
				Context context = mLayout.getContext();
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(TimeUnit.DAYS.toMillis(mViewPager
						.getCurrentItem()));
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy.MM.dd E요일 강원대 식단", Locale.KOREAN);

				intent.putExtra(Intent.EXTRA_SUBJECT,
						dateFormat.format(calendar.getTime()));
				intent.putExtra(Intent.EXTRA_TEXT, "냠냠 쩝쩝");
				context.startActivity(Intent.createChooser(intent,
						context.getString(R.string.menu_sharing)));
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.pager_title_strip:
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(TimeUnit.DAYS.toMillis(mViewPager
						.getCurrentItem()));

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				if (mDatePickerDialog == null)
				{
					Context context = view.getContext();
					mDatePickerDialog = new DatePickerDialog(context, this,
							year, month, day);
				}
				else
				{
					mDatePickerDialog.getDatePicker().updateDate(year, month,
							day);
				}

				mDatePickerDialog.show();
			}
		}
	}

	@Override
	public void onDateSet(DatePicker picker, int year, int month, int day)
	{
		if (mDatePickerDialog != null
				&& mDatePickerDialog.getDatePicker() == picker)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			calendar.set(Calendar.HOUR_OF_DAY, 12);

			int itemIndex = (int) TimeUnit.MILLISECONDS.toDays(calendar
					.getTimeInMillis());

			mViewPager.setCurrentItem(itemIndex);
		}
	}
}

class FoodMenuPagerAdapter extends PagerAdapter
{
	SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd E",
			Locale.KOREAN);
	ArrayList<View> mViewList = new ArrayList<View>();

	FoodMenuPresenter mPresenter;

	public FoodMenuPagerAdapter(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
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
			view = View.inflate(container.getContext(),
					R.layout.fragment_dormitory_menu, null);

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				int id = view.hashCode();
				for (; container.findViewById(id) != null; id++)
					;
				view.setId(id);
			}
			else
			{
				view.setId(View.generateViewId());
			}
			mViewList.add(view);
		}

		updateView(view, position);
		container.addView(view);

		return view;
	}

	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
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
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.add(Calendar.DAY_OF_MONTH, position);

		return mDateFormat.format(calendar.getTime());
	}

	void updateView(final View view, final int position)
	{
		final ExpandableListView listView = (ExpandableListView) view
				.findViewById(R.id.foodListView);
		final ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);

		listView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);

		// 현재 View에서 사용할 데이터의 id를 저장해 두어
		// DataReceiver에서 View의 갱신 유무를 판별한다.
		view.setTag(position);

		mPresenter.getData(position, new DataReceiver<FoodMenu>()
		{
			@Override
			public void onReceived(int id, FoodMenu data)
			{
				// View가 재사용 될 경우 다른 DataReceiver에서
				// View를 갱신을 하므로, View를 갱신하지 않음
				int currentPosition = (Integer) view.getTag();
				if (currentPosition != position)
					return;

				listView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);

				if (data == null)
					return;

				listView.setAdapter(new FoodMenuExpandableListAdapter(data));
			}
		});
	}
}