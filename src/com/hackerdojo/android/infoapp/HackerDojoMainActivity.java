package com.hackerdojo.android.infoapp;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.hackerdojo.android.event.EventsFragment;
import com.hackerdojo.android.location.LocationMenuFragment;
import com.hackerdojo.android.shopping.PurchaseSwagFragment;

public class HackerDojoMainActivity extends ActionBarActivity
{
	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;
	
	private Tab mEventsTab;
	private Tab mLocationTab;
	private Tab mPurchaseTab;
	
	public static final String TAG = "HackerDojoMainActivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.main);

			mViewPager = (ViewPager) findViewById(R.id.pager);
			// mViewPager.setAdapter(mSectionsPagerAdapter);
			
			ActionBar bar = getSupportActionBar();
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			mTabsAdapter = new TabsAdapter(this, mViewPager);
			
			mEventsTab = bar.newTab().setText("Events");
			
			mLocationTab = bar.newTab().setText("Location");
			
			mPurchaseTab = bar.newTab().setText("Shop");
			
			mTabsAdapter.addTab(mEventsTab, EventsFragment.class, null);
			mTabsAdapter.addTab(mLocationTab, LocationMenuFragment.class, null);
			mTabsAdapter.addTab(mPurchaseTab, PurchaseSwagFragment.class, null);
			
		}

	@Override
	protected void onResume()
		{
			// TODO Auto-generated method stub
			super.onResume();
		}


	public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener
	{
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo
		{
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args)
			{
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(ActionBarActivity activity, ViewPager pager)
			{
				super(activity.getSupportFragmentManager());
				mContext = activity;
				mActionBar = activity.getSupportActionBar();
				mViewPager = pager;
				mViewPager.setAdapter(this);
				mViewPager.setOnPageChangeListener(this);
			}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args)
			{
				TabInfo info = new TabInfo(clss, args);
				tab.setTag(info);
				tab.setTabListener(this);
				mTabs.add(info);
				mActionBar.addTab(tab);
				notifyDataSetChanged();
			}

		@Override
		public int getCount()
			{
				return mTabs.size();
			}

		@Override
		public Fragment getItem(int position)
			{
				TabInfo info = mTabs.get(position);
				return Fragment.instantiate(mContext, info.clss.getName(),info.args);
			}

		public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels)
			{
			}

		public void onPageSelected(int position)
			{
				mActionBar.setSelectedNavigationItem(position);
			}

		public void onPageScrollStateChanged(int state)
			{
			}

		public void onTabSelected(Tab tab, FragmentTransaction ft)
			{
				Object tag = tab.getTag();
				for (int i = 0; i < mTabs.size(); i++)
					{
						if (mTabs.get(i) == tag)
							{
								mViewPager.setCurrentItem(i);
							}
					}
			}



		@Override
		public void onTabUnselected(Tab tab,
				android.support.v4.app.FragmentTransaction ft)
			{
				// TODO Auto-generated method stub
				
			}

		@Override
		public void onTabReselected(Tab tab,
				android.support.v4.app.FragmentTransaction ft)
			{
				// TODO Auto-generated method stub
				
			}
	}

}