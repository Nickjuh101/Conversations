package eu.siacs.conversations;

import static eu.siacs.conversations.entities.Conversational.MODE_MULTI;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.services.XmppConnectionService;


public class GroupConversationTest {

    @Mock
    private XmppConnectionService mXmppConnectionService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        when(mXmppConnectionService.findOrCreateConversation(
                any(),
                any(),
                anyBoolean(),
                anyBoolean())).thenReturn(Mockito.mock(Conversation.class));
    }

    @Test
    public void testCreateGroupConversation() {
        Conversation conversation = mXmppConnectionService.findOrCreateConversation(
                null,
                null,
                true,
                true);
        when(conversation.getMode()).thenReturn(MODE_MULTI);
        assertEquals(MODE_MULTI, conversation.getMode());
    }


}
