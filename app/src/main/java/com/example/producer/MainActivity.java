package com.example.producer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.valueOf;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Producer";

    String ACTION = "com.example.consumer.MAIN";
    String CATEGORY = "android.intent.category.DEFAULT";

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(TAG,""+intent.toString());
        handleNewIntent(intent);
    }

    public void handleNewIntent(Intent intent){

        //Recieve recievedStringNumberList from consumer
        String recievedStringNumberList = intent.getStringExtra("NUMBER");
        Log.d(TAG,"Recieved_Number_From_Consumer : " + recievedStringNumberList);

        //Spilt the recieved string and put into an arrayList
        ArrayList<String> NumberArrayList = new ArrayList<>(Arrays.asList(recievedStringNumberList.split(",")));

        String Result="";

        try
        {
            for (int j = 0; j < NumberArrayList.size(); j++)
            {
                //Finding the prime factors for the recieved whole number
                int number = Integer.parseInt(NumberArrayList.get(j).trim());
                int i;
                String returnMessage = "";

                //Generate Prime Factors
                ArrayList<Integer> returnValueList = new ArrayList<Integer>();

                for (i = 2; i < number; i++) {
                    while (number % i == 0) {
                        returnValueList.add(valueOf(i));
                        number = number / i;
                    }
                }
                if (number > 2) {
                    returnValueList.add(valueOf(number));
                }

                //Creating one string returnMessage with all the prime factors generated
                for (i = 0; i < returnValueList.size(); i++) {
                    returnMessage = returnMessage + returnValueList.get(i) + " x ";
                    Log.d(TAG, "returnMessage : " + returnMessage);
                }
                //Remove last x
                returnMessage = returnMessage.trim().replaceAll(".$","");

                //Create the final output with all the prime factors corresponding to the Whole number
                Result = Result + Integer.parseInt(NumberArrayList.get(j)) + " = " + returnMessage + "\n";
            }
        }
        catch (NumberFormatException e)
        {
            //When entered numbers are not valid
            Result = "Please enter whole numbers only\n";
        }

        //Send prime factors to consumer
        Intent serviceStarter = new Intent();
        serviceStarter.setAction(ACTION);
        serviceStarter.addCategory(CATEGORY);
        serviceStarter.putExtra("NUMBER",Result);
        this.startActivity(serviceStarter);
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent i = getIntent();
        handleNewIntent(i);
    }
}
