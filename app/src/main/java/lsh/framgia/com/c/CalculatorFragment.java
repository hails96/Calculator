package lsh.framgia.com.c;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

    private TextView mCalculationTv;
    private TextView mResultTv;
    private Button mAcBtn;
    private Button mSignBtn;
    private Button mPercentBtn;
    private Button mDivideBtn;
    private Button mMultiplyBtn;
    private Button mSubtractBtn;
    private Button mAddBtn;
    private Button mResultBtn;
    private Button mDotBtn;
    private Button mZeroBtn;
    private Button mOneBtn;
    private Button mTwoBtn;
    private Button mThreeBtn;
    private Button mFourBtn;
    private Button mFiveBtn;
    private Button mSixBtn;
    private Button mSevenBtn;
    private Button mEightBtn;
    private Button mNineBtn;
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
            case R.id.clear: {
                clearCalculation();
                break;
            }
            case R.id.save: {
                saveResult(mCalculator.compute());
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_result: {
                if (mInput.isEmpty()) return;
                try {
                    if (mInput.contains(Constant.PERCENT))
                        mCalculator.setValueTwo(Double.parseDouble(mInput.replace(Constant.PERCENT, Constant.EMPTY_STRING)) / 100);
                    else mCalculator.setValueTwo(Double.parseDouble(mInput));
                    mInput = Constant.EMPTY_STRING;
                    mResultTv.setText(getString(R.string.calculation_result_place_holder, mCalculator.compute()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.syntax_error_message), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_add: {
                appendOperatorToCalculation(Constant.ADD);
                break;
            }
            case R.id.btn_subtract: {
                appendOperatorToCalculation(Constant.SUB);
                break;
            }
            case R.id.btn_multiply: {
                appendOperatorToCalculation(Constant.MUL);
                break;
            }
            case R.id.btn_divide: {
                appendOperatorToCalculation(Constant.DIV);
                break;
            }
            case R.id.btn_ac: {
                clearCalculation();
                break;
            }
            case R.id.btn_percent: {
                if (mCalculationTv.getText().toString().isEmpty()) return;
                appendNumberToCalculation(Constant.PERCENT);
                break;
            }
            case R.id.btn_dot: {
                appendNumberToCalculation(Constant.DOT);
                break;
            }
            case R.id.btn_one: {
                appendNumberToCalculation(Constant.ONE);
                break;
            }
            case R.id.btn_two: {
                appendNumberToCalculation(Constant.TWO);
                break;
            }
            case R.id.btn_three: {
                appendNumberToCalculation(Constant.THREE);
                break;
            }
            case R.id.btn_four: {
                appendNumberToCalculation(Constant.FOUR);
                break;
            }
            case R.id.btn_five: {
                appendNumberToCalculation(Constant.FIVE);
                break;
            }
            case R.id.btn_six: {
                appendNumberToCalculation(Constant.SIX);
                break;
            }
            case R.id.btn_seven: {
                appendNumberToCalculation(Constant.SEVEN);
                break;
            }
            case R.id.btn_eight: {
                appendNumberToCalculation(Constant.EIGHT);
                break;
            }
            case R.id.btn_nine: {
                appendNumberToCalculation(Constant.NINE);
                break;
            }
            case R.id.btn_zero: {
                appendNumberToCalculation(Constant.ZERO);
                break;
            }
        }
    }

    private void appendNumberToCalculation(String s) {
        if (!mResultTv.getText().toString().isEmpty()) clearCalculation();
        mCalculationTv.setText(
                getString(R.string.calculation_number_place_holder, mCalculationTv.getText().toString(), s)
        );
        mInput += s;
    }

    private void appendOperatorToCalculation(String operator) {
        if (mCalculationTv.getText().toString().isEmpty()) return;
        if (!mResultTv.getText().toString().isEmpty()) {
            mCalculator.setValueOne(mCalculator.compute());
            mResultTv.setText(Constant.EMPTY_STRING);
            mCalculationTv.setText(String.valueOf(mCalculator.getValueOne()));
        } else try {
            if (mInput.contains(Constant.PERCENT))
                mCalculator.setValueOne(Double.parseDouble(mInput.replace(Constant.PERCENT, Constant.EMPTY_STRING)) / 100);
            else mCalculator.setValueOne(Double.parseDouble(mInput));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), getString(R.string.syntax_error_message), Toast.LENGTH_SHORT).show();
        }
        mCalculator.setOperator(operator);
        mCalculationTv.setText(getString(R.string.calculation_operator_place_holder, mCalculationTv.getText(), operator));
        mInput = Constant.EMPTY_STRING;
    }

    private void clearCalculation() {
        mCalculationTv.setText(Constant.EMPTY_STRING);
        mResultTv.setText(Constant.EMPTY_STRING);
        mCalculator.setValueOne(Double.NaN);
        mCalculator.setValueTwo(Double.NaN);
        mCalculator.setOperator(null);
    }

    private void saveResult(double result) {
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        prefs.edit()
                .putFloat(Constant.PREF_RESULT_KEY, (float) result)
                .apply();
    }

    private void getSavedResult() {
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        float savedResult = prefs.getFloat(Constant.PREF_RESULT_KEY, Float.NaN);
        mInput = String.valueOf(savedResult);
        mCalculationTv.setText(mInput);
    }

    private void setupReferences(View view) {
        mCalculationTv = view.findViewById(R.id.tv_calculation);
        mResultTv = view.findViewById(R.id.tv_result);
        mAcBtn = view.findViewById(R.id.btn_ac);
        mSignBtn = view.findViewById(R.id.btn_sign);
        mPercentBtn = view.findViewById(R.id.btn_percent);
        mDivideBtn = view.findViewById(R.id.btn_divide);
        mMultiplyBtn = view.findViewById(R.id.btn_multiply);
        mSubtractBtn = view.findViewById(R.id.btn_subtract);
        mAddBtn = view.findViewById(R.id.btn_add);
        mResultBtn = view.findViewById(R.id.btn_result);
        mDotBtn = view.findViewById(R.id.btn_dot);
        mOneBtn = view.findViewById(R.id.btn_one);
        mTwoBtn = view.findViewById(R.id.btn_two);
        mThreeBtn = view.findViewById(R.id.btn_three);
        mFourBtn = view.findViewById(R.id.btn_four);
        mFiveBtn = view.findViewById(R.id.btn_five);
        mSixBtn = view.findViewById(R.id.btn_six);
        mSevenBtn = view.findViewById(R.id.btn_seven);
        mEightBtn = view.findViewById(R.id.btn_eight);
        mNineBtn = view.findViewById(R.id.btn_nine);
        mZeroBtn = view.findViewById(R.id.btn_zero);
    }

    private void setupListeners() {
        mAcBtn.setOnClickListener(this);
        mSignBtn.setOnClickListener(this);
        mPercentBtn.setOnClickListener(this);
        mDivideBtn.setOnClickListener(this);
        mMultiplyBtn.setOnClickListener(this);
        mSubtractBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        mResultBtn.setOnClickListener(this);
        mDotBtn.setOnClickListener(this);
        mOneBtn.setOnClickListener(this);
        mTwoBtn.setOnClickListener(this);
        mThreeBtn.setOnClickListener(this);
        mFourBtn.setOnClickListener(this);
        mFiveBtn.setOnClickListener(this);
        mSixBtn.setOnClickListener(this);
        mSevenBtn.setOnClickListener(this);
        mEightBtn.setOnClickListener(this);
        mNineBtn.setOnClickListener(this);
        mZeroBtn.setOnClickListener(this);
    }

}
