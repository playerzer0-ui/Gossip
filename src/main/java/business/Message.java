package business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
private int messageId;
private int inboxId;
private int senderId;
private String message;
private int messageType;
private LocalDateTime timeSent;
private int deletedState;
private int messageKey;
private String originalFileName;
public Message() {

}

    public Message(int messageId, int inboxId, int senderId, String message, int messageType, LocalDateTime timeSent, int deletedState) {
        this.messageId = messageId;
        this.inboxId = inboxId;
        this.senderId = senderId;
        this.message = message;
        this.messageType = messageType;
        this.timeSent = timeSent;
        this.deletedState = deletedState;
    }

    public Message(int messageId, int inboxId, int senderId, String message, int messageType, LocalDateTime timeSent, int deletedState, int messageKey) {
        this.messageId = messageId;
        this.inboxId = inboxId;
        this.senderId = senderId;
        this.message = message;
        this.messageType = messageType;
        this.timeSent = timeSent;
        this.deletedState = deletedState;
        this.messageKey = messageKey;
    }

    public Message(int messageId, int inboxId, int senderId, String message, int messageType, LocalDateTime timeSent, int deletedState, int messageKey, String originalFileName) {
        this.messageId = messageId;
        this.inboxId = inboxId;
        this.senderId = senderId;
        this.message = message;
        this.messageType = messageType;
        this.timeSent = timeSent;
        this.deletedState = deletedState;
        this.messageKey = messageKey;
        this.originalFileName = originalFileName;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getInboxId() {
        return inboxId;
    }

    public void setInboxId(int inboxId) {
        this.inboxId = inboxId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    public String displayTimeAMPM(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timeSent.format(formatter);
    }

    public int getDeletedState() {
        return deletedState;
    }

    public void setDeletedState(int deletedState) {
        this.deletedState = deletedState;
    }

    public int getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(int messageKey) {
        this.messageKey = messageKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageId == message.messageId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", inboxId=" + inboxId +
                ", senderId=" + senderId +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                ", timeSent=" + timeSent +
                ", deletedState=" + deletedState +
                ", messageKey=" + messageKey +
                ", originalFileName='" + originalFileName + '\'' +
                '}';
    }
}
