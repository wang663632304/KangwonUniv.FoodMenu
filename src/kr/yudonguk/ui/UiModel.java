package kr.yudonguk.ui;

import java.util.Iterator;

public interface UiModel<Data> extends Iterable<UiData<Data>>
{
	boolean enable();

	void disable();

	void update();

	Data getData(int id);

	void setData(int id, Data data);

	Iterator<UiData<Data>> iterator(int startId);

	Iterator<UiData<Data>> reverseIterator();

	Iterator<UiData<Data>> reverseIterator(int startId);
}
