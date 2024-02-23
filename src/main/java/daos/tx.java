package daos;

import business.InboxParticipants;

import java.time.LocalDateTime;

public class tx {
    public static void main(String[] args) {
        InboxParticipantsDao ibps = new InboxParticipantsDao("gossip");
        //System.out.println( ibps.getOtherInboxParticipant(1,1).toString());
        ReportsDao r = new ReportsDao("gossip");
        r.addReport(1,2, "kjnvjun", LocalDateTime.now(),1);
        String as = "<ion-icon class='return' onclick='seeChatList()' name='arrow-back-outline'></ion-icon> " +
                "<div class='userimg'>" +
                    "<img src='img/''some.png'' alt='profile' class='cover'> " +
                "</div>" +
                "<h4> ''otherUser.getUserName()'' <br><span></span></h4>" +
                "<div class='drop-menu-chat' id='drop-menu-chat'> " +
                    "<ul>  " +
                        "<a href='controller?action=block_user'> <li>block user</li> </a>  " +
                        "<a href='controller?action=showReport&reportedId=+otherUser.getUserId()+'> <li>report user</li>  </a>" +
                        "<a href='controller?action=leave_chat'><li>leave chat</li></a>" +
                    "</ul>   " +
                "</div>";
    }
}
