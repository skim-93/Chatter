package edu.uw.tcss450.chatapp_group1.ui.contact;

import java.io.Serializable;
public class Contact implements Serializable {
    /** string initializer for firstname**/
    private final String mFirstName;
    /** string initializer for lastname**/
    private final String mLastName;
    /** string initializer for email**/
    private final String mEmail;
    /** string initializer for username**/
    private final String mUserName;
    /** int initializer for member ID**/
    private final int mMemberID;

    /**
     * Contact constructor
     * @param firstName Contact first name
     * @param lastName Contact last Name
     * @param email Conatact email
     * @param userName Contact user Name
     * @param memberID Contact memberID
     */
    public Contact(String firstName, String lastName, String email, String userName, int memberID) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mUserName = userName;
        this.mMemberID = memberID;
    }

    /**
     * Getter for first name
     * @return return with contact's first name
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Getter for last name
     * @return return with contact's last name
     */
    public String getLastName() { return mLastName; }

    /**
     * Getter for email
     * @return return with contact's email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Getter for user name
     * @return return with contact user's name
     */
    public String getUserName() { return mUserName; }

    /**
     * Getter for member ID
     * @return return with contact member's ID
     */
    public int getmMemberID() {return mMemberID;}


}