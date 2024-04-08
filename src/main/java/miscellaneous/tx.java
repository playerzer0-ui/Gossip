package miscellaneous;

import business.InboxParticipants;
import business.Search;
import business.Users;
import daos.InboxParticipantsDao;
import daos.ReportsDao;
import daos.UsersDao;
import org.mindrot.jbcrypt.BCrypt;

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
//        System.out.println(Miscellaneous.checkEmail("sg@student.dkit.ie"));
//        System.out.println(Miscellaneous.checkPassword("br fdb /*K95"));
      /*  if(BCrypt.checkpw("123", "$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa")){
            System.out.println("dsadsasad");
        }
        else{
            System.out.println("WRONG");
        }*/
        /*InboxParticipants ibp = new InboxParticipants(1,1,0,0,0,LocalDateTime.now());
        InboxParticipantsDao inboxParticipantsDao = new InboxParticipantsDao("gossip");
        inboxParticipantsDao.updateInboxParticipant(ibp);*/
        /*ArrayList <String> jdj= new ArrayList<>();
        jdj.add(0,"A");
        jdj.add(0,"B");
        System.out.println(jdj);*/
       InboxParticipantsDao inboxParticipantsDao = new InboxParticipantsDao("gossip");
       ArrayList <InboxParticipants> ibps= inboxParticipantsDao.getAllInbox(1);
        //System.out.println(ibps);
        /*for(InboxParticipants ibp: ibps){
            System.out.println(ibp);
        }*/
        ArrayList <Integer> nums= new ArrayList();
        nums.add(2);
        nums.add(0,1);
      //  nums.add(0,-1);
        System.out.println(nums);
    }
}
