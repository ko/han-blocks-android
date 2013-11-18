package com.relurori.hanblocks;


import com.relurori.hanblocks.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private String[] mGameplayOptions;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	private Fragment mTempFragment = null;
	private QuickplayFragment mQuizFragment = new QuickplayFragment();
	private StudyFragment mStudyFragment = new StudyFragment();
	private SettingsFragment mSettingsFragment = new SettingsFragment();
	

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	private String mToken = new String();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences_example, false);
		
		onCreateSetupNavigationDrawer();
	}

	private void onCreateSetupNavigationDrawer() {
		mGameplayOptions = getResources().getStringArray(R.array.gameplay_options_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mTitle = mDrawerTitle = getTitle();
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View v) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();	// creates call to onPrepareOptionsMenu()

			}
			
			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View v) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();	// creates call to onPrepareOptionsMenu()
			}
		};
		
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mGameplayOptions));
		
		// Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	}
	
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	// Sync toggle state after onRestoreInstanceState has occured
    	mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Pass event to ActionBarDrawerToggle
    	// If it returns true, then it has handled
    	// the app icon touch event
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
    		return true;
    	}
    	// Handle other action bar items....
    	
    	return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	private void selectItem(int position) {
		Bundle b = new Bundle();
		switch(position) {
		case 0:
			mTempFragment = mStudyFragment;
			b.putInt(mStudyFragment.DRAWER_ITEM_KEY, position);
			break;
		case 1:
			mTempFragment = mQuizFragment;
			b.putInt(mQuizFragment.DRAWER_ITEM_KEY, position);
			break;
		case 2:
		case 3:
		default:
			mTempFragment = mSettingsFragment;
			b.putInt(mSettingsFragment.DRAWER_ITEM_KEY, position);
		}
		mTempFragment.setArguments(b);
		
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction()
			.replace(R.id.content_frame, mTempFragment)
			.commit();
		
		mDrawerList.setItemChecked(position, true);
		setTitle(mGameplayOptions[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}

	public void setToken(String s) {
		Log.d(TAG,"setToken|mToken=" + mToken + "|s=" + s);
		mToken = s;
	}
	public String getToken() {
		Log.d(TAG,"getToken|mToken=" + mToken);
		return mToken;
	}
}
