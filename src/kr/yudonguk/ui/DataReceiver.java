package kr.yudonguk.ui;

public interface DataReceiver<Data>
{
	void onReceived(int id, Data data);
}
