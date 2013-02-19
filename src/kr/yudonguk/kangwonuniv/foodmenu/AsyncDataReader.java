package kr.yudonguk.kangwonuniv.foodmenu;

import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiModel;
import android.os.AsyncTask;

public class AsyncDataReader<Data> extends AsyncTask<Void, Float, Data>
{
	private UiModel<Data> mModel;
	private DataReceiver<Data> mDataReceiver;
	private int mId;

	public AsyncDataReader(UiModel<Data> model)
	{
		mModel = model;
	}

	private AsyncDataReader(UiModel<Data> model, int id,
			DataReceiver<Data> receiver)
	{
		mModel = model;
		mId = id;
		mDataReceiver = receiver;
	}

	public void execute(int id, DataReceiver<Data> receiver)
	{
		new AsyncDataReader<Data>(mModel, id, receiver).execute();
	}

	@Override
	protected Data doInBackground(Void... params)
	{
		return mModel.getData(mId);
	}

	@Override
	protected void onProgressUpdate(Float... values)
	{
		mDataReceiver.onProgressed(mId, values[0]);
	}

	@Override
	protected void onCancelled()
	{
		mDataReceiver.onReceived(mId, null);
	}

	@Override
	protected void onPostExecute(Data result)
	{
		mDataReceiver.onReceived(mId, result);
	}

}
