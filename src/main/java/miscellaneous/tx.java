package miscellaneous;

import business.InboxParticipants;
import daos.InboxParticipantsDao;
import daos.ReportsDao;

import java.time.LocalDateTime;

public class tx {
    public static void main(String[] args) {
        InboxParticipantsDao ibps = new InboxParticipantsDao("gossip");
        //System.out.println( ibps.getOtherInboxParticipant(1,1).toString());
        ReportsDao r = new ReportsDao("gossip");
        r.addReport(1,2, "kjnvjun", LocalDateTime.now(),1);
    }
}