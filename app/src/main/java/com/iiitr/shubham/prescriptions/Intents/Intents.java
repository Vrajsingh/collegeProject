package com.iiitr.shubham.prescriptions.Intents;

public class Intents {

    public static final String NAME = "name";
    public static final String SYMPTOMS = "symptoms";
    public static final String PRESCRIPTION = "prescription";
    public static final String MEDICINE = "medicine";
    public static final String DIAGNOSIS = "diagnosis";
    public static final String ADVICE = "advice";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String CANCEL = "cancel";

    public static String manualIntentDetection(String s)throws IntentNotFoundError
    {
        s = " "+s.toLowerCase().trim() + " ";
        if(s.contains(NAME))
            return NAME;
        else if(s.contains(SYMPTOMS))
            return SYMPTOMS;
        else if(s.contains(PRESCRIPTION))
            return PRESCRIPTION;
        else if(s.contains(MEDICINE))
            return MEDICINE;
        else if(s.contains(DIAGNOSIS))
            return DIAGNOSIS;
        else if(s.contains(ADVICE))
            return ADVICE;
        else if(s.contains(AGE))
            return AGE;
        else if(s.contains(GENDER))
            return GENDER;
        else if(s.contains(CANCEL))
            return CANCEL;
        throw new IntentNotFoundError();
    }

}
