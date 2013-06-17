package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.FoodGroup;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Section;
import kr.yudonguk.ui.UiData;
import kr.yudonguk.ui.UiModel;
import kr.yudonguk.ui.UiPresenter;
import kr.yudonguk.ui.UpdateResult;

public class DummyFoodMenuModel implements UiModel<FoodMenu, Integer>
{
	UiPresenter<FoodMenu, Integer> mPresenter;

	@Override
	public boolean enable(UiPresenter<FoodMenu, Integer> presenter)
	{
		mPresenter = presenter;
		return true;
	}

	@Override
	public void disable()
	{
	}

	@Override
	public void update()
	{
		mPresenter.onModelUpdated(new UpdateResult());
	}

	@Override
	public FoodMenu getData(Integer id)
	{
		FoodMenu data = new FoodMenu();

		Random random = new Random(new Date().getTime());
		for (int i = 0, max = random.nextInt(10) + 1; i < max; i++)
		{
			Section section = new Section();
			section.name = "아침 : " + id;
			section.setStartTime(11, 00);
			section.setEndTime(13, 00);

			for (int j = 0, jMax = random.nextInt(10) + 1; j < jMax; j++)
			{
				if (random.nextBoolean())
				{
					FoodGroup foodGroup = new FoodGroup();
					foodGroup.name = "Group : " + j;

					for (int itemIndex = 0, size = random.nextInt(10) + 1; itemIndex < size; itemIndex++)
					{
						Food food = new Food();
						food.name = "Food : " + itemIndex;
						food.rate = random.nextInt(1001) / 1000.0f;
						foodGroup.add(food);
					}

					section.add(foodGroup);
				}
				else
				{
					Food food = new Food();
					food.name = "Food : " + j;
					food.rate = random.nextInt(1001) / 1000.0f;
					section.add(food);
				}
			}

			data.add(section);
		}

		try
		{
			Thread.sleep(random.nextInt(1000) + 200);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	@Override
	public void setData(Integer id, FoodMenu data)
	{
	}

	@Override
	public Iterator<UiData<FoodMenu, Integer>> reverseIterator()
	{
		return reverseIterator(Integer.MAX_VALUE);
	}

	@Override
	public Iterator<UiData<FoodMenu, Integer>> reverseIterator(
			final Integer startId)
	{
		return new Iterator<UiData<FoodMenu, Integer>>()
		{
			int mIndex = startId;

			@Override
			public void remove()
			{
			}

			@Override
			public UiData<FoodMenu, Integer> next()
			{
				return new UiData<FoodMenu, Integer>(mIndex, getData(mIndex++));
			}

			@Override
			public boolean hasNext()
			{
				return true;
			}
		};
	}

	@Override
	public Iterator<UiData<FoodMenu, Integer>> iterator()
	{
		return iterator(Integer.MIN_VALUE);
	}

	@Override
	public Iterator<UiData<FoodMenu, Integer>> iterator(final Integer startId)
	{
		return new Iterator<UiData<FoodMenu, Integer>>()
		{
			int mIndex = startId;

			@Override
			public void remove()
			{
			}

			@Override
			public UiData<FoodMenu, Integer> next()
			{
				return new UiData<FoodMenu, Integer>(mIndex, getData(mIndex--));
			}

			@Override
			public boolean hasNext()
			{
				return true;
			}
		};
	}
}
