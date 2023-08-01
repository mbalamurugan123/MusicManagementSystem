public abstract class UserDetails {
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String password;

    public UserDetails(String firstname, String lastname, String email, String contact, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getEmail(){
        return email;
    }
    public String getContact(){
        return contact;
    }
    public String getPassword(){
        return password;
    }

    public abstract void gUser();

}
class User extends UserDetails{
    public User(String firstname,String lastname,String email,String contact,String password){
        super(firstname, lastname, email, contact, password);
    }

    public void gUser() {
        System.out.println("Name :"+getFirstname()+" "+getLastname()+"\nEmail id :"+getEmail()+"\nContact :"+getContact()+"\nPassword :"+getPassword());
    }
}