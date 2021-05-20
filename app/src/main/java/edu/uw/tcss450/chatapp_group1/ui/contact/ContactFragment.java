package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import edu.uw.tcss450.chatapp_group1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    /**
     * On create for contact fragment
     * @param savedInstanceState saved instance state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * On create view for contact fragment
     * @param inflater layout inflater
     * @param container container for view group
     * @param savedInstanceState saved instance state
     * @return inflate the layout for contact fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    /**
     * on view created for contact fragment
     * @param view view for contact fragment
     * @param savedInstanceState saved instance state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        //add menu with fragment to adapter
        adapter.addFrag(new ContactListFragment(),"FRIENDS");
        adapter.addFrag(new ContactRequestFragment(),"REQUEST");
        adapter.addFrag(new ContactSearchFragment(), "FIND NEW");
        //set background to no color
        tabLayout.setBackground(null);
        //set adapter and initial menu position
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        //onchange listener for adapter
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                viewPager.getAdapter().notifyDataSetChanged();
            }
        });
    }
}