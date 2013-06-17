package kr.yudonguk.ui;

public interface AsyncIterator<Data, Identifier>
{
	interface Handler
	{
		boolean isRunning();

		void cancel();
	}

	boolean hasNext();

	Handler next(DataReceiver<Data, Identifier> receiver);
}
