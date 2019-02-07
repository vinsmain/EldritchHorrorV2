package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

public class InvestigatorActivity extends MvpAppCompatActivity implements InvestigatorView, CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    InvestigatorPresenter investigatorPresenter;

    @BindView(R.id.investigator_photo) ImageView photoImage;
    @BindView(R.id.investigator_expansion_icon) ImageView expansionIcon;
    @BindView(R.id.investigator_specialization_icon) ImageView specializationIcon;
    @BindView(R.id.investigator_name) TextView nameTV;
    @BindView(R.id.investigator_occupation) TextView occupationTV;
    @BindView(R.id.investigator_starting_game_switch) Switch startingGameSwitch;
    @BindView(R.id.investigator_replacement_switch) Switch replacementSwitch;
    @BindView(R.id.investigator_dead_switch) Switch deadSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator);
        ButterKnife.bind(this);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }
    }

    @Override
    public void setListeners() {
        startingGameSwitch.setOnCheckedChangeListener(this);
        replacementSwitch.setOnCheckedChangeListener(this);
        deadSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void setMaleOrFemale(boolean isMale) {
        if (isMale) {
            startingGameSwitch.setText(R.string.starting_game);
            replacementSwitch.setText(R.string.replacement);
            deadSwitch.setText(R.string.dead);
        } else {
            startingGameSwitch.setText(R.string.starting_game_female);
            replacementSwitch.setText(R.string.replacement_female);
            deadSwitch.setText(R.string.dead_female);
        }
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
        startingGameSwitch.setChecked(investigator.getIsStarting());
        replacementSwitch.setChecked(investigator.getIsReplacement());
        deadSwitch.setChecked(investigator.getIsDead());
    }

    @Override
    public void showExpansionIcon(String iconName) {
        expansionIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
    }

    @Override
    public void showSpecializationIcon(String iconName) {
        specializationIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
    }
}