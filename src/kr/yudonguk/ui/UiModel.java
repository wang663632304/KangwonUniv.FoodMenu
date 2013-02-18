package kr.yudonguk.ui;

public interface UiModel<Data>
{
	void update();

	Data getData(int id);

	void setData(int id, Data data);
}
