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

        String fullPath = "C:\\Users\\jerik\\Documents\\codes\\JAVAAAAAAAAAAAAAAA\\Gossip\\target\\Gossip-1.0-SNAPSHOT\\";

        // Find the index of "Gossip-1.0-SNAPSHOT"
        int index = fullPath.indexOf("target");

        if (index != -1) {
            // Cut the string
            String resultPath = fullPath.substring(0, index);

            System.out.println(resultPath + "src\\main\\webapp\\");
        } else {
            System.out.println("Substring not found in the path.");
        }
    }
}
