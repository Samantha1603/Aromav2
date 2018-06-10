package com.example.android.aroma;

public class Steps {
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }


    private String step;

    public String getStep_number() {
        return step_number;
    }

    public void setStep_number(String step_number) {
        this.step_number = step_number;
    }

    private String step_number;

    public Steps(String step,String step_number){
        this.setStep(step);
        this.setStep_number(step_number);

    }

}
