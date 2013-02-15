package kr.yudonguk.kangwonuniv.foodmenu.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kr.yudonguk.kangwonuniv.foodmenu.R;
import kr.yudonguk.kangwonuniv.foodmenu.activity.SettingsActivity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.Toast;

public class FoodMenuUiView extends UiView implements OnClickListener,
		OnDateSetListener
{
	PagerAdapter mPagerAdapter;

	View mLayout;
	ViewPager mViewPager;
	PagerTitleStrip mPagerTitleStrip;
	DatePickerDialog mDatePickerDialog;

	public FoodMenuUiView(PagerAdapter pagerAdapter)
	{
		mPagerAdapter = pagerAdapter;
	}

	@Override
	public View getView()
	{
		return mLayout;
	}

	@Override
	public View onLoaded(LayoutInflater inflater, Bundle savedState)
	{
		if (mLayout != null)
			return mLayout;

		mLayout = inflater.inflate(R.layout.fragment_food_menu, null);

		mViewPager = (ViewPager) mLayout.findViewById(R.id.pager);
		mPagerTitleStrip = (PagerTitleStrip) mLayout
				.findViewById(R.id.pager_title_strip);

		// ////////////////////////////////////////
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);

		int itemIndex = (int) TimeUnit.MILLISECONDS.toDays(calendar
				.getTimeInMillis());

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(itemIndex);

		// ////////////////////////////////////////
		mPagerTitleStrip.setOnClickListener(this);

		return mLayout;
	}

	@Override
	public void onUnloaded(Bundle outState)
	{
	}

	@Override
	public void update()
	{
		if (mLayout != null)
			mLayout.invalidate();
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
				Context context = item.getActionView().getContext();
				Intent intent = new Intent(context, SettingsActivity.class);
				context.startActivity(intent);
				return true;
			}
			case R.id.menu_refresh:
			{
				Context context = item.getActionView().getContext();
				Toast.makeText(context, "새로고침", Toast.LENGTH_SHORT).show();
			}
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
				Context context = item.getActionView().getContext();
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