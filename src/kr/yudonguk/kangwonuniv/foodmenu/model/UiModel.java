package kr.yudonguk.kangwonuniv.foodmenu.model;

public interface UiModel<Data>
{
	void update();

	Data getData(int id);

	void setData(Data data, int id);
}
