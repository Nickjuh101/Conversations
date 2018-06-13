package eu.siacs.conversations;

import org.junit.Test;

import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.entities.Message;

import static org.junit.Assert.*;


public class MessagesTest {

    private Conversation conversation = new Conversation(
            "UUID",
            "Test Conversation",
            "Contact_UUID",
            "Account_UUID",
            null,
            101010,
            0,
            0,
            ""
    );

    private Message unreadMessage = new Message(
            conversation,
            "Test Message",
            0
    );

    @Test
    public void testUnreadMessage() {
        unreadMessage.markUnread();
        conversation.add(unreadMessage);
        assertEquals("Test Message", conversation.getFirstUnreadMessage().getBody());
    }

}
