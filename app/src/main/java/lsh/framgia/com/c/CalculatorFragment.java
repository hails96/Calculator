package lsh.framgia.com.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener {

    private TextView mTextViewCalculation;
    private TextView mTextViewResult;
    private Button mButtonAc;
    private Button mButtonSign;
    private Button mButtonPercent;
    private Button mButtonDivide;
    private Button mButtonMultiply;
    private Button mButtonSubtract;
    private Button mButtonAdd;
    private Button mButtonResult;
    private Button mButtonDot;
    private Button mButtonZero;
    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;
    private Button mButtonFour;
    private Button mButtonFive;
    private Button mButtonSix;
    private Button mButtonSeven;
    private Button mButtonEight;
    private Button mButtonNine;
    private Calculator mCalculator;
    private String mInput = Constant.EMPTY_STRING;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        mCalculator = new Calculator();
        setupReferences(view);
        setupListeners();
        getSavedResult();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear:
                clearCalculation();
                break;
            case R.id.item_save:
                saveResult(mCalculator.compute());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_result:
                handleCompute();
                break;
            case R.id.button_add:
                appendOperatorToCalculation(Constant.ADD);
                break;
            case R.id.button_subtract:
                appendOperatorToCalculation(Constant.SUB);
                break;
            case R.id.button_multiply:
                appendOperatorToCalculation(Constant.MUL);
                break;
            case R.id.button_divide:
                appendOperatorToCalculation(Constant.DIV);
                break;
            case R.id.button_ac:
                clearCalculation();
                break;
            case R.id.button_percent:
                if (mTextViewCalculation.getText().toString().isEmpty()) return;
                appendNumberToCalculation(Constant.PERCENT);
                break;
            case R.id.button_dot:
                appendNumberToCalculation(Constant.DOT);
                break;
            case R.id.button_one:
                appendNumberToCalculation(Constant.ONE);
                break;
            case R.id.button_two:
                appendNumberToCalculation(Constant.TWO);
                break;
            case R.id.button_three:
                appendNumberToCalculation(Constant.THREE);
                break;
            case R.id.button_four:
                appendNumberToCalculation(Constant.FOUR);
                break;
            case R.id.button_five:
                appendNumberToCalculation(Constant.FIVE);
                break;
            case R.id.button_six:
                appendNumberToCalculation(Constant.SIX);
                break;
            case R.id.button_seven:
                appendNumberToCalculation(Constant.SEVEN);
                break;
            case R.id.button_eight:
                appendNumberToCalculation(Constant.EIGHT);
                break;
            case R.id.button_nine:
                appendNumberToCalculation(Constant.NINE);
                break;
            case R.id.button_zero:
                appendNumberToCalculation(Constant.ZERO);
                break;
            default:
                break;
        }
    }

    private void handleCompute() {
        if (mInput.isEmpty()) return;
        try {
            if (mInput.contains(Constant.PERCENT))
                mCalculator.setValueTwo(Double.parseDouble(
                        mInput.replace(Constant.PERCENT, Constant.EMPTY_STRING)) / 100);
            else mCalculator.setValueTwo(Double.parseDouble(mInput));
            mInput = Constant.EMPTY_STRING;
            mTextViewResult.setText(
                    getString(R.string.calculation_result_place_holder, mCalculator.compute()));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), getString(R.string.error_syntax), Toast.LENGTH_SHORT).show();
        }
    }

    private void appendNumberToCalculation(String s) {
        if (!mTextViewResult.getText().toString().isEmpty()) clearCalculation();
        mTextViewCalculation.setText(
                getString(R.string.calculation_number_place_holder, mTextViewCalculation.getText().toString(), s));
        mInput += s;
    }

    private void appendOperatorToCalculation(String operator) {
        if (mTextViewCalculation.getText().toString().isEmpty()) return;
        if (!mTextViewResult.getText().toString().isEmpty()) {
            mCalculator.setValueOne(mCalculator.compute());
            mTextViewResult.setText(Constant.EMPTY_STRING);
            mTextViewCalculation.setText(String.valueOf(mCalculator.getValueOne()));
        } else try {
            if (mInput.contains(Constant.PERCENT)) {
                mCalculator.setValueOne(Double.parseDouble(
                        mInput.replace(Constant.PERCENT, Constant.EMPTY_STRING)) / 100);
                return;
            }
            mCalculator.setValueOne(Double.parseDouble(mInput));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), getString(R.string.error_syntax), Toast.LENGTH_SHORT).show();
        }
        mCalculator.setOperator(operator);
        mTextViewCalculation.setText(getString(R.string.calculation_operator_place_holder,
                mTextViewCalculation.getText(), operator));
        mInput = Constant.EMPTY_STRING;
    }

    private void clearCalculation() {
        mTextViewCalculation.setText(Constant.EMPTY_STRING);
        mTextViewResult.setText(Constant.EMPTY_STRING);
        mCalculator.setValueOne(Double.NaN);
        mCalculator.setValueTwo(Double.NaN);
        mCalculator.setOperator(null);
    }

    private void saveResult(double result) {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);
        prefs.edit()
                .putFloat(Constant.PREF_RESULT_KEY, (float) result)
                .apply();
    }

    private void getSavedResult() {
        SharedPreferences prefs = getActivity().
                getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        float savedResult = prefs.getFloat(Constant.PREF_RESULT_KEY, Float.NaN);
        mInput = String.valueOf(savedResult);
        mTextViewCalculation.setText(mInput);
    }

    private void setupReferences(View view) {
        mTextViewCalculation = view.findViewById(R.id.text_view_calculation);
        mTextViewResult = view.findViewById(R.id.text_view_result);
        mButtonAc = view.findViewById(R.id.button_ac);
        mButtonSign = view.findViewById(R.id.button_sign);
        mButtonPercent = view.findViewById(R.id.button_percent);
        mButtonDivide = view.findViewById(R.id.button_divide);
        mButtonMultiply = view.findViewById(R.id.button_multiply);
        mButtonSubtract = view.findViewById(R.id.button_subtract);
        mButtonAdd = view.findViewById(R.id.button_add);
        mButtonResult = view.findViewById(R.id.button_result);
        mButtonDot = view.findViewById(R.id.button_dot);
        mButtonOne = view.findViewById(R.id.button_one);
        mButtonTwo = view.findViewById(R.id.button_two);
        mButtonThree = view.findViewById(R.id.button_three);
        mButtonFour = view.findViewById(R.id.button_four);
        mButtonFive = view.findViewById(R.id.button_five);
        mButtonSix = view.findViewById(R.id.button_six);
        mButtonSeven = view.findViewById(R.id.button_seven);
        mButtonEight = view.findViewById(R.id.button_eight);
        mButtonNine = view.findViewById(R.id.button_nine);
        mButtonZero = view.findViewById(R.id.button_zero);
    }

    private void setupListeners() {
        mButtonAc.setOnClickListener(this);
        mButtonSign.setOnClickListener(this);
        mButtonPercent.setOnClickListener(this);
        mButtonDivide.setOnClickListener(this);
        mButtonMultiply.setOnClickListener(this);
        mButtonSubtract.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonResult.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);
        mButtonOne.setOnClickListener(this);
        mButtonTwo.setOnClickListener(this);
        mButtonThree.setOnClickListener(this);
        mButtonFour.setOnClickListener(this);
        mButtonFive.setOnClickListener(this);
        mButtonSix.setOnClickListener(this);
        mButtonSeven.setOnClickListener(this);
        mButtonEight.setOnClickListener(this);
        mButtonNine.setOnClickListener(this);
        mButtonZero.setOnClickListener(this);
    }
}
