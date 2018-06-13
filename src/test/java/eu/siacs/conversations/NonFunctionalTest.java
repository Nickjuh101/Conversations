package eu.siacs.conversations;

import android.app.Application;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.entities.Message;
import eu.siacs.conversations.services.XmppConnectionService;

import static eu.siacs.conversations.entities.Message.STATUS_SEND_FAILED;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class NonFunctionalTest extends Application {

    @Mock
    private TextView mTextView;

    @Mock
    private XmppConnectionService mXmppConnectionService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
       when(mTextView.getTextSize()).thenReturn((float) 11);
    }


    @Test
    public void testMinimalSDK() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("build.gradle");
        int bytesRead = 0;
        byte[] contents = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while((bytesRead = stream.read(contents)) != -1) {
            stringBuilder.append(new String(contents, 0, bytesRead));
        }
        String str = stringBuilder.toString();

        String minSDKVersion = str.substring(
                str.indexOf("minSdkVersion ") + 14,
                str.indexOf("targetSdkVersion")
        ).replace("\n", "").replace(" ", "");
        assertTrue(Integer.parseInt(minSDKVersion) >= 19);
    }

    @Test
    public void testSendMessageSpeed() {
        long startTime = System.currentTimeMillis();
        Conversation conversation = new Conversation(
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
        Message message = new Message(
                conversation,
                "Test Message",
                1
        );
        mXmppConnectionService.sendMessage(message);
        conversation.add(message);
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).sendMessage(
                message
        );
        long finishTime = System.currentTimeMillis();
        assertTrue((finishTime - startTime) < 500);
    }

    @Test
    public void testFontSize() {
        assertTrue(mTextView.getTextSize() >= 11);
    }

    @Test
    public void testXMPPServiceExists() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("AndroidManifest.xml");
        int bytesRead = 0;
        byte[] contents = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while((bytesRead = stream.read(contents)) != -1) {
            stringBuilder.append(new String(contents, 0, bytesRead));
        }
        String str = stringBuilder.toString();
        assertTrue(str.contains(".services.XmppConnectionService"));
    }

    @Test
    public void testOMEMOAndOpenPGP() {
        Boolean OMEMO = false;
        Boolean OpenPGP = false;
        for (Method method : mXmppConnectionService.getClass().getMethods()) {
            String name = method.getName();
            if (name.equals("getMemorizingTrustManager")) OMEMO = true;
            if (name.equals("getOpenPgpApi")) OpenPGP = true;
        }
        assertTrue(OMEMO && OpenPGP);
    }

    @Test
    public void testMessageDraftResend() {
        Conversation conversation = new Conversation(
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
        conversation.setDraftMessage("Draft Message");
        Message message = new Message(
                conversation,
                conversation.getDraftMessage(),
                1
        );
        mXmppConnectionService.sendMessage(message);
        message.setStatus(STATUS_SEND_FAILED);
        mXmppConnectionService.resendFailedMessages(message);
        conversation.add(message);
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).sendMessage(
                message
        );
        Mockito.verify(mXmppConnectionService,
                Mockito.times(1)).resendFailedMessages(
                message
        );
        assertEquals("Draft Message", conversation.getLatestMessage().getBody());
    }

}
