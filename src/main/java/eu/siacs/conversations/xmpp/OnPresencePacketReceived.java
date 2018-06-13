package eu.siacs.conversations.xmpp;

import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.xmpp.stanzas.PresencePacket;

public interface OnPresencePacketReceived {
	void onPresencePacketReceived(Account account, PresencePacket packet);
}
