package eu.siacs.conversations.xmpp;

import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.xmpp.stanzas.IqPacket;

public interface OnIqPacketReceived {
	void onIqPacketReceived(Account account, IqPacket packet);
}
