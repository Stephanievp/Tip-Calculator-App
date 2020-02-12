package com.example.tipcalculator;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements TextWatcher,SeekBar.OnSeekBarChangeListener{

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private TextView textViewPercent;
    private TextView tipAmount;
    private TextView totalAmount;
    private SeekBar seekBar;

    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent = .15;

    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add Listeners to Widgets
        editTextBillAmount = (EditText)findViewById(R.id.editText_BillAmount);

     /*  Note: each View that will be retrieved using findViewById needs to map to a View with the matching id
    Example: editTextBillAmount
    Needs to map to a View with the following: android:id="@+id/editText_BillAmount
    */
        editTextBillAmount.addTextChangedListener((TextWatcher) this);

        textViewBillAmount = (TextView)findViewById(R.id.textView_BillAmount);
        textViewPercent = (TextView)findViewById(R.id.textView_TipPercentage);
        tipAmount = (TextView)findViewById(R.id.tip_Amount);
        totalAmount = (TextView)findViewById(R.id.total_Amount);
        seekBar = (SeekBar)findViewById(R.id.seekbar);

        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        calculate();
    }
    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        //charSequence is converted to a String and parsed to a double for you
        billAmount = Double.parseDouble(charSequence.toString()) / 100.0; Log.d("MainActivity", "Bill Amount = "+billAmount);
        //setText on the textView
        //textViewBillAmount.setText(currencyFormat.format(billAmount));
        //perform tip and total calculation and update UI by calling calculate
         calculate();//uncomment this line
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        percent = progress / 100.0; //calculate percent based on seeker value
        //textViewPercent.setText(percentFormat.format(progress * .01));
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        percent = seekBar.getProgress() /100.0;
        calculate();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        percent = seekBar.getProgress() /100.0;
        calculate();

    }

    // calculate and display tip and total amounts
    private void calculate() {
        Log.d("MainActivity", "inside calculate method");

        try {
            if (billAmount == 0.0);
            throw new Exception("");
        }
        catch (Exception e){
            Log.d("MainActivity", "bill amount cannot be zero");
        }

            // format percent and display in percentTextView
            textViewPercent.setText(percentFormat.format(percent));

            // calculate the tip and total
            double tip = billAmount * percent;

            //use the tip example to do the same for the Total
            double total = billAmount + tip;

            // display tip and total formatted as currency
            //user currencyFormat instead of percentFormat to set the textViewTip
            tipAmount.setText(currencyFormat.format(tip));

            //use the tip example to do the same for the Total
            totalAmount.setText(currencyFormat.format(total));

    }
}
