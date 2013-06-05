package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.ui.UiPresenter;

public class CheonJiFoodMenuModel extends KnuCoopFoodMenuModel
{
	final UiPresenter<FoodMenu> mPresenter;

	public CheonJiFoodMenuModel(UiPresenter<FoodMenu> presenter)
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
		return "http://knucoop.kangwon.ac.kr/weekly_menu_01.asp";
	}
}
