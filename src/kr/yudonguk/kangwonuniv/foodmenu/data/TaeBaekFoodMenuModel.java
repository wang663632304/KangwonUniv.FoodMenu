package kr.yudonguk.kangwonuniv.foodmenu.data;

import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuCoopFoodMenuParser;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;

public class TaeBaekFoodMenuModel extends KnuCoopFoodMenuModel
{
	final FoodMenuPresenter mPresenter;

	public TaeBaekFoodMenuModel(FoodMenuPresenter presenter)
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
		return KnuCoopFoodMenuParser.TAE_BAEK_URL;
	}
}
