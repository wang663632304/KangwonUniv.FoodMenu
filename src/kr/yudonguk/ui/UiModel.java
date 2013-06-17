package kr.yudonguk.ui;

import java.util.Iterator;

public interface UiModel<Data, Identifier> extends
		Iterable<UiData<Data, Identifier>>
{
	boolean enable(UiPresenter<Data, Identifier> presenter);

	void disable();

	void update();

	Data getData(Identifier id);

	void setData(Identifier id, Data data);

	Iterator<UiData<Data, Identifier>> iterator(Identifier startId);

	Iterator<UiData<Data, Identifier>> reverseIterator();

	Iterator<UiData<Data, Identifier>> reverseIterator(Identifier startId);
}
