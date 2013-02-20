package kr.yudonguk.kangwonuniv.foodmenu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiModel;
import android.os.AsyncTask;

public class AsyncDataReader<Data>
{
	private UiModel<Data> mModel;
	private List<DataReaderAsyncTask<Data>> mTaskList;

	public AsyncDataReader(UiModel<Data> model)
	{
		mModel = model;
		mTaskList = new LinkedList<DataReaderAsyncTask<Data>>();
	}

	public void execute(int id, DataReceiver<Data> receiver)
	{
		DataReaderAsyncTask<Data> task = new DataReaderAsyncTask<Data>(mModel,
				id, receiver);
		task.execute();
		mTaskList.add(task);
	}

	public void cancel(int id)
	{
		synchronized (mTaskList)
		{
			for (Iterator<DataReaderAsyncTask<Data>> itor = mTaskList
					.iterator(); itor.hasNext();)
			{
				DataReaderAsyncTask<Data> futureTask = itor.next();
				if (futureTask.getId() == id)
				{
					itor.remove();
					futureTask.cancel(true);
					break;
				}
			}
		}
	}
}

class DataReaderAsyncTask<Data> extends AsyncTask<Void, Float, Data>
{
	private final UiModel<Data> mModel;
	private final int mId;
	private final DataReceiver<Data> mDataReceiver;

	public DataReaderAsyncTask(UiModel<Data> model, int id,
			DataReceiver<Data> receiver)
	{
		mModel = model;
		mId = id;
		mDataReceiver = receiver;
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
	}

	@Override
	protected void onCancelled(Data result)
	{
		mDataReceiver.onReceived(mId, null);
	}

	@Override
	protected void onProgressUpdate(Float... values)
	{
		mDataReceiver.onProgressed(mId, values[0]);
	}
}