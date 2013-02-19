package kr.yudonguk.kangwonuniv.foodmenu;

import java.text.SimpleDateFormat;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.FoodGroup;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Section;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FoodMenuExpandableListAdapter extends BaseExpandableListAdapter
{
	final int HEADER_COUNT = 1;
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	FoodMenu mFoodMenu;

	public FoodMenuExpandableListAdapter(FoodMenu foodMenu)
	{
		mFoodMenu = foodMenu;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return getCombinedChildId(groupPosition, childPosition);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent)
	{
		Food food = null;
		for (Section section : mFoodMenu.sections)
		{
			if (groupPosition == 0)
				return null;

			groupPosition -= HEADER_COUNT; // 헤더 제거

			if (groupPosition < section.size())
			{
				if (section.get(groupPosition).isGroup())
				{
					FoodGroup foodGroup = (FoodGroup) section
							.get(groupPosition);

					food = foodGroup.get(childPosition);
					break;
				}
				else
				{
					return null;
				}
			}

			groupPosition -= section.size();// 그룹원 제거
		}
		if (food == null)
			return null;

		if (convertView == null)
		{
			convertView = View.inflate(parent.getContext(),
					R.layout.food_menu_item, null);
		}

		TextView textView = (TextView) convertView
				.findViewById(R.id.foodTextView);
		RatingBar ratingBar = (RatingBar) convertView
				.findViewById(R.id.ratingBar);

		textView.setText(food.name);
		ratingBar.setRating(food.rate);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		for (Section section : mFoodMenu.sections)
		{
			if (groupPosition == 0)
				return 0;

			groupPosition -= HEADER_COUNT; // 헤더 제거

			if (groupPosition < section.size())
			{
				Food food = section.get(groupPosition);
				if (food.isGroup())
				{
					return ((FoodGroup) food).size();
				}
				else
				{
					return 0;
				}
			}

			groupPosition -= section.size();// 그룹원 제거
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public int getGroupCount()
	{
		int groupCount = 0;

		for (Section section : mFoodMenu.sections)
		{
			groupCount += HEADER_COUNT;// 헤더
			groupCount += section.size();// 그룹원
		}

		return groupCount;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return getCombinedGroupId(groupPosition);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = View.inflate(parent.getContext(),
					R.layout.food_menu_group, null);
		}

		View indicator = convertView.findViewById(R.id.foodMenuIndocator);
		View group = convertView.findViewById(R.id.foodMenuGroup);

		for (Section section : mFoodMenu.sections)
		{
			if (groupPosition == 0)
			{
				indicator.setVisibility(View.VISIBLE);
				group.setVisibility(View.GONE);

				TextView titleTextView = (TextView) indicator
						.findViewById(R.id.titleTextView);
				TextView subtitleTextView = (TextView) indicator
						.findViewById(R.id.subtitleTextView);

				titleTextView.setText(section.name);
				subtitleTextView.setText(timeFormat.format(section.startTime)
						+ "~" + timeFormat.format(section.endTime));

				break;
			}

			groupPosition -= HEADER_COUNT; // 헤더 제거

			if (groupPosition < section.size())
			{
				Food food = section.get(groupPosition);

				indicator.setVisibility(View.GONE);
				group.setVisibility(View.VISIBLE);

				TextView textView = (TextView) group
						.findViewById(R.id.foodGroupTextView);
				ImageView imageView = (ImageView) group
						.findViewById(R.id.foodGroupIndicator);
				RatingBar ratingBar = (RatingBar) group
						.findViewById(R.id.ratingBar);

				if (food.isGroup())
				{
					if (isExpanded)
						imageView
								.setImageResource(R.drawable.expander_close_holo_light);
					else
						imageView
								.setImageResource(R.drawable.expander_open_holo_light);

					float average = 0.0f;
					int itemCount = 0;
					for (Food item : ((FoodGroup) food).foods)
					{
						if (item.rate < 0)
							continue;

						itemCount++;
						average += item.rate;
					}
					average /= itemCount;
					ratingBar.setRating(average);
				}
				else
				{
					imageView.setImageBitmap(null);
					ratingBar.setRating(food.rate);
				}

				textView.setText(food.name);
				break;
			}

			groupPosition -= section.size();// 그룹원 제거
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
}