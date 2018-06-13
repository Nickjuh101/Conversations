package eu.siacs.conversations;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.IOException;

import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.services.XmppConnectionService;
import eu.siacs.conversations.xmpp.pep.Avatar;

import static org.junit.Assert.*;


public class AccountTest {

    @Mock
    private XmppConnectionService mXmppConnectionService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testAvatar() throws IOException {
        Account account = new Account(
                null,
                "Password"
        );
        Avatar avatar = new Avatar();
        File tempImage = File.createTempFile( "image", ".png");
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
        assertEquals(avatar.getFilename(), account.getAvatar());
    }

}
