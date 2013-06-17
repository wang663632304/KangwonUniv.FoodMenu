package kr.yudonguk.ui;

public class UiData<Data, Identifier>
{
	public final Identifier id;
	public final Data data;

	public UiData(Identifier id_, Data data_)
	{
		id = id_;
		data = data_;
	}
}
