package kr.yudonguk.ui;

public abstract class DataReceiver<Data>
{
	public abstract void onReceived(int id, Data data);

	public void onProgressed(int id, float progress)
	{}
}
