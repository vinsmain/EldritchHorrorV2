package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityInvestigatorBinding;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.InvestigatorPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

public class InvestigatorActivity extends MvpAppCompatActivity implements InvestigatorView, CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    InvestigatorPresenter investigatorPresenter;

    private ActivityInvestigatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvestigatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }
    }

    @Override
    public void setListeners() {
        binding.investigatorStartingGameSwitch.setOnCheckedChangeListener(this);
        binding.investigatorReplacementSwitch.setOnCheckedChangeListener(this);
        binding.investigatorDeadSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void setMaleOrFemale(boolean isMale) {
        if (isMale) {
            binding.investigatorStartingGameSwitch.setText(R.string.starting_game);
            binding.investigatorReplacementSwitch.setText(R.string.replacement);
            binding.investigatorDeadSwitch.setText(R.string.dead);
        } else {
            binding.investigatorStartingGameSwitch.setText(R.string.starting_game_female);
            binding.investigatorReplacementSwitch.setText(R.string.replacement_female);
            binding.investigatorDeadSwitch.setText(R.string.dead_female);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.investigator_starting_game_switch && b) {
            binding.investigatorReplacementSwitch.setChecked(false);
        } else if (compoundButton.getId() == R.id.investigator_replacement_switch && b) {
            binding.investigatorStartingGameSwitch.setChecked(false);
        }
        saveInvestigatorChange();
    }

    private void saveInvestigatorChange() {
        investigatorPresenter.setInvestigator(binding.investigatorStartingGameSwitch.isChecked(), binding.investigatorReplacementSwitch.isChecked(), binding.investigatorDeadSwitch.isChecked());
    }

    @Override
    public void showInvestigatorCard(Investigator investigator) {
        binding.investigatorPhoto.setImageResource(getResources().getIdentifier(investigator.getImageResource(), "drawable", getPackageName()));
        binding.investigatorName.setText(investigator.getName());
        binding.investigatorOccupation.setText(investigator.getOccupation());
        binding.investigatorStartingGameSwitch.setChecked(investigator.getIsStarting());
        binding.investigatorReplacementSwitch.setChecked(investigator.getIsReplacement());
        binding.investigatorDeadSwitch.setChecked(investigator.getIsDead());
    }

    @Override
    public void showExpansionIcon(String iconName) {
        binding.investigatorExpansionIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
    }

    @Override
    public void showSpecializationIcon(String iconName) {
        binding.investigatorSpecializationIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
    }
}
