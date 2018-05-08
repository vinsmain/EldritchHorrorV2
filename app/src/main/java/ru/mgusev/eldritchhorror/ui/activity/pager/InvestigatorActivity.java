package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;

public class InvestigatorActivity extends MvpAppCompatActivity implements InvestigatorView, CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    InvestigatorPresenter investigatorPresenter;

    private ImageView photoImage;
    private ImageView expansionIcon;
    private TextView nameTV;
    private TextView occupationTV;
    private Switch startingGameSwitch;
    private Switch replacementSwitch;
    private Switch deadSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator);

        photoImage = findViewById(R.id.investigator_photo);
        expansionIcon = findViewById(R.id.investigator_expansion_icon);
        nameTV = findViewById(R.id.investigator_name);
        occupationTV = findViewById(R.id.investigator_occupation);
        startingGameSwitch = findViewById(R.id.investigator_starting_game_switch);
        replacementSwitch = findViewById(R.id.investigator_replacement_switch);
        deadSwitch = findViewById(R.id.investigator_dead_switch);

        setListeners();
    }

    private void setListeners() {
        startingGameSwitch.setOnCheckedChangeListener(this);
        replacementSwitch.setOnCheckedChangeListener(this);
        deadSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.investigator_starting_game_switch:
                if (b) replacementSwitch.setChecked(false);
                break;
            case R.id.investigator_replacement_switch:
                if (b) startingGameSwitch.setChecked(false);
                break;
            default:
                break;
        }
        saveInvestigatorChange();
    }

    private void saveInvestigatorChange() {
        investigatorPresenter.setInvestigator(startingGameSwitch.isChecked(), replacementSwitch.isChecked(), deadSwitch.isChecked());
    }

    @Override
    public void showInvestigatorCard(Investigator investigator) {
        photoImage.setImageResource(getResources().getIdentifier(investigator.getImageResource(), "drawable", getPackageName()));
        nameTV.setText(investigator.getName());
        occupationTV.setText(investigator.getOccupation());
        startingGameSwitch.setChecked(investigator.isStarting());
        replacementSwitch.setChecked(investigator.isReplacement());
        deadSwitch.setChecked(investigator.isDead());
    }

    @Override
    public void showExpansionIcon(String iconName) {
        expansionIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
    }
}
