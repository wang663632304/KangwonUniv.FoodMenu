package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.LinkedList;
import java.util.Queue;

import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiModel;
import android.os.AsyncTask;
import android.util.SparseArray;

public class AsyncDataReader<Data, Identifier> implements
		DataReadCompleteListener<Data, Identifier>
{
	private UiModel<Data, Identifier> mModel;
	private SparseArray<Queue<DataReaderAsyncTask<Data, Identifier>>> mTaskQueueMap;

	public AsyncDataReader(UiModel<Data, Identifier> model)
	{
		mModel = model;
		mTaskQueueMap = new SparseArray<Queue<DataReaderAsyncTask<Data, Identifier>>>();
	}

	public void execute(Identifier id, DataReceiver<Data, Identifier> receiver)
	{
		DataReaderAsyncTask<Data, Identifier> task = new DataReaderAsyncTask<Data, Identifier>(
				mModel, id, receiver, this);
		task.execute();

		synchronized (mTaskQueueMap)
		{
			Queue<DataReaderAsyncTask<Data, Identifier>> taskQueue = mTaskQueueMap
					.get(id.hashCode());

			if (taskQueue == null)
			{
				taskQueue = new LinkedList<DataReaderAsyncTask<Data, Identifier>>();
				mTaskQueueMap.put(id.hashCode(), taskQueue);
			}

			taskQueue.add(task);
		}
	}

	public void cancel(Identifier id)
	{
		synchronized (mTaskQueueMap)
		{
			Queue<DataReaderAsyncTask<Data, Identifier>> taskQueue = mTaskQueueMap
					.get(id.hashCode());

			if (taskQueue == null)
				return;

			DataReaderAsyncTask<Data, Identifier> task = taskQueue.poll();

			if (task != null)
				task.cancel(true);

			if (taskQueue.isEmpty())
				mTaskQueueMap.remove(id.hashCode());
		}
	}

	@Override
	public void onCompleted(DataReaderAsyncTask<Data, Identifier> task)
	{
		synchronized (mTaskQueueMap)
		{
			Identifier id = task.getId();
			Queue<DataReaderAsyncTask<Data, Identifier>> taskQueue = mTaskQueueMap
					.get(id.hashCode());

			if (taskQueue == null)
				return;

			taskQueue.remove(task);

			if (taskQueue.isEmpty())
				mTaskQueueMap.remove(id.hashCode());
		}
	}
}

class DataReaderAsyncTask<Data, Identifier> extends
		AsyncTask<Void, Float, Data>
{
	private final UiModel<Data, Identifier> mModel;
	private final Identifier mId;
	private final DataReceiver<Data, Identifier> mDataReceiver;
	private final DataReadCompleteListener<Data, Identifier> mCompleteListener;

	public DataReaderAsyncTask(UiModel<Data, Identifier> model, Identifier id,
			DataReceiver<Data, Identifier> receiver,
			DataReadCompleteListener<Data, Identifier> completeListener)
	{
		mModel = model;
		mId = id;
		mDataReceiver = receiver;
		mCompleteListener = completeListener;
	}

	public Identifier getId()
	{
		return mId;
	}

	@Override
	protected Data doInBackground(Void... params)
	{
		return mModel.getData(mId);
	}

	@Override
	protected void onPostExecute(Data result)
	{
		mDataReceiver.onReceived(mId, result);
		mCompleteListener.onCompleted(this);
	}

	@Override
	protected void onCancelled(Data result)
	{
		mDataReceiver.onReceived(mId, null);
		mCompleteListener.onCompleted(this);
	}

	@Override
	protected void onProgressUpdate(Float... values)
	{
		mDataReceiver.onProgressed(mId, values[0]);
	}
}

interface DataReadCompleteListener<Data, Identifier>
{
	void onCompleted(DataReaderAsyncTask<Data, Identifier> task);
}