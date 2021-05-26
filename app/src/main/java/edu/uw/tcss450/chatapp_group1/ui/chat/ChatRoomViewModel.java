package edu.uw.tcss450.chatapp_group1.ui.chat;

import java.io.Serializable;

/**
 * View model for a chat room. Holds data about a single chat room.
 *
 * @author Joseph
 */
public class ChatRoomViewModel implements Serializable {

    private int mChatId;
    private String mChatRoomName;

    // we store the chatid and chat room name in this view model.
    public ChatRoomViewModel(int chatid, String chatRoomName) {
        mChatId = chatid;
        mChatRoomName = chatRoomName;
    }

    /**
     * Get name of chat room.
     * @return name of chat room
     */
    public String getmChatRoomName() {
        return mChatRoomName;
    }

    /**
     * Get id of chat room.
     * @return chat id
     */
    public int getmChatId() {
        return mChatId;
    }
}
