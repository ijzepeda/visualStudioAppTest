package com.ijzepeda.masterdetailmanual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;

import com.codepath.example.masterdetailmanual.ItemsListFragment.OnItemSelectedListener;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.push.Push;

public class ItemsListActivity extends FragmentActivity implements OnItemSelectedListener {
	private boolean isTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AppCenter.start(getApplication(), String.valueOf(R.string.project_id_vsKe),
//				Analytics.class, Crashes.class);
		AppCenter.start(getApplication(), String.valueOf(R.string.project_id_vsSofttek), Push.class);

		setContentView(R.layout.activity_items);
		determinePaneLayout();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
		if (fragmentItemDetail != null) {
			isTwoPane = true;
			com.ijzepeda.masterdetailmanual.ItemsListFragment fragmentItemsList =
					(com.ijzepeda.masterdetailmanual.ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
			fragmentItemsList.setActivateOnItemClick(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public void onItemSelected(Item item) {
		if (isTwoPane) { // single activity with list and detail
			// Replace frame layout with correct detail fragment
			com.ijzepeda.masterdetailmanual.ItemDetailFragment fragmentItem = com.ijzepeda.masterdetailmanual.ItemDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItem);
			ft.commit();
		} else { // separate activities
			// launch detail activity using intent
			Intent i = new Intent(this, com.ijzepeda.masterdetailmanual.ItemDetailActivity.class);
			i.putExtra("item", item);
			startActivity(i);
		}
	}

}
