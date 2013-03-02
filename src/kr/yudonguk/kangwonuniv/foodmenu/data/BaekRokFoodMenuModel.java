package kr.yudonguk.kangwonuniv.foodmenu.data;

import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;

public class BaekRokFoodMenuModel extends KnuCoopFoodMenuModel
{
	final FoodMenuPresenter mPresenter;

	public BaekRokFoodMenuModel(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
	}

	@Override
	public void update()
	{
	}

	@Override
	public void setData(int id, FoodMenu data)
	{
	}

	@Override
	public String getKnuCoopUrl()
	{
		return "http://knucoop.kangwon.ac.kr/weekly_menu_02.asp";
	}
}
