package kr.yudonguk.ui;

public interface AsyncIterator<Data>
{
	boolean hasNext();

	void next(DataReceiver<Data> receiver);
}
