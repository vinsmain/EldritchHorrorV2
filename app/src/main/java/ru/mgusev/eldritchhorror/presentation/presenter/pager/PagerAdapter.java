package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.ui.activity.main.PagerActivity;
import ru.mgusev.eldritchhorror.ui.fragment.StartingDataFragment;

public class PagerAdapter  extends FragmentPagerAdapter {

    private StartingDataFragment startingDataFragment;
    //private InvestigatorsChoiceFragment investigatorsChoiceFragment;
    //private ResultGameFragment resultGameFragment;
    private String[] titleArray;

    public PagerAdapter(Context context, FragmentManager fm//, PassMeLinkOnObject activity
                         ) {
        super(fm);
        titleArray = new String[]{context.getString(R.string.activity_add_party_header), context.getString(R.string.activity_investigators_choice_header), context.getString(R.string.gameResult)};
        //startingDataFragment = StartingDataFragment.newInstance(0);
        //investigatorsChoiceFragment = InvestigatorsChoiceFragment.newInstance(1, activity);
        //resultGameFragment = ResultGameFragment.newInstance(2, activity);
    }

    @Override
    public Fragment getItem(int position) {
        //if (position == 0)
            return StartingDataFragment.newInstance(0);
        //else if (position == 1) return investigatorsChoiceFragment;
        //else return resultGameFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}
