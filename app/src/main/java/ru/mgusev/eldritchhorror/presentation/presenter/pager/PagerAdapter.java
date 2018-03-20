package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.ui.fragment.InvestigatorChoiceFragment;
import ru.mgusev.eldritchhorror.ui.fragment.ResultGameFragment;
import ru.mgusev.eldritchhorror.ui.fragment.StartDataFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private String[] titleArray;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        titleArray = new String[]{context.getString(R.string.activity_add_party_header), context.getString(R.string.activity_investigators_choice_header), context.getString(R.string.gameResult)};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StartDataFragment.newInstance(position);
            case 1:
                return InvestigatorChoiceFragment.newInstance(position);
            case 2:
                return ResultGameFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}
