package edu.uw.tcss450.chatapp_group1.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatapp_group1.MainActivity;
import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * This class is the fragment for a chat room users can send and receive messages in.
 *
 * @author Joseph
 */
public class ChatRoomFragment extends Fragment {

    /**
     * Fields to store states/data.
     */
    private int mChatID;
    private String mChatTitle;
    private ChatSendViewModel mSendModel;
    private ChatViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    /**
     * Constructor.
     */
    public ChatRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mSendModel = provider.get(ChatSendViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the arguments from chat room xml
        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        mChatID = args.getChatid();
        mChatTitle = args.getChattitle();
        ((MainActivity) getActivity()).setActionBarTitle(mChatTitle);
        mChatModel.getFirstMessages(mChatID, mUserModel.getmJwt());
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());

        // Show this until messages fully load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;

        // Setting the adapter to hold a list for this specific chat id
        rv.setAdapter(new ChatRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(mChatID),
                mUserModel.getEmail()));

        // Enable list refreshing when scrolling to the top of the recycler view.
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(mChatID, mUserModel.getmJwt());
        });

        // add an observer for messages
        mChatModel.addMessageObserver(mChatID, getViewLifecycleOwner(),
                list -> {
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        // Send the message via ChatSendViewModel.
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(mChatID,
                    mUserModel.getmJwt(),
                    binding.editMessage.getText().toString());
        });

        // setting the button to redirect to contacts
        binding.buttonAdd.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatRoomFragmentDirections.actionChatRoomFragmentToNavigationContact(mChatID, true)
                ));

        // After a response is retrieved, reset the text in the editMessage field.
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editMessage.setText(""));
    }
}