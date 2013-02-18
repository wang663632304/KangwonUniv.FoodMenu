package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.util.ArrayList;

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
		data.name = "음식 : " + id;
		data.subList = new ArrayList<String>();

		for (int i = 0, max = 4; i < max; i++)
		{
			data.subList.add("음식 종류 : " + i);
		}

		return data;
	}

	@Override
	public void setData(int id, FoodMenu data)
	{
	}

}
