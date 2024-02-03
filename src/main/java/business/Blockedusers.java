package business;

import java.util.Objects;

public class Blockedusers {
//    `userId` int(11) NOT NULL COMMENT 'the user that did the blocking',
//    `blockedId` int(11) NOT NULL COMMENT 'the user that was blocked',
//    KEY `userId` (`userId`),
//    KEY `blockedId` (`blockedId`)

    private int userId;
    private int blockedId;

    public Blockedusers() {
    }

    public Blockedusers(int userId, int blockedId) {
        this.userId = userId;
        this.blockedId = blockedId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlockedId() {
        return blockedId;
    }

    public void setBlockedId(int blockedId) {
        this.blockedId = blockedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blockedusers that = (Blockedusers) o;
        return userId == that.userId && blockedId == that.blockedId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, blockedId);
    }

    @Override
    public String toString() {
        return "Blockedusers{" +
                "userId=" + userId +
                ", blockedId=" + blockedId +
                '}';
    }
}
