package edu.uw.tcss450.chatapp_group1.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * View model to store data about new message notification counts.
 */
public class NewMessageCountViewModel extends ViewModel {
    /**
     * Map to store {chat room id, new message count}.
     */
    private MutableLiveData<Map<Integer, Integer>> mNewMessageCount;

    /**
     * Integer to store count of total new messages.
     */
    private MutableLiveData<Integer> mTotalMessageCount;

    /**
     * Constructor.
     */
    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mNewMessageCount.setValue(new HashMap<>());
        mTotalMessageCount = new MutableLiveData<>();
        mTotalMessageCount.setValue(0);
    }

    /**
     * Add an observer to this instance
     * @param owner the owner
     * @param observer the observer
     */
    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mTotalMessageCount.observe(owner, observer);
    }

    /**
     * Increment the total new message number.
     * @param chatId the id of the specified chat room
     */
    public void increment(int chatId) {
        // if message count key exists for that chat room, just increment it
        if (mNewMessageCount.getValue().containsKey(chatId)) {
            int currentMessageCount = mNewMessageCount.getValue().get(chatId);
            int newMessageCount = currentMessageCount + 1;
            mNewMessageCount.getValue().put(chatId, newMessageCount);
        } else {
            // message count key doesn't exist in map, create it
            mNewMessageCount.getValue().put(chatId, 1);
        }
        // increment total message badge count
        mTotalMessageCount.setValue(mTotalMessageCount.getValue() + 1);
    }

    /**
     * Change the total new message number to its new value after entering a chat room.
     * @param chatId the id of the specified chat room
     */
    public void reset(int chatId) {
        if (mNewMessageCount.getValue().containsKey(chatId)) {
            // get current message count for that chat room
            int currentMessageCount = mNewMessageCount.getValue().get(chatId);
            System.out.println("Current message count for chatid: " + chatId + " is " + currentMessageCount);
            if (currentMessageCount > 0) {
                // reset that message count to zero, and subtract that count from the total message badge count
                mNewMessageCount.getValue().put(chatId, 0);
                mTotalMessageCount.setValue(mTotalMessageCount.getValue() - currentMessageCount);
                System.out.println("New total message count is: " + mTotalMessageCount.getValue());
            }
        }
    }
}
