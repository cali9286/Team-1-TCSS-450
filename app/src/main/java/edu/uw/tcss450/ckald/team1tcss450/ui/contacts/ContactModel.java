package edu.uw.tcss450.ckald.team1tcss450.ui.contacts;

import java.io.Serializable;

public class ContactModel  implements Serializable {
    String email;

    public ContactModel(String email) { this.email = email; }

    public String getEmail() {
        return email;
    }

}
