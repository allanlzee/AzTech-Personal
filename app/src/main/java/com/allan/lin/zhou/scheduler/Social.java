package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.SocialActivityBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Social extends AppCompatActivity {

    private Toolbar toolbar;
    private SocialActivityBinding binding;

    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";

    private ArrayList<Chats> textChatsList;
    private ChatAdapter textChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_activity);

        binding = SocialActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textChatsList = new ArrayList<>();
        textChatAdapter = new ChatAdapter(textChatsList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        binding.textHistory.setLayoutManager(manager);
        binding.textHistory.setAdapter(textChatAdapter);

        binding.sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String textMessage = binding.textEdit.getText().toString().toLowerCase();

                if (textMessage.isEmpty()) {
                    Toast.makeText(Social.this, "Enter a Message!", Toast.LENGTH_SHORT).show();
                } else {
                    switch(textMessage) {
                        case "home":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, MainActivity.class));
                            break;

                        case "schedule":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, Schedule.class));
                            break;

                        case "camera":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, CameraView.class));
                            break;

                        case "reminders":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, Reminders.class));
                            break;

                        case "homework":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, Homework.class));
                            break;

                        case "inspire":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, Inspire.class));
                            break;

                        case "mindfulness":
                            shortResponse(textMessage);
                            startActivity(new Intent(Social.this, Mindfulness.class));
                            break;

                        default:
                            getResponse(textMessage);
                            binding.textEdit.setText("");
                            break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getResponse(String message) {
        textChatsList.add(new Chats(message, USER_KEY));
        textChatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=158067&key=9AOrAxbhob5jN7Kq&uid=[uid]&msg=" + message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Message> call = retrofitAPI.getMessage(url);

        call.enqueue(new Callback<Message>() {

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message text = response.body();
                    textChatsList.add(new Chats(text.getMsg(), BOT_KEY));
                    textChatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                textChatsList.add(new Chats("Please Ask Again!", BOT_KEY));
                textChatAdapter.notifyDataSetChanged();
            }
        });
    }

    private void shortResponse(String message) {
        textChatsList.add(new Chats(message, USER_KEY));
        textChatAdapter.notifyDataSetChanged();
        binding.textEdit.setText("");
    }
}

// LLXyaMXXeBNznxrr API KEY
// http://api.brainshop.ai/get?bid=158063&key=LLXyaMXXeBNznxrr&uid=[uid]&msg=[msg]