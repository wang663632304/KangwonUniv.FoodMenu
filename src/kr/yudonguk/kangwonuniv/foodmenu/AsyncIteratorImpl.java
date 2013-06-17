package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.Iterator;

import kr.yudonguk.ui.AsyncIterator;
import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiData;
import android.os.AsyncTask;

public class AsyncIteratorImpl<Data, Identifier> implements
		AsyncIterator<Data, Identifier>
{
	private Iterator<UiData<Data, Identifier>> mIterator;

	public AsyncIteratorImpl(Iterator<UiData<Data, Identifier>> iterator)
	{
		mIterator = iterator;
	}

	@Override
	public boolean hasNext()
	{
		return mIterator.hasNext();
	}

	@Override
	public AsyncIterator.Handler next(
			final DataReceiver<Data, Identifier> receiver)
	{
		if (!mIterator.hasNext())
			return null;

		final AsyncTask<Void, Float, UiData<Data, Identifier>> task = new AsyncTask<Void, Float, UiData<Data, Identifier>>()
		{
			@Override
			protected UiData<Data, Identifier> doInBackground(Void... params)
			{
				return mIterator.next();
			}

			protected void onPostExecute(UiData<Data, Identifier> result)
			{
				receiver.onReceived(result.id, result.data);
			}

			protected void onCancelled(UiData<Data, Identifier> result)
			{
				// 현재는 id를 알수 없어서 null 처리하지만
				// 이런 방법은 문제를 야기할 것이다.
				receiver.onReceived(null, null);
			}

			@Override
			protected void onProgressUpdate(Float... values)
			{
				// 현재는 id를 알수 없어서 null 처리하지만
				// 이런 방법은 문제를 야기할 것이다.
				receiver.onProgressed(null, values[0]);
			}
		};
		task.execute();

		return new AsyncIterator.Handler()
		{
			@Override
			public void cancel()
			{
				task.cancel(true);
			}

			@Override
			public boolean isRunning()
			{
				return task.getStatus() != AsyncTask.Status.FINISHED;
			}
		};
	}
}