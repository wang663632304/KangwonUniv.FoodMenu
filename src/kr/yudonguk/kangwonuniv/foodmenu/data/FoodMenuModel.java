package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.util.Date;
import java.util.Random;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.FoodGroup;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Section;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;
import kr.yudonguk.ui.UiModel;
import kr.yudonguk.ui.UpdateResult;

public class FoodMenuModel implements UiModel<FoodMenu>
{
	FoodMenuPresenter mPresenter;

	public FoodMenuModel(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
	}

	@Override
	public void update()
	{
		mPresenter.onModelUpdated(new UpdateResult());
	}

	@Override
	public FoodMenu getData(int id)
	{
		FoodMenu data = new FoodMenu();

		Random random = new Random(new Date().getTime());
		for (int i = 0, max = random.nextInt(10) + 1; i < max; i++)
		{
			Section section = new Section();
			section.name = "아침";
			section.startTime = Section.createTime(11, 00);
			section.endTime = Section.createTime(13, 00);

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

		return data;
	}

	@Override
	public void setData(int id, FoodMenu data)
	{}

}
