package kr.yudonguk.kangwonuniv.foodmenu.presenter;

public interface DataReceiver<Data>
{
	void onReceived(int id, Data data);
}
