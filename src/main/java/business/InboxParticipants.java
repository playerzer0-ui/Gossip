package business;

import java.util.Objects;

public class InboxParticipants {
    private int userId;
    private int inboxId;
    private int deletedState;
    private int unseenMessages;
    private int isOpen;

    public InboxParticipants(int userId, int inboxId, int deletedState, int unseenMessages, int isOpen) {
        this.userId = userId;
        this.inboxId = inboxId;
        this.deletedState = deletedState;
        this.unseenMessages = unseenMessages;
        this.isOpen = isOpen;
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

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboxParticipants that = (InboxParticipants) o;
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
                ", unseenMessages=" + unseenMessages +
                ", isOpen=" + isOpen +
                '}';
    }
}
