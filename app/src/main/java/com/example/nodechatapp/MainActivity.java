package com.example.nodechatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },10);

        EditText edtText = findViewById(R.id.edt_text);
        findViewById(R.id.enterButton)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this,ChatActivity.class);
                    intent.putExtra("name",edtText.getText().toString());
                    startActivity(intent);
                });

    }
}
