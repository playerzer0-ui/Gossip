package miscellaneous;

import business.InboxParticipants;
import business.Search;
import business.Users;
import daos.InboxParticipantsDao;
import daos.ReportsDao;
import daos.UsersDao;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class tx {
    public static void main(String[] args) {
//        InboxParticipantsDao ibps = new InboxParticipantsDao("gossip");
//        //System.out.println( ibps.getOtherInboxParticipant(1,1).toString());
//        ReportsDao r = new ReportsDao("gossip");
//        r.addReport(1,2, "kjnvjun", LocalDateTime.now(),1);

       /* String fullPath = "C:\\Users\\jerik\\Documents\\codes\\JAVAAAAAAAAAAAAAAA\\Gossip\\target\\Gossip-1.0-SNAPSHOT\\";

        // Find the index of "Gossip-1.0-SNAPSHOT"
        int index = fullPath.indexOf("target");

        if (index != -1) {
            // Cut the string
            String resultPath = fullPath.substring(0, index);

            System.out.println(resultPath + "src\\main\\webapp\\");
        } else {
            System.out.println("Substring not found in the path.");
        }*/
       /* UsersDao d= new UsersDao("gossip");
        Users u = new Users();
        u.setUserId(1);
        List<Search> l=d.generalSearch("pau",u);
        for(Search s: l){
            System.out.println(s);
        }
        System.out.println(d.getUserById(1));*/
        System.out.println(Miscellaneous.checkEmail("sg@student.dkit.ie"));
        System.out.println(Miscellaneous.checkPassword("br fdb /*K95"));
    }
}
