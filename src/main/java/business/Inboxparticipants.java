package business;

import java.util.Objects;

public class Inboxparticipants {
    private int userId;
    private int inboxId;
    private int deletedState;
    private int newMessages;

    public Inboxparticipants(){

    }

    public Inboxparticipants(int userId, int inboxId, int deletedState, int newMessages) {
        this.userId = userId;
        this.inboxId = inboxId;
        this.deletedState = deletedState;
        this.newMessages = newMessages;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInboxId() {
        return inboxId;
    }

    public void setInboxId(int inboxId) {
        this.inboxId = inboxId;
    }

    public int getDeletedState() {
        return deletedState;
    }

    public void setDeletedState(int deletedState) {
        this.deletedState = deletedState;
    }

    public int getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(int newMessages) {
        this.newMessages = newMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inboxparticipants that = (Inboxparticipants) o;
        return userId == that.userId && inboxId == that.inboxId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, inboxId);
    }

    @Override
    public String toString() {
        return "Inboxparticipants{" +
                "userId=" + userId +
                ", inboxId=" + inboxId +
                ", deletedState=" + deletedState +
                ", newMessages=" + newMessages +
                '}';
    }
}
