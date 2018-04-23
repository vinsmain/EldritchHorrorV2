package ru.mgusev.eldritchhorror.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.InvestigatorChoiceAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class InvestigatorChoiceFragment extends MvpAppCompatFragment implements InvestigatorChoiceView, OnItemClicked {

    @InjectPresenter
    InvestigatorChoicePresenter investigatorChoicePresenter;

    private RecyclerView invRecycleView;
    private InvestigatorChoiceAdapter adapter;

    private int columnsCount = 3;

    public static InvestigatorChoiceFragment newInstance(int page) {
        InvestigatorChoiceFragment pageFragment = new InvestigatorChoiceFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investigator_choice, null);

        invRecycleView = view.findViewById(R.id.inv_choice_recycler_view);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 5;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), columnsCount);
        invRecycleView.setLayoutManager(gridLayoutManager);
        invRecycleView.setHasFixedSize(true);

        adapter = new InvestigatorChoiceAdapter();
        invRecycleView.setAdapter(adapter);
        adapter.setOnClick(this);

        return view;
    }

    public void initInvestigatorList() {


        /*try {
            if (investigatorList == null) {
                investigatorList = new ArrayList<>();
            }

            investigatorList.clear();
            investigatorList.addAll(HelperFactory.getStaticHelper().getInvestigatorDAO().getAllInvestigatorsLocal());
            /*invSavedList = activity.getGame().invList;
            if (invSavedList != null) {
                for (int i = 0; i < invSavedList.size(); i++) {
                    for (int j = 0; j < investigatorList.size(); j++) {
                        if (investigatorList.get(j).getName().equals(invSavedList.get(i).getName())) {
                            investigatorList.set(j, invSavedList.get(i));
                            break;
                        }
                    }
                    if (!investigatorList.contains(invSavedList.get(i))) investigatorList.add(invSavedList.get(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        //sortList();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onEditClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void showItems(List<Investigator> investigatorList, List<Expansion> expansionList) {
        adapter.setListStorage(investigatorList, expansionList);
    }
}
