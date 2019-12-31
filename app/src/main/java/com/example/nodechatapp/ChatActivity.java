package com.example.nodechatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends AppCompatActivity implements TextWatcher {

    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH = "";
    private EditText messageEdit;
    private View sendBtn, PickImgBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        initiateSocketConnection();
    }

    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request,new SocketListener() );
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
       String string = s.toString().trim();
       if (string.isEmpty())
       {
           resetMessageEdit();
       }
       else
       {
           sendBtn.setVisibility(View.VISIBLE);
           PickImgBtn.setVisibility(View.INVISIBLE);
       }
    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        PickImgBtn.setVisibility(View.VISIBLE);
        messageEdit.addTextChangedListener(this);
    }

    private class SocketListener extends WebSocketListener{
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(()->{
                Toast.makeText(ChatActivity.this, "Socket Connection Success", Toast.LENGTH_SHORT).show();
                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
        }
    }

    private void initializeView() {
        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);
        PickImgBtn = findViewById(R.id.pickImgBtn);
        recyclerView = findViewById(R.id.recyclerView);

        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name",name);
                jsonObject.put("message",messageEdit.getText().toString());
                webSocket.send(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
