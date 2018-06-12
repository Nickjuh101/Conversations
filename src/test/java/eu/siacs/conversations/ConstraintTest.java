package eu.siacs.conversations;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.entities.MucOptions;
import eu.siacs.conversations.services.XmppConnectionService;
import eu.siacs.conversations.xmpp.pep.Avatar;
import static org.mockito.Mockito.when;


public class ConstraintTest {

    @Mock
    private Conversation mConversation;

    @Mock
    private XmppConnectionService mXmppConnectionService;

    @Mock
    private MucOptions mMucOptions;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        when(mConversation.getMucOptions()).thenReturn(mMucOptions);
        when(mMucOptions.getUserCount()).thenReturn(26);
    }


    @Test
    public void testAvatar() throws IOException {
        Account account = new Account(
                null,
                "Password"
        );
        Avatar avatar = new Avatar();
        File tempImage = File.createTempFile( "image", ".png");
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempImage));
        for (int n = 0; n < 2000000; n++)
            bw.write("0");
        bw.close();
        avatar.image = tempImage.getAbsolutePath();
        mXmppConnectionService.publishAvatar(
                account,
                avatar,
                null
        );
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).publishAvatar(
                account,
                avatar,
                null
        );
        assertTrue(tempImage.length() <= avatar.getMaxAvatarSize());
    }

    @Test
    public void testConferenceSize() {
        int MAX_CONFERENCE_MEMBERS = 25; // Cooking
        assertFalse(mConversation.getMucOptions().getUserCount() < MAX_CONFERENCE_MEMBERS);
    }

    @Test
    public void testXMPPProtocolExist() {
        try {
            Class.forName("eu.siacs.conversations.services.XmppConnectionService");
        } catch (ClassNotFoundException e) {
            Assert.fail("Should have a class called XmppConnectionService");
        }
    }


}
