package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import java.util.Iterator;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.ui.UiData;
import kr.yudonguk.ui.UiModel;
import kr.yudonguk.ui.UiPresenter;
import android.util.SparseArray;

public class DummyFoodMenuRatingModel implements UiModel<Food>
{
	private final SparseArray<Food> mFoodRatingMap = new SparseArray<Food>();

	@Override
	public boolean enable(UiPresenter<Food> presenter)
	{
		return true;
	}

	@Override
	public void disable()
	{
	}

	@Override
	public void update()
	{
	}

	@Override
	public Food getData(int id)
	{
		Food data = mFoodRatingMap.get(id);
		if (data == null)
			return null;
		return new Food(data.name, data.rate);
	}

	@Override
	public void setData(int id, Food data)
	{
		Food food = mFoodRatingMap.get(id);
		if (food == null)
			mFoodRatingMap.put(id, food = new Food(data.name));

		food.rate = data.rate;
	}

	@Override
	public Iterator<UiData<Food>> iterator()
	{
		return iterator(Integer.MIN_VALUE);
	}

	@Override
	public Iterator<UiData<Food>> iterator(int startId)
	{
		int index = 0;
		for (int i = 0; i < mFoodRatingMap.size(); i++)
		{
			index = i;
			if (startId <= mFoodRatingMap.keyAt(i))
				break;
		}
		final int startIndex = index;

		return new Iterator<UiData<Food>>()
		{
			int mIndex = Math.max(0, startIndex);

			@Override
			public void remove()
			{
			}

			@Override
			public UiData<Food> next()
			{
				if (!hasNext())
					return null;

				int id = mFoodRatingMap.keyAt(mIndex);
				Food data = mFoodRatingMap.valueAt(mIndex++);

				return new UiData<Food>(id, new Food(data.name, data.rate));
			}

			@Override
			public boolean hasNext()
			{
				return mIndex < mFoodRatingMap.size();
			}
		};
	}

	@Override
	public Iterator<UiData<Food>> reverseIterator()
	{
		return reverseIterator(Integer.MAX_VALUE);
	}

	@Override
	public Iterator<UiData<Food>> reverseIterator(int startId)
	{
		int index = 0;
		for (int i = mFoodRatingMap.size() - 1; i >= 0; i--)
		{
			index = i;
			if (mFoodRatingMap.keyAt(i) <= startId)
				break;
		}
		final int startIndex = index;

		return new Iterator<UiData<Food>>()
		{
			int mIndex = Math.min(mFoodRatingMap.size() - 1, startIndex);

			@Override
			public void remove()
			{
			}

			@Override
			public UiData<Food> next()
			{
				if (!hasNext())
					return null;

				int id = mFoodRatingMap.keyAt(mIndex);
				Food data = mFoodRatingMap.valueAt(mIndex--);

				return new UiData<Food>(id, new Food(data.name, data.rate));
			}

			@Override
			public boolean hasNext()
			{
				return mIndex >= 0;
			}
		};
	}

}
