package edu.uw.tcss450.chatapp.ui.contact;

import java.util.Arrays;
import java.util.List;


public class ContactGenerator {
    /**contact initializer**/
    private static final Contact[] CONTACTS;
    /**sample counts**/
    public static final int COUNT = 10;
    //static sample generator for friend contact card
    static {
        CONTACTS = new Contact[COUNT];
        CONTACTS[0] = new Contact("aaaaa", "aaa", "aaa@fake.com", "aaaaaa",1);
        CONTACTS[1] = new Contact("bbbbb", "bbb", "bbb@fake.com", "bbbbbb",2);
        CONTACTS[2] = new Contact("ccccc", "ccc", "ccc@fake.com", "cccccc",3);
        CONTACTS[3] = new Contact("ddddd", "ddd", "ddd@fake.com", "dddddd",4);
        CONTACTS[4] = new Contact("eeeee", "eee", "eee@fake.com", "eeeeee",5);
        for (int i = 5; i < CONTACTS.length; i++) {
            //String mFName, String mLName, String mEmail, String mUserName, int memberID
            CONTACTS[i] = new Contact(i+ "first_name", i+"last_name", i + "email@email.com", i+ "username", i+10);
        }
    }

    /**
     *  Getter for contact list
     * @return return contacts array list
     */
    public static List<Contact> getContactList() {
        return Arrays.asList(CONTACTS);
    }

    /**
     * Getter for contact
     * @return array of contacts
     */
    public static Contact[] getCONTACTS() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    /**
     * Contact generator
     */
    public ContactGenerator() {}
}
