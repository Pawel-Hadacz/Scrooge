package com.scrooge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SimulateActivity extends AppCompatActivity {
    private boolean isStarted;
    private Long idToSimulate;
    private String nameToSimulate;
    private double debtToSimulate;
    private TextView name,debt,commsionLeft;
    private EditText value;
    CountDownTimer cTimer = null;
    private double valueNum;
    private double commission;
    private double simulatedVal;
    private double commisionToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);
        name = findViewById(R.id.simulateNameId);
        debt =  findViewById(R.id.simulateDebtId);

        value = findViewById(R.id.changePerSecVal);
        if(getIntent().hasExtra("DEBTOR_TO_SIMULATE_ID")) {
            idToSimulate = getIntent().getLongExtra("DEBTOR_TO_SIMULATE_ID",0);
            nameToSimulate = getIntent().getStringExtra("DEBTOR_TO_SIMULATE_NAME");
            debtToSimulate = getIntent().getDoubleExtra("DEBTOR_TO_SIMULATE_DEBT",0);
            name.setText(nameToSimulate);
            debt.setText(String.valueOf(debtToSimulate));
            simulatedVal = debtToSimulate;
        }

        findViewById(R.id.simulateBtnId).setOnClickListener(v -> {
            if(validate()) {
                startSimulation();
            }

        });
        findViewById(R.id.stopsimulationId).setOnClickListener(v -> {
            cancelTimer();
        });
    }
    public boolean validate(){
        if(value.getText()!=null && value.getText().length()<0){
            value.setError("field must not be empty!");
            return false;
        }
        if(!value.getText().toString().contains(",")){
            value.setError("Please type value then ',' then commision!(22,5)");
            return false;
        }
        String [] values = value.getText().toString().split(",");

        if(values[0].isEmpty()|| values[1].isEmpty()){
            value.setError("Please type value then ',' then commision!(22,5)");
            return false;
        }

        valueNum = Double.valueOf(values[0]);
        commission = Double.valueOf(values[1]);
        System.out.println("volue num " +valueNum);

        System.out.println("volue num " +commission);
        return true;
    }
    public void startSimulation(){
        new CountDownTimer(1000000000, 1000) {
            public void onTick(long millisUntilFinished) {
               if(millisUntilFinished%1000==0){
                   simulatedVal = simulatedVal - valueNum;
               }
                debt.setText("seconds remaining: " + millisUntilFinished / 1000);
                debt.setText(String.valueOf(simulatedVal));
                if(simulatedVal<=1){
                    cTimer.cancel();
                }
                if(commission!=0) {
                    simulatedVal = (simulatedVal * (commission*0.01));
                    commisionToPay = (simulatedVal * commission) - simulatedVal;
                }
            }

            public void onFinish() {
                value.setText(String.valueOf(commisionToPay));
            }

        }.start();

    }
   public void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
