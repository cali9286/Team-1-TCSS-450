package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import java.io.Serializable;

public class Chat implements Serializable {

    private String mChatID;

    public static class Builder {

        private final String mChatID;

        public Builder(String ChatID) {

            this.mChatID = ChatID;

        }
        public Chat build() { return new Chat(this);}
    }



    private Chat(final Builder builder) {
        this.mChatID = builder.mChatID;
    }

    public String getChatID() { return mChatID; }
}
