package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;

public class DormitoryThirdFoodMenuModel extends KnuDormitoryFoodMenuModel
{
	final FoodMenuPresenter mPresenter;

	public DormitoryThirdFoodMenuModel(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
	}

	@Override
	public void update()
	{}

	@Override
	public void setData(int id, FoodMenu data)
	{}

	@Override
	public String getKnuDormitoryUrl()
	{
		return KnuDormitoryFoodMenuParser.THIRD_RESTAURANT_URL;
	}
}
