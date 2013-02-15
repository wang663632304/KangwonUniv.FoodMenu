package kr.yudonguk.kangwonuniv.foodmenu.presenter;

public interface UiPresenter<Data>
{
	void onUpdated(UpdateResult result);

	Data getData(int id);

	void setData(Data data, int id);
}
