package kr.yudonguk.kangwonuniv.foodmenu.activity;

import kr.yudonguk.kangwonuniv.foodmenu.AsyncDataReader;
import kr.yudonguk.kangwonuniv.foodmenu.AsyncIteratorImpl;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.BaekRokFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.DummyFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuView;
import kr.yudonguk.ui.AsyncIterator;
import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UiModel;
import kr.yudonguk.ui.UiPresenter;
import kr.yudonguk.ui.UpdateResult;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FoodMenuFragment extends SherlockFragment implements
		UiPresenter<FoodMenu, Integer>
{
	public static final String ARG_RESTAURANT_NAME = "restaurant_name";

	FoodMenuView mUiView;
	UiModel<FoodMenu, Integer> mUiModel;
	AsyncDataReader<FoodMenu, Integer> mAsyncDataReader;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mUiView = new FoodMenuView();

		String modelClassName = null;

		Bundle bundle = getArguments();
		if (bundle != null)
		{
			modelClassName = bundle.getString(ARG_RESTAURANT_NAME);
		}

		modelClassName = modelClassName != null ? modelClassName
				: BaekRokFoodMenuModel.class.getName();

		try
		{
			mUiModel = (UiModel<FoodMenu, Integer>) Class.forName(
					modelClassName).newInstance();
		} catch (Exception e)
		{
			e.printStackTrace();
			mUiModel = new DummyFoodMenuModel();
		}

		mAsyncDataReader = new AsyncDataReader<FoodMenu, Integer>(mUiModel);

		setHasOptionsMenu(true);

		mUiModel.enable(this);
		View view = mUiView.onEnabled(inflater, this);
		if (savedInstanceState != null)
			mUiView.restoreState(savedInstanceState);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mUiView.saveState(outState);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mUiView.onDisabled();
		mUiModel.disable();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		mUiView.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mUiView.onOptionsItemSelected(item) ? true : super
				.onOptionsItemSelected(item);
	}

	@Override
	public void onModelUpdated(final UpdateResult result)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if (result.isSuccess)
					mUiView.update();
				else
					mUiView.onError(result);
			}
		});
	}

	@Override
	public void getData(final Integer id,
			final DataReceiver<FoodMenu, Integer> receiver)
	{
		mAsyncDataReader.execute(id, receiver);
	}

	@Override
	public void cancelGetting(Integer id)
	{
		mAsyncDataReader.cancel(id);
	}

	@Override
	public void setData(Integer id, FoodMenu data)
	{
		mUiModel.setData(id, data);
	}

	@Override
	public Context getContext()
	{
		return getActivity();
	}

	@Override
	public AsyncIterator<FoodMenu, Integer> iterator()
	{
		return new AsyncIteratorImpl<FoodMenu, Integer>(mUiModel.iterator());
	}

	@Override
	public AsyncIterator<FoodMenu, Integer> reverseIterator()
	{
		return new AsyncIteratorImpl<FoodMenu, Integer>(
				mUiModel.reverseIterator());
	}

	@Override
	public AsyncIterator<FoodMenu, Integer> iterator(Integer startId)
	{
		return new AsyncIteratorImpl<FoodMenu, Integer>(
				mUiModel.iterator(startId));
	}

	@Override
	public AsyncIterator<FoodMenu, Integer> reverseIterator(Integer startId)
	{
		return new AsyncIteratorImpl<FoodMenu, Integer>(
				mUiModel.reverseIterator(startId));
	}
}
