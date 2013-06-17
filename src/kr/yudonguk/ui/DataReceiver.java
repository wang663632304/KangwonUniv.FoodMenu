package kr.yudonguk.ui;

public abstract class DataReceiver<Data, Identifier>
{
	public abstract void onReceived(Identifier id, Data data);

	public void onProgressed(Identifier id, float progress)
	{
	}
}
