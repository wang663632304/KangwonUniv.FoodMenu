package kr.yudonguk.ui;

public interface UiPresenter<Data>
{
	void onModelUpdated(UpdateResult result);

	void getData(int id, DataReceiver<Data> receiver);

	void setData(int id, Data data);
}