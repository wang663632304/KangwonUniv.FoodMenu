package kr.yudonguk.kangwonuniv.foodmenu.data.parser;

import java.io.IOException;
import java.net.URL;

import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu;

public interface FoodMenuParser
{
	WeekFoodMenu parse(URL url) throws IOException;

	WeekFoodMenu parse(String html);
}
