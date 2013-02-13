package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.Date;
import java.util.Random;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FoodMenuExpandableListAdapter extends BaseExpandableListAdapter
{
	public FoodMenuExpandableListAdapter()
	{
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
		if (groupPosition % 4 == 0)
			return null;

		if (convertView == null)
		{
			convertView = View.inflate(parent.getContext(),
					R.layout.food_menu_item, parent);
		}

		TextView textView = (TextView) convertView
				.findViewById(R.id.foodTextView);
		RatingBar ratingBar = (RatingBar) convertView
				.findViewById(R.id.ratingBar);

		Random random = new Random(new Date().getTime());
		ratingBar.setRating(random.nextInt(1000) / 1000.0f);
		textView.setText("" + groupPosition + ":" + childPosition);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		if (groupPosition % 4 == 0)
			return 0;

		return 4;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public int getGroupCount()
	{
		return 15;
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

		if (groupPosition % 4 == 0)
		{
			indicator.setVisibility(View.VISIBLE);
			group.setVisibility(View.GONE);
		}
		else
		{
			indicator.setVisibility(View.GONE);
			group.setVisibility(View.VISIBLE);

			TextView textView = (TextView) group
					.findViewById(R.id.foodGroupTextView);
			ImageView imageView = (ImageView) group
					.findViewById(R.id.foodGroupIndicator);

			if (isExpanded)
				imageView
						.setImageResource(R.drawable.expander_close_holo_light);
			else
				imageView.setImageResource(R.drawable.expander_open_holo_light);

			textView.setText("" + groupPosition);
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