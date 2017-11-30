package com.example.fastmsg;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String user = "CD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the edit box to send a message */
    public void removeText(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("");
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        System.out.println(message);
        message = "\n" + user + " : " + message;


        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.append(message);

        //finally clear the EditText box once the message is sent and reset focus
        editText.setText("");
        hideKeyboard(this, view);

    }

    // Call this function to hide the on screen keyboard
    public static void hideKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
