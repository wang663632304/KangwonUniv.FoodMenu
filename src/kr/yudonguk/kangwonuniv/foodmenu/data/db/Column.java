package kr.yudonguk.kangwonuniv.foodmenu.data.db;

public class Column
{
	public final String name;
	public final int index;

	public Column(String name_, int index_)
	{
		name = name_;
		index = index_;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
