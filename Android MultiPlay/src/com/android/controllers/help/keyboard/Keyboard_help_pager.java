package com.android.controllers.help.keyboard;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.multiplay.R;

/** Adapter support the display of the view, based on moving between views 
 * within a single activity using gestures shift to the left and right.
 * 
 * @author Piotr Baczkiewicz
 * @author tomasz
 *
 */
public class Keyboard_help_pager extends FragmentPagerAdapter {
	
	/**
	 * 
	 */
	private final int PAGE_COUNT = 2;
	
	/**
	 * 
	 */
	private final int[] PAGE_TITLES = {
			R.string.help_keyboard_page_title_1,
			R.string.help_keyboard_page_title_2
	};

	/**
	 * 
	 */
	Resources resources = null;
	
	/**
	 * @param fragmentManager
	 * @param resources
	 */
	public Keyboard_help_pager(FragmentManager fragmentManager, Resources resources) {
		super(fragmentManager);
		this.resources = resources;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
		Keyboard_Fragment myFragment = new Keyboard_Fragment();
		Bundle data = new Bundle();
		data.putInt("current_page", position + 1);
		myFragment.setArguments(data);
		return myFragment;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	public CharSequence getPageTitle(int position) {
		return resources.getString(PAGE_TITLES[position]);
	}
}