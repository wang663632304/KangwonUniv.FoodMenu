package kr.yudonguk.ui;

public class UiData<T>
{
	public int id;
	public T data;

	public UiData(int id_, T data_)
	{
		id = id_;
		data = data_;
	}
}
