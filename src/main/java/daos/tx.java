package daos;

import business.InboxParticipants;

public class tx {
    public static void main(String[] args) {
        InboxParticipantsDao ibps = new InboxParticipantsDao("gossip");
        System.out.println( ibps.getOtherInboxParticipant(1,1).toString());
    }
}
