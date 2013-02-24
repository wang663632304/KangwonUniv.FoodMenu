package kr.yudonguk.kangwonuniv.foodmenu.data.parser;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.htmlcleaner.TagNode;

public class Table implements Iterable<List<String>>
{
	private final List<List<String>> mTable;

	public Table(TagNode table)
	{
		mTable = new ArrayList<List<String>>();

		if (!table.getName().equals("table"))
			return;

		TagNode tbody = table.findElementByName("tbody", false);
		if (tbody == null)
			return;

		TagNode[] tableRows = tbody.getChildTags();
		for (int rowIndex = 0; rowIndex < tableRows.length; rowIndex++)
		{
			TagNode tableRow = tableRows[rowIndex];
			if (!tableRow.getName().equals("tr"))
				continue;

			int columnIndex = 0;
			for (TagNode tableCell : tableRow.getChildTags())
			{
				if (!tableCell.getName().equals("td"))
					continue;

				for (; getCell(rowIndex, columnIndex) != null; columnIndex++)
				{
				}

				String value = tableCell.getText().toString().trim();
				String colspan = tableCell.getAttributeByName("colspan");
				String rowspan = tableCell.getAttributeByName("rowspan");

				setCell(value, rowIndex,
						rowspan == null ? 1 : Integer.parseInt(rowspan),
						columnIndex,
						colspan == null ? 1 : Integer.parseInt(colspan));
			}
		}

		nomalizeColumn();
	}

	public String get(int row, int column) throws InvalidParameterException
	{
		checkValid(row, column);
		return mTable.get(row).get(column);
	}

	public void set(String input, int row, int column)
			throws InvalidParameterException
	{
		checkValid(row, column);
		mTable.get(row).set(column, input);
	}

	public int getColumnCount()
	{
		if (mTable.size() == 0)
			return 0;
		return mTable.get(0).size();
	}

	public int getRowCount()
	{
		return mTable.size();
	}

	@Override
	public Iterator<List<String>> iterator()
	{
		return mTable.iterator();
	}

	private void checkValid(int row, int column)
			throws InvalidParameterException
	{
		int rowCount = getRowCount();
		int columnCount = getColumnCount();

		if (row < 0 || row >= rowCount)
			throw new InvalidParameterException("row=" + row
					+ ", row must be 0~" + (rowCount - 1));
		if (column < 0 || column >= columnCount)
			throw new InvalidParameterException("column=" + column
					+ ", column must be 0~" + (columnCount - 1));
	}

	private void nomalizeColumn()
	{
		int maxColumnCount = 0;
		for (List<String> row : mTable)
		{
			maxColumnCount = Math.max(maxColumnCount, row.size());
		}

		for (List<String> row : mTable)
		{
			int delta = maxColumnCount - row.size();
			for (; delta > 0; delta--)
			{
				row.add(null);
			}
		}
	}

	private List<String> getRow(int row)
	{
		if (row >= mTable.size())
		{
			int delta = row - mTable.size();
			for (; delta >= 0; delta--)
				mTable.add(new ArrayList<String>());
		}
		return mTable.get(row);
	}

	private String getCell(int row, int column)
	{
		if (mTable.size() <= row)
			return null;
		if (mTable.get(row).size() <= column)
			return null;

		return mTable.get(row).get(column);
	}

	private void setCell(String input, int rowIndex, int columnIndex)
	{
		List<String> row = getRow(rowIndex);
		if (columnIndex >= row.size())
		{
			int delta = columnIndex - row.size();
			for (; delta >= 0; delta--)
				row.add(null);
		}
		row.set(columnIndex, input);
	}

	private void setCell(String input, int rowIndex, int rowSize,
			int columnIndex, int columnSize)
	{
		for (int row = rowIndex, rowEnd = rowIndex + rowSize; row < rowEnd; row++)
		{
			for (int column = columnIndex, columnEnd = columnIndex + columnSize; column < columnEnd; column++)
			{
				setCell(input, row, column);
			}
		}
	}
}
