package com.allan.lin.zhou.scheduler.communication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.camera.CameraView;
import com.allan.lin.zhou.scheduler.Homework;
import com.allan.lin.zhou.scheduler.Inspire;
import com.allan.lin.zhou.scheduler.MainActivity;
import com.allan.lin.zhou.scheduler.mind.Mindfulness;
import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.reminder.list.Reminders;
import com.allan.lin.zhou.scheduler.schedule.Schedule;
import com.allan.lin.zhou.scheduler.databinding.SocialActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.availability.BaseAvailability;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Social extends BaseAvailability {

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

        introMessage();

        binding.sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String text = binding.textEdit.getText().toString();
                String textMessage = text.toLowerCase();

                if (textMessage.isEmpty()) {
                    Toast.makeText(Social.this, "Enter a Message!", Toast.LENGTH_SHORT).show();
                } else {
                    switch(textMessage) {
                        case "home":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, MainActivity.class));
                            break;

                        case "schedule":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, Schedule.class));
                            break;

                        case "camera":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, CameraView.class));
                            break;

                        case "reminders":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, Reminders.class));
                            break;

                        case "homework":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, Homework.class));
                            break;

                        case "inspire":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, Inspire.class));
                            break;

                        case "mindfulness":
                            shortResponse(text);
                            startActivity(new Intent(Social.this, Mindfulness.class));
                            break;

                        default:
                            getResponse(text);
                            binding.textEdit.setText("");
                            break;
                    }

                    binding.textHistory.smoothScrollToPosition(textChatsList.size() - 1);
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
        String BASE_URL = "http://brainshop.ai/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MessageModel> call = retrofitAPI.getMessage(url);

        call.enqueue(new Callback<MessageModel>() {

            @Override
            public void onResponse(Call<MessageModel> call, @NotNull @NonNull Response<MessageModel> response)
                    throws NullPointerException {

                if (response.isSuccessful()) {
                    MessageModel text = response.body();
                    if (text.getCnt() != null) {
                        textChatsList.add(new Chats(text.getCnt(), BOT_KEY));
                    } else {
                        textChatsList.add(new Chats("Could not find API", BOT_KEY));
                    }

                    binding.textHistory.scrollToPosition(textChatsList.size() - 1);
                    textChatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                textChatsList.add(new Chats("Check Internet Connection!", BOT_KEY));
                binding.textHistory.scrollToPosition(textChatsList.size() - 1);
                textChatAdapter.notifyDataSetChanged();
            }
        });
    }

    private void shortResponse(String message) {
        textChatsList.add(new Chats(message, USER_KEY));
        textChatAdapter.notifyDataSetChanged();
        binding.textEdit.setText("");
    }

    private void introMessage() {
        String message = "Welcome! I am a bot from BrainShop's API that runs using machine learning! Use me to translate, " +
                "tell jokes, and anything else!";
        textChatsList.add(new Chats(message, BOT_KEY));
        textChatAdapter.notifyDataSetChanged();
    }
}

// LLXyaMXXeBNznxrr API KEY
// http://api.brainshop.ai/get?bid=158063&key=LLXyaMXXeBNznxrr&uid=[uid]&msg=[msg]