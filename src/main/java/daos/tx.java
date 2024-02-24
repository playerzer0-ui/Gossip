package daos;

import business.InboxParticipants;

import java.nio.file.Paths;
import java.time.LocalDateTime;

public class tx {
    public static void main(String[] args) {
//        InboxParticipantsDao ibps = new InboxParticipantsDao("gossip");
//        //System.out.println( ibps.getOtherInboxParticipant(1,1).toString());
//        ReportsDao r = new ReportsDao("gossip");
//        r.addReport(1,2, "kjnvjun", LocalDateTime.now(),1);

        String up = String.valueOf(Paths.get("src/main/java/controller/Controller.java"));
        System.out.println(up);
    }
}
