package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.ui.fragment.pager.GamePhotoFragment;
import ru.mgusev.eldritchhorror.ui.fragment.pager.InvestigatorChoiceFragment;
import ru.mgusev.eldritchhorror.ui.fragment.pager.ResultGameFragment;
import ru.mgusev.eldritchhorror.ui.fragment.pager.StartDataFragment;


public class PagerAdapter extends FragmentPagerAdapter {

    private String[] titleArray;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        titleArray = new String[]{context.getString(R.string.activity_add_party_header), context.getString(R.string.activity_investigators_choice_header), context.getString(R.string.gameResult), context.getString(R.string.game_photo_header)};
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
            case 3:
                return GamePhotoFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}
