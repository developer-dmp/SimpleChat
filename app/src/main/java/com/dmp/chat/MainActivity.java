package com.dmp.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton sendButton;
    private ListView mListView;
    private EditText userInput;
    private ChatAdapter mAdapter;
    private List<ChatMessage> messages = new ArrayList<>();;

    private boolean myMessage = true;

    private SharedPreferences preferences;
    private String preferenceFileChatKey = "chat";
    private String preferenceFileName = "chat_preferences";

    private String chatMessageDelim = ";;";
    private String chatMessageContentsDelim = ";";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);

        if (!preferences.getString(preferenceFileChatKey, "empty").equals("empty")) {
            restoreChat(preferences.getString(preferenceFileChatKey, "empty"));
            Toast.makeText(this, "Chat restored", Toast.LENGTH_SHORT).show();
        } else {
            loadDummyMessages();
        }

        mListView = (ListView)findViewById(R.id.message_listview);
        mAdapter = new ChatAdapter(this, R.layout.item_chat_outgoing, messages);
        mListView.setAdapter(mAdapter);
//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        userInput = (EditText)findViewById(R.id.edittext_chatbox);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    checkUserInput();
                    return true;
                } else {
                    return false;
                }
            }
        });

        sendButton = (ImageButton)findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInput();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                saveChat();
                return true;
            case R.id.menu_action_delete:
                deleteChat();
                return true;
            default:
                return false;
        }
    }

    private void restoreChat(String chat) {
        String[] chatMessages = chat.split(chatMessageDelim);
        for (String msg : chatMessages) {
            Log.i("****", msg);
            String[] contents = msg.split(chatMessageContentsDelim);
            Log.i("****", contents.toString());
            messages.add(new ChatMessage(contents[0], contents[1], Boolean.valueOf(contents[2])));
        }
    }

    /**
     * Helper method to validate the user has typed a valid messsage to send.
     */
    private void checkUserInput() {
        if (!userInput.getText().toString().trim().isEmpty()) {
            ChatMessage msg = new ChatMessage(userInput.getText().toString().trim(), myMessage);
            messages.add(msg);
            mAdapter.notifyDataSetChanged();
            myMessage = !myMessage;
            userInput.setText("");
        } else {
            Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to save the chat to the device for future lives of the app.
     */
    private void saveChat() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceFileChatKey, stringifyChat());
        editor.apply();
        Toast.makeText(this, "Chat saved!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to perform the necessary actions to delete the chat
     * from the device.
     */
    private void deleteChat() {
        removeSharedPrefs();
        messages.clear();
        loadDummyMessages();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "User generated chat removed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Custom logic to turn the chat into a parsable string
     * @return string representation of the entire chat
     */
    private String stringifyChat() {
        StringBuilder builder = new StringBuilder();
        for (ChatMessage message : messages) {
            builder.append(chatMessageDelim);
            builder.append(message.getContent()).append(chatMessageContentsDelim);
            builder.append(message.getTime()).append(chatMessageContentsDelim);
            builder.append(String.valueOf(message.isMine()));
        }

        return builder.toString().substring(chatMessageDelim.length());
    }

    /**
     * Helper method to remove the chat from the {@link SharedPreferences}
     * so we do not load chat messages the next time the app operns.
     */
    private void removeSharedPrefs() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(preferenceFileChatKey);
        editor.apply();
    }

    /**
     * To start the conversation
     */
    private void loadDummyMessages() {
        ChatMessage msg = new ChatMessage("Hello from a friend", false);
        ChatMessage msg2 = new ChatMessage("Hello friend!", true);
        ChatMessage msg3 = new ChatMessage("I am well, how are you?", false);

        messages.add(msg);
        messages.add(msg2);
        messages.add(msg3);
    }
}