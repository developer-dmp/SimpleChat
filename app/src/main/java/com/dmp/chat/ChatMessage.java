package com.dmp.chat;

import java.text.SimpleDateFormat;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Locale;

/**
 * Created by rtd1p on 12/28/2017.
 */

public class ChatMessage {

    private String time;
    private String content;
    private boolean isMine;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.US);
    //private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm a", Locale.US);

    public ChatMessage(@NonNull String content, @NonNull boolean isMine) {
        this.content = content;
        this.isMine = isMine;
        this.time = sdf.format(new Date());
    }

    public ChatMessage(@NonNull String content, @NonNull String time, @NonNull boolean isMine) {
        this.content = content;
        this.isMine = isMine;
        this.time = time;
    }

    public String getTime() {return time;}

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
