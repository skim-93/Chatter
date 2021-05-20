package edu.uw.tcss450.chatapp_group1.ui.contact;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    /**Initializer for fragment list **/
    private final List<Fragment> mFragmentList = new ArrayList<>();
    /**Initializer for Title list **/
    private final List<String> mFragmentTitleList = new ArrayList<>();
    /**
     * view pager adapter generator
     * @param getChildFragmentManager fragment manager
     * @param i int initializer
     */
    public ViewPagerAdapter(@NonNull FragmentManager getChildFragmentManager, int i) {
        super(getChildFragmentManager, i);
    }

    /**
     * Getter for fragment
     * @param position fragment position
     * @return return with the positioned fragment
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Getter for fragment counter
     * @return fragment list size
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Getter for page title of fragment
     * @param position fragment position
     * @return fragment title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    /**
     * Adder for fragment to adapter
      * @param fragment fragment
     * @param title name of title
     */
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

}
