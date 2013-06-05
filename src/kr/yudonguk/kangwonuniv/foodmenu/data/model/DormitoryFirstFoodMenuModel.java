package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;

public class DormitoryFirstFoodMenuModel extends KnuDormitoryFoodMenuModel
{
	@Override
	public void update()
	{}

	@Override
	public void setData(int id, FoodMenu data)
	{}

	@Override
	public String getKnuDormitoryUrl()
	{
		return KnuDormitoryFoodMenuParser.FIRST_RESTAURANT_URL;
	}
}
