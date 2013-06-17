package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;

public class BaekRokFoodMenuModel extends KnuCoopFoodMenuModel
{
	@Override
	public void update()
	{
	}

	@Override
	public void setData(Integer id, FoodMenu data)
	{
	}

	@Override
	public String getKnuCoopUrl()
	{
		return "http://knucoop.kangwon.ac.kr/weekly_menu_02.asp";
	}
}
