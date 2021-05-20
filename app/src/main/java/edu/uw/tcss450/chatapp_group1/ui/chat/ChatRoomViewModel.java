package edu.uw.tcss450.chatapp_group1.ui.chat;

import java.io.Serializable;

/**
 * View model for a chat room.
 */
public class ChatRoomViewModel implements Serializable {

    private int mChatId;
    private String mChatRoomName;

    // we store the chatid and chat room name in this view model.
    public ChatRoomViewModel(int chatid, String chatRoomName) {
        mChatId = chatid;
        mChatRoomName = chatRoomName;
    }

    public String getmChatRoomName() {
        return mChatRoomName;
    }

    public int getmChatId() {
        return mChatId;
    }
}
