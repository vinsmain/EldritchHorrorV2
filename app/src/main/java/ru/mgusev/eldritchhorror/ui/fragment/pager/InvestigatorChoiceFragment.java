package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.InvestigatorChoiceAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.InvestigatorActivity;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class InvestigatorChoiceFragment extends MvpAppCompatFragment implements InvestigatorChoiceView, OnItemClicked {

    @InjectPresenter
    InvestigatorChoicePresenter investigatorChoicePresenter;

    @BindView(R.id.inv_choice_recycler_view) RecyclerView invRecycleView;

    private Unbinder unbinder;
    private InvestigatorChoiceAdapter adapter;
    private AlertDialog clearDialog;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(getContext(), MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        View view = inflater.inflate(R.layout.fragment_investigator_choice, null);

        unbinder = ButterKnife.bind(this, view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            columnsCount = 5;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), columnsCount);
        invRecycleView.setLayoutManager(gridLayoutManager);
        invRecycleView.setHasFixedSize(true);

        adapter = new InvestigatorChoiceAdapter();
        invRecycleView.setAdapter(adapter);
        adapter.setOnClick(this);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        investigatorChoicePresenter.itemClick(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onEditClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void updateAllItems(List<Investigator> investigatorList) {
        adapter.updateAllInvCards(investigatorList);
    }

    @Override
    public void updateItem(int position, List<Investigator> investigatorList) {
        adapter.updateInvCard(position, investigatorList);
    }

    @Override
    public void moveItem(int oldPosition, int newPosition, List<Investigator> investigatorList) {
        adapter.moveInvCard(oldPosition, newPosition, investigatorList);
    }

    @Override
    public void removeItem(int position, List<Investigator> investigatorList) {
        adapter.removeInvCard(position, investigatorList);
    }

    @Override
    public void showInvestigatorActivity() {
        Intent intent = new Intent(this.getContext(), InvestigatorActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.alert_investigators_limit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showClearInvListDialog() {
        if (clearDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setCancelable(false);
            builder.setTitle(R.string.dialogAlert);
            builder.setMessage(R.string.cleanDialogMessage);
            builder.setIcon(R.drawable.cancel_select);
            builder.setPositiveButton(R.string.messageOK, (dialog, which) -> {
                investigatorChoicePresenter.clearInvestigatorList();
                investigatorChoicePresenter.dismissDialog();
            });
            builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> investigatorChoicePresenter.dismissDialog());
            clearDialog = builder.show();
        }
    }

    @Override
    public void hideClearInvListDialog() {
        clearDialog = null;
        //Delete showDialog() from currentState with DismissDialogStrategy
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(clearDialog != null && clearDialog.isShowing()) clearDialog.dismiss();
        unbinder.unbind();
    }
}