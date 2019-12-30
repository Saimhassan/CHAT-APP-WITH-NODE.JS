package com.example.nodechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtText = findViewById(R.id.edt_text);
        findViewById(R.id.enterButton)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this,ChatActivity.class);
                    intent.putExtra("name",edtText.getText().toString());
                    startActivity(intent);
                });

    }
}
