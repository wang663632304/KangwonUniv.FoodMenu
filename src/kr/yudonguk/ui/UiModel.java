package kr.yudonguk.ui;

import java.util.Iterator;

public interface UiModel<Data> extends Iterable<UiData<Data>>
{
	void update();

	Data getData(int id);

	void setData(int id, Data data);

	Iterator<UiData<Data>> reverseIterator();
}
