package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout ageInputLayout, heightInputLayout, weightInputLayout;
    RadioGroup sexRadioGroup;
    TextView resultView;
    Spinner activityLevelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWidgets();
    }

    private void initializeWidgets() {
        ageInputLayout = findViewById(R.id.ageInputLayout);
        heightInputLayout = findViewById(R.id.heightInputLayout);
        weightInputLayout = findViewById(R.id.weightInputLayout);
        resultView = (TextView) findViewById(R.id.resultView);
        sexRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        activityLevelSpinner = (Spinner) findViewById(R.id.activityLevel);
    }

    public void getCalories(View view) {
        resultView.setText("");
        if (validateInputFields()) {
            double result;
            int sexId = sexRadioGroup.getCheckedRadioButtonId();
            int activityId = activityLevelSpinner.getSelectedItemPosition();
            double amrIndex;
            switch (activityId) {

                case 0:
                    amrIndex = 1.375;
                    break;
                case 1:
                    amrIndex = 1.55;
                    break;
                case 2:
                    amrIndex = 1.725;
                    break;
                default:
                    amrIndex = 1.2;
            }
            if (sexId == R.id.maleRadioButton) {
                result = amrIndex * (66.4730 + (13.7516 * Integer.parseInt(weightInputLayout.getEditText().getText().toString()))
                        + (5.0033 * Integer.parseInt(heightInputLayout.getEditText().getText().toString()))
                        - (6.7550 * Integer.parseInt(ageInputLayout.getEditText().getText().toString())));
            } else {
                result = amrIndex * (655.0955 + (9.5634 * Integer.parseInt(weightInputLayout.getEditText().getText().toString()))
                        + (1.8496 * Integer.parseInt(heightInputLayout.getEditText().getText().toString()))
                        - (4.6756 * Integer.parseInt(ageInputLayout.getEditText().getText().toString())));
            }
            resultView.setText("???????????????? ?????????? ??????????????: " + Math.round(result));
        }
    }

    public boolean validateInputFields() {
        boolean isValid = true;
        if (ageInputLayout.getEditText().getText().toString().isEmpty()) {
            ageInputLayout.setError("????????????????????, ?????????????? ??????????????");
            isValid = false;
        } else if (Integer.parseInt(ageInputLayout.getEditText().getText().toString()) > 100) {
            ageInputLayout.setError("?????????????? ???? ?????????? ???????? ???????? 100 ??????");
            isValid = false;
        }
        else {
            ageInputLayout.setErrorEnabled(false);
        }
        if (heightInputLayout.getEditText().getText().toString().isEmpty()) {
            heightInputLayout.setError("????????????????????, ?????????????? ????????");
            isValid = false;
        } else if (Integer.parseInt(heightInputLayout.getEditText().getText().toString()) > 250 || Integer.parseInt(heightInputLayout.getEditText().getText().toString()) < 30) {
            heightInputLayout.setError("???????? ???? ?????????? ???????? ???????????? 250 ?? ???????????? 30 ??????????????????????");
            isValid = false;
        }
        else {
            heightInputLayout.setErrorEnabled(false);
        }
        if (weightInputLayout.getEditText().getText().toString().isEmpty()) {
            weightInputLayout.setError("????????????????????, ?????????????? ??????");
            isValid = false;
        } else if (Integer.parseInt(weightInputLayout.getEditText().getText().toString()) > 300 || Integer.parseInt(weightInputLayout.getEditText().getText().toString()) < 10) {
            weightInputLayout.setError("?????? ???? ?????????? ???????? ???????????? 300 ?? ???????????? 10 ??????????????????");
            isValid = false;
        }
        else {
            weightInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }


}