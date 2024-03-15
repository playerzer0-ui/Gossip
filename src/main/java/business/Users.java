package business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Users {
//  `userId` int(11) NOT NULL,
//  `email` varchar(255) NOT NULL,
//  `userName` varchar(24) NOT NULL,
//  `profilePicture` varchar(80) NOT NULL DEFAULT 'default.png',
//  `password` varchar(80) NOT NULL,
//  `dateOfBirth` datetime NOT NULL,
//  `userType` int(1) NOT NULL,
//  `suspended` tinyint(1) NOT NULL DEFAULT 0,
//  `bio` varchar(25) DEFAULT NULL,
//  `online` tinyint(1) NOT NULL DEFAULT 0

    private int userId;

    private String email;

    private String userName;

    private String profilePicture;

    private String password;

    private LocalDate dateOfBirth;

    private int userType;

    private int suspended;

    private String bio;

    private int online;
    private String searchCategory;
    public Users() {
    }

    //Users all
    public Users(int userId, String email, String userName, String profilePicture, String password, LocalDate dateOfBirth, int userType, int suspended, String bio, int online) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.suspended = suspended;
        this.bio = bio;
        this.online = online;
    }

    //Users without userID
    public Users(String email, String userName, String profilePicture, String password, LocalDate dateOfBirth, int userType, int suspended, String bio, int online) {
        this.email = email;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.suspended = suspended;
        this.bio = bio;
        this.online = online;
    }

    public Users(int userId, String email, String userName, String profilePicture, String password, LocalDate dateOfBirth, int userType, int suspended, String bio, int online, String searchCategory) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.suspended = suspended;
        this.bio = bio;
        this.online = online;
        this.searchCategory = searchCategory;
    }

    public Users(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getSuspended() {
        return suspended;
    }

    public void setSuspended(int suspended) {
        this.suspended = suspended;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
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
        Users users = (Users) o;
        return userId == users.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", userType=" + userType +
                ", suspended=" + suspended +
                ", bio='" + bio + '\'' +
                ", online=" + online +
                ", searchCategory='" + searchCategory + '\'' +
                '}';
    }

    /*public void updateUsers(String email, String userName, String profilePicture, String password, LocalDate dateOfBirth, int userType, int suspended, String bio, int online) {
        this.email = email;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.suspended = suspended;
        this.bio = bio;
        this.online = online;
    }*/
}
