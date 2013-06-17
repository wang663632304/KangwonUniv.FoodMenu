package kr.yudonguk.ui;

import android.content.Context;

public interface UiPresenter<Data, Identifier>
{
	Context getContext();

	void onModelUpdated(UpdateResult result);

	void getData(Identifier id, DataReceiver<Data, Identifier> receiver);

	AsyncIterator<Data, Identifier> iterator();

	AsyncIterator<Data, Identifier> iterator(Identifier startId);

	AsyncIterator<Data, Identifier> reverseIterator();

	AsyncIterator<Data, Identifier> reverseIterator(Identifier startId);

	void cancelGetting(Identifier id);

	void setData(Identifier id, Data data);
}