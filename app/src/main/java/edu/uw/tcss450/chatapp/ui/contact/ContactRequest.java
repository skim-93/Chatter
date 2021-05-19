package edu.uw.tcss450.chatapp.ui.contact;

public class ContactRequest {
    //String initializer for userName
    private final String mUserName;
    //Int initializer for userName
    private final int mMemberID;
    //Contact request constructor
    public ContactRequest(String username, int mMemberID) {
        this.mUserName = username;
        this.mMemberID = mMemberID;
    }

    //Getter for string user name
    public String getUsername() {
        return mUserName;
    }

    //Getter for int member ID
    public int getMemberID() {
        return mMemberID;
    }
}
