package com.dmp.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rtd1p on 12/28/2017.
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private LayoutInflater inflater;
    private List<ChatMessage> messageList;

    public ChatAdapter(Context context, int resource, List<ChatMessage> messageList) {
        super (context, resource, messageList);
        this.messageList = messageList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        ChatMessage message = getItem(position);

        int layoutResource = message.isMine() ? R.layout.item_chat_outgoing : R.layout.item_chat_incoming;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.msg.setText(message.getContent());
        holder.time.setText(message.getTime());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }


    private class ViewHolder {
        private TextView msg;
        private TextView time;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            time = (TextView) v.findViewById(R.id.timeTextView);
        }
    }
}
