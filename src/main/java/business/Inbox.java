package business;

import java.util.Objects;

public class Inbox {
    private int inboxId;
    private int inboxType;
    private int adminId;
    private String groupName;
    private String groupProfilePicture;
    private String searchCategory;

    public Inbox(int inboxId, int inboxType, int adminId, String groupName) {
        this.inboxId = inboxId;
        this.inboxType = inboxType;
        this.adminId = adminId;
        this.groupName = groupName;
    }

    public Inbox(int inboxId, int inboxType, int adminId, String groupName, String groupProfilePicture, String searchCategory) {
        this.inboxId = inboxId;
        this.inboxType = inboxType;
        this.adminId = adminId;
        this.groupName = groupName;
        this.groupProfilePicture = groupProfilePicture;
        this.searchCategory = searchCategory;
    }

    public Inbox(int inboxId, int inboxType) {
        this.inboxId = inboxId;
        this.inboxType = inboxType;
    }
    public Inbox(int inboxId, int inboxType, int adminId, String groupName, String groupProfilePicture) {
        this.inboxId = inboxId;
        this.inboxType = inboxType;
        this.adminId = adminId;
        this.groupName = groupName;
        this.groupProfilePicture=groupProfilePicture;
    }
    public Inbox(){

    }
    public int getInboxId() {
        return inboxId;
    }

    public void setInboxId(int inboxId) {
        this.inboxId = inboxId;
    }

    public int getInboxType() {
        return inboxType;
    }

    public void setInboxType(int inboxType) {
        this.inboxType = inboxType;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupProfilePicture() {
        return groupProfilePicture;
    }

    public void setGroupProfilePicture(String groupProfilePicture) {
        this.groupProfilePicture = groupProfilePicture;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inbox inbox = (Inbox) o;
        return inboxId == inbox.inboxId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboxId);
    }

    @Override
    public String toString() {
        return "Inbox{" +
                "inboxId=" + inboxId +
                ", inboxType=" + inboxType +
                ", adminId=" + adminId +
                ", groupName='" + groupName + '\'' +
                ", groupProfilePicture='" + groupProfilePicture + '\'' +
                ", searchCategory='" + searchCategory + '\'' +
                '}';
    }

    public boolean updateInbox(Inbox inbox){
        if(   this.inboxId == inbox.inboxId) {
            this.inboxType = inbox.getInboxType();
            this.adminId = inbox.getAdminId();
            this.groupName = inbox.getGroupName();
            this.groupProfilePicture = inbox.getGroupProfilePicture();
            return true;
        }
        return false;
    }
}
