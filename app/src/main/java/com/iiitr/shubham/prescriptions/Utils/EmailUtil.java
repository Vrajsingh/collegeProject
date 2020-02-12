package com.iiitr.shubham.prescriptions.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class EmailUtil
{
    private Context mContext;
    public EmailUtil(Context context)
    {
        mContext = context;
    }
    public void sendEmail(String receiver,String message)
    {
        String[] TO = {receiver};
        //String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Prescription : ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
