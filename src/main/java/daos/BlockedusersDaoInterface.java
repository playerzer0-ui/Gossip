package daos;

import java.time.LocalDateTime;

public interface BlockedusersDaoInterface {

    /**
     * addBlockUser method let user able to block other user.
     *
     * @param userId is user's id who want to block other user
     * @param blockedId is other user's id who blocked by user
     *
     * @return an int if added block else added block fail will return -1
     */
    public int addBlockUser(int userId, int blockedId);

    /**
     * deleteBlockUser method able to delete the blocked user' id .
     *
     * @param blockedId is the blocked user's id
     *
     * @return an int after deleted else return 0 when no rows are affected by the deleted.
     */
    public int deleteBlockUser (int blockedId);

}
