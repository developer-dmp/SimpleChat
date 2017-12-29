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
 * Implementation of {@link ArrayAdapter} to provide the
 * view for the {@link android.widget.ListView} in the MainActivity.
 *
 * @author Domenic Polidoro
 * @version 1.0
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private LayoutInflater inflater;

    public ChatAdapter(Context context, int resource, List<ChatMessage> messageList) {
        super (context, resource, messageList);
        inflater = LayoutInflater.from(context);
    }

    /**
     * Invoked by the OS to draw each row of the {@link android.widget.ListView}.
     * The message sender is determined by a boolean flag in my {@link ChatMessage}
     * @param position the location in the {@link android.widget.ListView}
     * @param convertView recycled view that we can modify
     * @param parent parent to the convertView
     * @return {@link View} to be displayed in this specific row (position)
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        ChatMessage message = getItem(position);

        // determine who sent the message
        int layoutResource = message.isMine() ? R.layout.item_chat_outgoing : R.layout.item_chat_incoming;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        // add the appropriate information
        holder.msg.setText(message.getContent());
        holder.time.setText(message.getTime());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types.
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    /**
     * Class used to associate UI components to each
     * inflation of the two possible XML layout files.
     */
    private class ViewHolder {
        private TextView msg;
        private TextView time;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            time = (TextView) v.findViewById(R.id.timeTextView);
        }
    }
}
