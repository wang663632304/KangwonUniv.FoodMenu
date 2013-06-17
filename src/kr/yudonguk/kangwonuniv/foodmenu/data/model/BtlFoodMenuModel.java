package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;

public class BtlFoodMenuModel extends KnuDormitoryFoodMenuModel
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
	public String getKnuDormitoryUrl()
	{
		return KnuDormitoryFoodMenuParser.BTL_URL;
	}
}
