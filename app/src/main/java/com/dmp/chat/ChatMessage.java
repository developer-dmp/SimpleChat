package com.dmp.chat;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * Class designed to facilitate OOP.
 *
 * @author Domenic Polidoro
 * @version 1.0
 */

class ChatMessage {

    private String time;
    private String content;
    private boolean isMine;
    //private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm a", Locale.US); // was too long for my liking

    ChatMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
        this.time = new SimpleDateFormat("HH:mm a", Locale.US).format(new Date());
    }

    ChatMessage(String content, String time, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
        this.time = time;
    }

    String getTime() {
        return time;
    }

    String getContent() {
        return content;
    }

    boolean isMine() {
        return isMine;
    }
}
