package edu.uw.tcss450.chatapp_group1.ui.contact;

import java.util.Arrays;
import java.util.List;

public class ContactRequestGenerator {
    //contact request initializer
    private static final ContactRequest[] CONTACTS;
    //set up sample size
    public static final int COUNT = 10;
    //set up sample request contact list
    static {
        CONTACTS = new ContactRequest[COUNT];
        CONTACTS[0] = new ContactRequest("aaaaa",14);
        CONTACTS[1] = new ContactRequest("bbbbb", 16);
        CONTACTS[2] = new ContactRequest("ccccc",20);
        CONTACTS[3] = new ContactRequest("ddddd",45);
        CONTACTS[4] = new ContactRequest("eeeee",101);
        for (int i = 5; i < CONTACTS.length; i++) {
            //String userName, int MemberID
            CONTACTS[i] = new ContactRequest(i+"user_name", i+30);
        }
    }

    /**
     * Getter for request contact list
     * @return return with array list of request contacts
     */
    public static List<ContactRequest> getContactList() {
            return Arrays.asList(CONTACTS);
        }
    /**
     * Getter for request contact
     * @return array of request contacts and length
     */
    public static ContactRequest[] getCONTACTS() {return Arrays.copyOf(CONTACTS, CONTACTS.length);}

    /**
     * contact request generator
     */
    public ContactRequestGenerator() { }
    }