package eu.siacs.conversations.xmpp.jingle;

import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.xmpp.jingle.stanzas.JinglePacket;

public interface OnJinglePacketReceived {
	void onJinglePacketReceived(Account account, JinglePacket packet);
}
