package kr.yudonguk.ui;

public interface AsyncIterator<Data>
{
	interface Handler
	{
		boolean isRunning();

		void cancel();
	}

	boolean hasNext();

	Handler next(DataReceiver<Data> receiver);
}
