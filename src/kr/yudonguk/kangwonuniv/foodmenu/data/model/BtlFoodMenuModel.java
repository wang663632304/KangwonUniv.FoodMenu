package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;
import kr.yudonguk.ui.UiPresenter;

public class BtlFoodMenuModel extends KnuDormitoryFoodMenuModel
{
	final UiPresenter<FoodMenu> mPresenter;

	public BtlFoodMenuModel(UiPresenter<FoodMenu> presenter)
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
		return KnuDormitoryFoodMenuParser.BTL_URL;
	}
}
