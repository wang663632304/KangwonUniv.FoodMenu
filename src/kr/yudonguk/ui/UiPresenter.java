package kr.yudonguk.ui;

import android.content.Context;

public interface UiPresenter<Data>
{
	Context getContext();

	void onModelUpdated(UpdateResult result);

	void getData(int id, DataReceiver<Data> receiver);

	AsyncIterator<Data> iterator();

	AsyncIterator<Data> reverseIterator();

	void cancelGetting(int id);

	void setData(int id, Data data);
}