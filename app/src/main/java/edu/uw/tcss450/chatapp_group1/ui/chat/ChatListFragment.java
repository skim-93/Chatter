package edu.uw.tcss450.chatapp_group1.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentChatListBinding;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * Fragment class for listing chat rooms that the current user is associated with.
 */
public class ChatListFragment extends Fragment {

    /**
     * These fields store states and data into view models.
     */
    private ChatListViewModel mChatListModel;
    private UserInfoViewModel mUserInfoViewModel;
    private FragmentChatListBinding binding;
    private ChatListRecyclerViewAdapter chatListRecyclerViewAdapter;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatListModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mChatListModel.connectGet(mUserInfoViewModel.getmJwt());
        mChatListModel.setUserInfoViewModel(mUserInfoViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    /**
     * Setting listeners to buttons such as adding a chat, and also a chat list observer to listen for changes.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentChatListBinding.bind(getView());

        binding.buttonAddChat.setOnClickListener(button -> {
            String title = binding.textChatTitle.getText().toString().trim();
            if (title.length() < 1){
                binding.textChatTitle.setError("Please enter a valid chat room name.");
            } else {
                mChatListModel.addChat(mUserInfoViewModel.getmJwt(), title);
            }
        });

        chatListRecyclerViewAdapter = new ChatListRecyclerViewAdapter(new ArrayList<>(), this);

        binding.listChatRoot.setAdapter(chatListRecyclerViewAdapter);
        mChatListModel.addChatListObserver(getViewLifecycleOwner(), chatRoomList -> {
            chatListRecyclerViewAdapter.setChatRooms(chatRoomList);
        });
    }

    /**
     * To delete a chat room.
     * @param chatId the chat room id
     */
    public void deleteChat(final int chatId) {
        mChatListModel.deleteChat(chatId);
    }
}
