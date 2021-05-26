package edu.uw.tcss450.chatapp_group1.ui.chat;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentChatListCardBinding;

/**
 * Recycler view adapter class for holding a chat list.
 *
 * @author Joseph
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    /**
     * Fields to store states/data.
     */
    private List<ChatRoomViewModel> mChatRoomViewModels;
    private final ChatListFragment mParent;

    /**
     * Constructor.
     * @param chats chats list
     * @param parent parent fragment
     */
    public ChatListRecyclerViewAdapter(List<ChatRoomViewModel> chats, ChatListFragment parent) {
        this.mChatRoomViewModels = chats;
        this.mParent = parent;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_chat_list_card, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        try {
            holder.setChatRoom(mChatRoomViewModels.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // inner class acting as a chat list holder
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ChatRoomViewModel mChatRoomViewModel;
        private final View mView;
        private FragmentChatListCardBinding binding;

        public ChatListViewHolder(View v) {
            super(v);
            mView = v;
            nameTextView = v.findViewById(R.id.text_chatRoom_title);
            binding = FragmentChatListCardBinding.bind(mView);

        }

        /**
         * Setting a chat room by initializing buttons for deleting chat rooms, and entering a chat room.
         * @param chatRoomViewModel the view model for a chat room
         * @throws JSONException
         */
        void setChatRoom(final ChatRoomViewModel chatRoomViewModel) throws JSONException {
            mChatRoomViewModel = chatRoomViewModel;
            nameTextView.setText(mChatRoomViewModel.getmChatRoomName());

            binding.buttonDelete.setOnClickListener(button -> {
                mChatRoomViewModels.remove(mChatRoomViewModel);
                mParent.deleteChat(mChatRoomViewModel.getmChatId());
                notifyDataSetChanged();
            });

            binding.buttonEnter.setOnClickListener(view ->
                    Navigation.findNavController(mView)
                            .navigate(ChatListFragmentDirections
                                    .actionChatListFragmentToChatRoomFragment(mChatRoomViewModel.getmChatId(), mChatRoomViewModel.getmChatRoomName())));
        }
    }

    /**
     * Setting chat rooms
     * @param rooms list
     */
    public void setChatRooms(List<ChatRoomViewModel> rooms){
        mChatRoomViewModels = rooms;
        notifyDataSetChanged();
    }

    /**
     * Get count of chat rooms.
     */
    @Override
    public int getItemCount() {
        return mChatRoomViewModels.size();
    }
}
