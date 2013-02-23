package kr.yudonguk.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<E> implements Iterator<E>
{
	private final E[] mArray;
	private int mCursor;
	private final boolean mNullSkip;

	public ArrayIterator(E[] array)
	{
		this(array, false);
	}

	public ArrayIterator(E[] array, boolean nullSkip)
	{
		mArray = array;
		mCursor = -1;
		mNullSkip = nullSkip;
	}

	@Override
	public boolean hasNext()
	{
		if (!mNullSkip)
		{
			return (mCursor + 1) < mArray.length;
		}
		else
		{
			for (int cursor = mCursor + 1; cursor < mArray.length; cursor++)
			{
				if (mArray[cursor] != null)
					return true;
			}
			return false;
		}
	}

	@Override
	public E next()
	{
		if (!mNullSkip)
		{
			if ((mCursor + 1) < mArray.length)
				return mArray[++mCursor];
			throw new NoSuchElementException();
		}
		else
		{
			for (mCursor = mCursor + 1; mCursor < mArray.length; mCursor++)
			{
				if (mArray[mCursor] != null)
					return mArray[mCursor];
			}
			throw new NoSuchElementException();
		}
	}

	@Override
	public void remove()
	{
		if (mCursor < 0)
			throw new IllegalStateException();
		mArray[mCursor] = null;
	}
}
