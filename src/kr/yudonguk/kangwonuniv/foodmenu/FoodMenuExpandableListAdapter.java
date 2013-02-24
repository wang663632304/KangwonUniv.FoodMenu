package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.Calendar;

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
	private static class GroupViewHolder
	{
		// Section 표시할 때 사용
		public View section = null;
		public TextView titleView = null;
		public TextView subtitleView = null;

		// 그룹으로 사용하지 않을 때 사용
		public View group = null;
		public TextView textView = null;
		public ImageView imageView = null;
		public RatingBar ratingBar = null;
	}

	private static class ChildViewHolder
	{
		public TextView textView = null;
		public RatingBar ratingBar = null;
	}

	private final int HEADER_COUNT = 1;
	private FoodMenu mFoodMenu;

	public FoodMenuExpandableListAdapter()
	{
		this(new FoodMenu());
	}

	public FoodMenuExpandableListAdapter(FoodMenu foodMenu)
	{
		mFoodMenu = foodMenu;
	}

	public void change(FoodMenu foodMenu)
	{
		synchronized (mFoodMenu)
		{
			mFoodMenu = foodMenu;
		}
		notifyDataSetChanged();
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

		synchronized (mFoodMenu)
		{
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
		}
		if (food == null)
			return null;

		if (convertView == null)
		{
			convertView = View.inflate(parent.getContext(),
					R.layout.food_menu_item, null);

			ChildViewHolder viewHolder = new ChildViewHolder();

			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.foodTextView);
			viewHolder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratingBar);

			convertView.setTag(viewHolder);
		}

		ChildViewHolder viewHolder = (ChildViewHolder) convertView.getTag();

		viewHolder.textView.setText(food.name);
		viewHolder.ratingBar.setRating(food.rate);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		synchronized (mFoodMenu)
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

		synchronized (mFoodMenu)
		{
			for (Section section : mFoodMenu.sections)
			{
				groupCount += HEADER_COUNT;// 헤더
				groupCount += section.size();// 그룹원
			}
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

			GroupViewHolder viewHolder = new GroupViewHolder();

			viewHolder.section = convertView
					.findViewById(R.id.foodMenuIndocator);
			viewHolder.titleView = (TextView) convertView
					.findViewById(R.id.titleTextView);
			viewHolder.subtitleView = (TextView) convertView
					.findViewById(R.id.subtitleTextView);

			viewHolder.group = convertView.findViewById(R.id.foodMenuGroup);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.foodGroupTextView);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.foodGroupIndicator);
			viewHolder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratingBar);

			convertView.setTag(viewHolder);
		}

		Section section = null;
		synchronized (mFoodMenu)
		{
			for (Section item : mFoodMenu.sections)
			{
				if (groupPosition == 0)
				{
					section = item;
					break;
				}

				groupPosition -= HEADER_COUNT; // 헤더 제거

				if (groupPosition < item.size())
				{
					section = item;
					groupPosition += 1;
					break;
				}

				groupPosition -= item.size();// 그룹원 제거
			}
		}
		if (section == null)
			return null;

		GroupViewHolder viewHolder = (GroupViewHolder) convertView.getTag();

		if (groupPosition == 0)
		{
			viewHolder.section.setVisibility(View.VISIBLE);
			viewHolder.group.setVisibility(View.GONE);

			TextView titleTextView = viewHolder.titleView;
			TextView subtitleTextView = viewHolder.subtitleView;

			titleTextView.setText(section.name);

			int startHour = section.startTime.get(Calendar.HOUR_OF_DAY);
			int startMinute = section.startTime.get(Calendar.HOUR_OF_DAY);
			int endHour = section.endTime.get(Calendar.HOUR_OF_DAY);
			int endMinute = section.endTime.get(Calendar.MINUTE);

			if (startHour == 0 && startMinute == 0 && endHour == 0
					&& endMinute == 0)
			{
				subtitleTextView.setText("");
			}
			else
			{
				StringBuilder subtitle = new StringBuilder();
				subtitle.append(startHour);
				subtitle.append(":");
				subtitle.append(startMinute);
				subtitle.append("~");
				subtitle.append(endHour);
				subtitle.append(":");
				subtitle.append(endMinute);
				subtitleTextView.setText(subtitle);
			}
		}
		else
		{
			Food food = section.get(groupPosition - 1);

			viewHolder.section.setVisibility(View.GONE);
			viewHolder.group.setVisibility(View.VISIBLE);

			TextView textView = viewHolder.textView;
			ImageView imageView = viewHolder.imageView;
			RatingBar ratingBar = viewHolder.ratingBar;

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