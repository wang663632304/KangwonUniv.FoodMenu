package kr.yudonguk.ui;

import android.content.Context;

public interface UiPresenter<Data>
{
	Context getContext();

	void onModelUpdated(UpdateResult result);

	void getData(int id, DataReceiver<Data> receiver);

	AsyncIterator<Data> iterator();

	AsyncIterator<Data> iterator(int startId);

	AsyncIterator<Data> reverseIterator();

	AsyncIterator<Data> reverseIterator(int startId);

	void cancelGetting(int id);

	void setData(int id, Data data);
}