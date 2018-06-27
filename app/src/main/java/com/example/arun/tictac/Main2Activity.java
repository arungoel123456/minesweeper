package com.example.arun.tictac;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    public static String NAME_KEY = "player_name";
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText=findViewById(R.id.input);
    }

    public void startMainActivity(View view){

        String name = editText.getText().toString();

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(NAME_KEY,name);
        startActivity(intent);

    }
}
