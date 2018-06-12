package eu.siacs.conversations;

import android.net.Uri;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.IOException;

import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.services.XmppConnectionService;


public class SendTest {
    @Mock
    private XmppConnectionService mXmppConnectionService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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

    @Test
    public void testSendMedia() throws IOException {
        File image = File.createTempFile( "image", ".png");
        mXmppConnectionService.attachImageToConversation(
                conversation,
                Uri.parse(image.toURI().toString()),
                null
        );
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).attachImageToConversation(
                conversation,
                Uri.parse(image.toURI().toString()),
                null
        );
    }

    @Test
    public void testShareLocation() {
        String location = "geo:12.3456,-123.4567";
        mXmppConnectionService.attachLocationToConversation(
                conversation,
                Uri.parse(location),
                null
        );
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).attachLocationToConversation(
                conversation,
                Uri.parse(location),
                null
        );
    }

    @Test
    public void testSendVoiceMessages() throws IOException {
        File voice = File.createTempFile( "voice", ".mp3");
        mXmppConnectionService.attachFileToConversation(
                conversation,
                Uri.parse(voice.toURI().toString()),
                null, // Process As File
                null
        );
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).attachFileToConversation(
                conversation,
                Uri.parse(voice.toURI().toString()),
                null, // Process As File
                null
        );
    }

}
