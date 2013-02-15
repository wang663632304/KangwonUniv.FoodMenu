package kr.yudonguk.kangwonuniv.foodmenu.activity;

import kr.yudonguk.kangwonuniv.foodmenu.model.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.model.FoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.presenter.FoodMenuPresenter;
import kr.yudonguk.kangwonuniv.foodmenu.presenter.UpdateResult;
import kr.yudonguk.kangwonuniv.foodmenu.view.FoodMenuView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FoodMenuFragment extends Fragment implements FoodMenuPresenter
{
	public static final String ARG_RESTAURANT_NAME = "restaurant_name";

	FoodMenuView mUiView;
	FoodMenuModel mUiModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mUiView = new FoodMenuView(this);
		mUiModel = new FoodMenuModel(this);

		setHasOptionsMenu(true);

		return mUiView.onLoaded(inflater, savedInstanceState);
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
	public void onUpdated(final UpdateResult result)
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
	public FoodMenu getData(int id)
	{
		return mUiModel.getData(id);
	}

	@Override
	public void setData(FoodMenu data, int id)
	{
		mUiModel.setData(data, id);
	}
}
