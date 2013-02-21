package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.LinkedList;
import java.util.Queue;

import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiModel;
import android.os.AsyncTask;
import android.util.SparseArray;

public class AsyncDataReader<Data> implements DataReadCompleteListener<Data>
{
	private UiModel<Data> mModel;
	private SparseArray<Queue<DataReaderAsyncTask<Data>>> mTaskQueueMap;

	public AsyncDataReader(UiModel<Data> model)
	{
		mModel = model;
		mTaskQueueMap = new SparseArray<Queue<DataReaderAsyncTask<Data>>>();
	}

	public void execute(int id, DataReceiver<Data> receiver)
	{
		DataReaderAsyncTask<Data> task = new DataReaderAsyncTask<Data>(mModel,
				id, receiver, this);
		task.execute();

		synchronized (mTaskQueueMap)
		{
			Queue<DataReaderAsyncTask<Data>> taskQueue = mTaskQueueMap.get(id);

			if (taskQueue == null)
			{
				taskQueue = new LinkedList<DataReaderAsyncTask<Data>>();
				mTaskQueueMap.put(id, taskQueue);
			}

			taskQueue.add(task);
		}
	}

	public void cancel(int id)
	{
		synchronized (mTaskQueueMap)
		{
			Queue<DataReaderAsyncTask<Data>> taskQueue = mTaskQueueMap.get(id);

			if (taskQueue == null)
				return;

			DataReaderAsyncTask<Data> task = taskQueue.poll();

			if (task != null)
				task.cancel(true);

			if (taskQueue.isEmpty())
				mTaskQueueMap.remove(id);
		}
	}

	@Override
	public void onCompleted(DataReaderAsyncTask<Data> task)
	{
		synchronized (mTaskQueueMap)
		{
			int id = task.getId();
			Queue<DataReaderAsyncTask<Data>> taskQueue = mTaskQueueMap.get(id);

			if (taskQueue == null)
				return;

			taskQueue.remove(task);

			if (taskQueue.isEmpty())
				mTaskQueueMap.remove(id);
		}
	}
}

class DataReaderAsyncTask<Data> extends AsyncTask<Void, Float, Data>
{
	private final UiModel<Data> mModel;
	private final int mId;
	private final DataReceiver<Data> mDataReceiver;
	private final DataReadCompleteListener<Data> mCompleteListener;

	public DataReaderAsyncTask(UiModel<Data> model, int id,
			DataReceiver<Data> receiver,
			DataReadCompleteListener<Data> completeListener)
	{
		mModel = model;
		mId = id;
		mDataReceiver = receiver;
		mCompleteListener = completeListener;
	}

	public int getId()
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

interface DataReadCompleteListener<Data>
{
	void onCompleted(DataReaderAsyncTask<Data> task);
}