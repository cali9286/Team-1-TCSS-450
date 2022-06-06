package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import java.util.Arrays;
import java.util.List;

public class ChatGenerator {

    private static final MessagesFragment[] CHATS;
    public static final int COUNT = 6;

    static {
        CHATS = new MessagesFragment[COUNT];
        for (int i = 0; i < CHATS.length; i++) {
            CHATS[i] = MessagesFragment.newInstance(i + 1);
        }
    }

    public static List<MessagesFragment> getChat() {return Arrays.asList(CHATS);}

    public static MessagesFragment[] getCHATS() { return Arrays.copyOf(CHATS, CHATS.length); }

    private ChatGenerator() { }

}
