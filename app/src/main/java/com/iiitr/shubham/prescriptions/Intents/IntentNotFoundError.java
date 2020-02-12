package com.iiitr.shubham.prescriptions.Intents;

public class IntentNotFoundError extends Throwable
{
    private String message;
    public String getMessage()
    {
        return message;
    }
    private void setMessage(String s)
    {
        message = s;
    }
    public IntentNotFoundError()
    {
        String m = "Intent Not Found";
        setMessage(m);
    }
}
