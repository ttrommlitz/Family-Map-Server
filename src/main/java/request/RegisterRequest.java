package request;

/** Contains the HTTP POST Request
 * for registering a new User **/
public class RegisterRequest {
    /** Username of new User **/
    private String username;

    /** Password of new User **/
    private String password;

    /** Email of new User **/
    private String email;

    /** First name of new User **/
    private String firstName;

    /** Last name of new User **/
    private String lastName;

    /** Gender of new User **/
    private String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
