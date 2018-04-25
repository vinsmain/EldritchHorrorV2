package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;

public class InvestigatorActivity extends MvpAppCompatActivity implements InvestigatorView, CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    InvestigatorPresenter investigatorPresenter;

    Investigator investigator;
    ImageView invPhotoDialog;
    ImageView invExpansionDialog;
    TextView invNameDialog;
    TextView invOccupationDialog;
    Switch switchStartingGame;
    Switch switchReplacement;
    Switch switchDead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator);

        investigator = (Investigator) getIntent().getParcelableExtra("investigator");

        invPhotoDialog = (ImageView) findViewById(R.id.invPhotoDialog);
        invExpansionDialog = (ImageView) findViewById(R.id.invExpansionDialog);
        invNameDialog = (TextView) findViewById(R.id.invNameDialog);
        invOccupationDialog = (TextView) findViewById(R.id.invOccupationDialog);
        switchStartingGame = (Switch) findViewById(R.id.switchStartingGame);
        switchReplacement = (Switch) findViewById(R.id.switchReplacement);
        switchDead = (Switch) findViewById(R.id.switchDead);

        setListeners();
        //initInvestigator();
    }

    private void setListeners() {
        switchStartingGame.setOnCheckedChangeListener(this);
        switchReplacement.setOnCheckedChangeListener(this);
        switchDead.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
/*
    private void initInvestigator() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier(investigator.imageResource, "drawable", this.getPackageName());
        invPhotoDialog.setImageResource(resourceId);
        invNameDialog.setText(investigator.getName());
        invOccupationDialog.setText(investigator.getOccupation());

        try {
            String expansionResource = HelperFactory.getStaticHelper().getExpansionDAO().getImageResourceByID(investigator.expansionID);
            if (expansionResource != null) {
                resourceId = resources.getIdentifier(expansionResource, "drawable", this.getPackageName());
                invExpansionDialog.setImageResource(resourceId);
                invExpansionDialog.setVisibility(View.VISIBLE);
            } else invExpansionDialog.setVisibility(View.GONE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!investigator.isMale) {
            switchStartingGame.setText(R.string.starting_game_female);
            switchReplacement.setText(R.string.replacement_female);
            switchDead.setText(R.string.dead_female);
        }

        switchStartingGame.setChecked(investigator.isStarting);
        switchReplacement.setChecked(investigator.isReplacement);
        switchDead.setChecked(investigator.isDead);
        if (!switchStartingGame.isChecked() && !switchReplacement.isChecked()) switchDead.setEnabled(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switchStartingGame:
                if (b && switchReplacement.isChecked()) switchReplacement.setChecked(false);
                break;
            case R.id.switchReplacement:
                if (b && switchStartingGame.isChecked()) switchStartingGame.setChecked(false);
                break;
            default:
                break;
        }
        if (switchStartingGame.isChecked() || switchReplacement.isChecked()) switchDead.setEnabled(true);
        else {
            switchDead.setChecked(false);
            switchDead.setEnabled(false);
        }
    }

    @Override
    public void finish() {
        if (!switchStartingGame.isChecked() && !switchReplacement.isChecked()) {
            switchDead.setChecked(false);
        }
        investigator.isStarting = switchStartingGame.isChecked();
        investigator.isReplacement = switchReplacement.isChecked();
        investigator.isDead = switchDead.isChecked();

        Intent intent = new Intent();
        intent.putExtra("investigator", investigator);
        setResult(RESULT_OK, intent);

        super.finish();
    }*/
}
