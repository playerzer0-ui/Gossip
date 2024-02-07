package daos;

import business.Users;

import java.time.LocalDate;

public interface UsersDaoInterface {

    public Users Login(String uemail, String pword);

    public int Register(String email, String uname, String pPicture, String pword, LocalDate dOBirth, int userType, int suspended, String bio, int online);

    public Users getUserById(int id);

    public int Register(Users newUser);
}
