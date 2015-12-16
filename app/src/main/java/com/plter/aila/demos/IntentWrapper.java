package com.plter.aila.demos;

import android.content.Intent;

/**
 * Created by plter on 12/2/15.
 */
public class IntentWrapper {



    private String label = "label";
    private Intent intent;


    public IntentWrapper(String label,Intent intent) {
        this.label = label;
        this.intent = intent;
    }

    public String getLabel() {
        return label;
    }

    public Intent getIntent() {
        return intent;
    }

    @Override
    public String toString() {
        return label;
    }
}
