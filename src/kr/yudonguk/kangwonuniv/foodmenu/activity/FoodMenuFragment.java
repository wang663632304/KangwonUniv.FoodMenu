package kr.yudonguk.kangwonuniv.foodmenu.activity;

import kr.yudonguk.kangwonuniv.foodmenu.AsyncDataReader;
import kr.yudonguk.kangwonuniv.foodmenu.AsyncIteratorImpl;
import kr.yudonguk.kangwonuniv.foodmenu.R;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.BaekRokFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.BtlFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.CheonJiFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.DormitoryFirstFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.DormitoryThirdFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.DummyFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.FoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.model.TaeBaekFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuView;
import kr.yudonguk.ui.AsyncIterator;
import kr.yudonguk.ui.DataReceiver;
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
		FoodMenuPresenter
{
	public static final String ARG_RESTAURANT_NAME = "restaurant_name";

	FoodMenuView mUiView;
	FoodMenuModel mUiModel;
	AsyncDataReader<FoodMenu> mAsyncDataReader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mUiView = new FoodMenuView(this);

		Bundle bundle = getArguments();
		if (bundle != null)
		{
			String restaurantName = bundle.getString(ARG_RESTAURANT_NAME);

			final String[] restaurantList = getResources().getStringArray(
					R.array.restaurant_list);
			int index = 0;
			for (String restaurant : restaurantList)
			{
				if (restaurant.equals(restaurantName))
					break;
				index++;
			}

			switch (index)
			{
				case 0:
					mUiModel = new BaekRokFoodMenuModel(this);
					break;
				case 1:
					mUiModel = new CheonJiFoodMenuModel(this);
					break;
				case 2:
					mUiModel = new TaeBaekFoodMenuModel(this);
					break;
				case 3:
					mUiModel = new DormitoryFirstFoodMenuModel(this);
					break;
				case 4:
					mUiModel = new DormitoryThirdFoodMenuModel(this);
					break;
				case 5:
					mUiModel = new BtlFoodMenuModel(this);
					break;
			}
		}

		if (mUiModel == null)
		{
			mUiModel = new DummyFoodMenuModel(this);
		}

		mAsyncDataReader = new AsyncDataReader<FoodMenu>(mUiModel);

		setHasOptionsMenu(true);

		mUiModel.enable();
		View view = mUiView.onEnabled(inflater);
		if (savedInstanceState != null)
			mUiView.restoreState(savedInstanceState);

		return view;
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState)
	{
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null)
			mUiView.restoreState(savedInstanceState);
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
	public void getData(final int id, final DataReceiver<FoodMenu> receiver)
	{
		mAsyncDataReader.execute(id, receiver);
	}

	@Override
	public void cancelGetting(int id)
	{
		mAsyncDataReader.cancel(id);
	}

	@Override
	public void setData(int id, FoodMenu data)
	{
		mUiModel.setData(id, data);
	}

	@Override
	public Context getContext()
	{
		return getActivity();
	}

	@Override
	public AsyncIterator<FoodMenu> iterator()
	{
		return new AsyncIteratorImpl<FoodMenu>(mUiModel.iterator());
	}

	@Override
	public AsyncIterator<FoodMenu> reverseIterator()
	{
		return new AsyncIteratorImpl<FoodMenu>(mUiModel.reverseIterator());
	}
}