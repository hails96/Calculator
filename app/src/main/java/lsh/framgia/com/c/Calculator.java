package lsh.framgia.com.c;

public class Calculator {

    private double mValueOne = Double.NaN;
    private double mValueTwo = Double.NaN;
    private String mOperator;

    public Calculator() {
    }

    public double getValueOne() {
        return mValueOne;
    }

    public void setValueOne(double valueOne) {
        this.mValueOne = valueOne;
    }

    public double getValueTwo() {
        return mValueTwo;
    }

    public void setValueTwo(double valueTwo) {
        this.mValueTwo = valueTwo;
    }

    public String getOperator() {
        return mOperator;
    }

    public void setOperator(String operator) {
        this.mOperator = operator;
    }

    public double compute() {
        switch (mOperator) {
            case Constant.ADD: {
                return mValueOne + mValueTwo;
            }
            case Constant.SUB: {
                return mValueOne - mValueTwo;
            }
            case Constant.MUL: {
                return mValueOne * mValueTwo;
            }
            case Constant.DIV: {
                return mValueOne / mValueTwo;
            }
        }
        return Double.NaN;
    }
}
