package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.Date;
import java.util.Random;

import kr.yudonguk.kangwonuniv.foodmenu.model.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.presenter.DataReceiver;
import kr.yudonguk.kangwonuniv.foodmenu.presenter.FoodMenuPresenter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FoodMenuExpandableListAdapter extends BaseExpandableListAdapter
{
	FoodMenuPresenter mPresenter;

	public FoodMenuExpandableListAdapter(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
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
		if (groupPosition % 2 == 0)
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

		Random random = new Random(new Date().getTime());
		ratingBar.setRating(random.nextInt(1000) / 1000.0f);
		textView.setText(mPresenter.getData(groupPosition / 2).subList
				.get(childPosition));

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		if (groupPosition % 2 == 0)
			return 0;

		return mPresenter.getData(groupPosition / 2).subList.size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public int getGroupCount()
	{
		return 14;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return getCombinedGroupId(groupPosition);
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = View.inflate(parent.getContext(),
					R.layout.food_menu_group, null);
		}

		View indicator = convertView.findViewById(R.id.foodMenuIndocator);
		View group = convertView.findViewById(R.id.foodMenuGroup);

		if (groupPosition % 2 == 0)
		{
			indicator.setVisibility(View.VISIBLE);
			group.setVisibility(View.GONE);
		}
		else
		{
			indicator.setVisibility(View.GONE);
			group.setVisibility(View.VISIBLE);

			final TextView textView = (TextView) group
					.findViewById(R.id.foodGroupTextView);
			ImageView imageView = (ImageView) group
					.findViewById(R.id.foodGroupIndicator);

			if (isExpanded)
				imageView
						.setImageResource(R.drawable.expander_close_holo_light);
			else
				imageView.setImageResource(R.drawable.expander_open_holo_light);

			mPresenter.getData(groupPosition / 2, new DataReceiver<FoodMenu>()
			{
				@Override
				public void onReceived(int id, FoodMenu data)
				{
					textView.setText(data.name);
				}
			});
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