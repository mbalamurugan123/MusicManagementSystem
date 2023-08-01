
import java.sql.*;
import java.util.*;



public class Main extends MusicManagement{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/music";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Bhamesh@123";
    
    public static boolean Login(){
        Scanner in=new Scanner(System.in);
        System.out.println("Login ");
        System.out.print("Email id : ");
        String email=in.nextLine();
        System.out.print("Password : ");
        String password=in.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM register WHERE email= ?"
                );
                
                preparedStatement.setString(1, email);
                 ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                String pass = resultSet.getString("password");
                if(pass.equals(password)){
                    System.out.println("Login Sucess\n");
                    return true;
                }
                else{
                    System.out.println("Incorrect password"+password);
                    
                    return false;
                }
            } else {
                System.out.println("Account not found...\n");
                return false;
            }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }
    
    
    public static void Register(){
        Scanner in=new Scanner(System.in);
        
        System.out.println("Registation");
        System.out.print("First Name : ");
        String firstname=in.nextLine();
        System.out.print("Last Name : ");
        String lastname=in.nextLine();
        System.out.print("Email Id : ");
        String email=in.nextLine();
        System.out.print("Contact : ");
        String contact=in.nextLine();
        System.out.print("Password : ");
        String password=in.nextLine();
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO register (firstname, lastname, email, contact, password) VALUES (?, ?, ?, ?, ?)"
                    );
                    
                    preparedStatement.setString(1, firstname);
                    preparedStatement.setString(2, lastname);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, contact);
                    preparedStatement.setString(5,password);
                    preparedStatement.executeUpdate();
                    System.out.println("Registered successfully.\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
            }
    public static void main(String[] args) throws Exception{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Scanner in=new Scanner(System.in);
                // MusicManagement manage=new MusicManagement();
                Main main=new Main();
                boolean auth=false;
                boolean use=false;
                int choice;
           

                do{
             
                    System.out.println("\n-------------------Music Management----------------------\n");

                    System.out.println("1. Login");
                    System.out.println("2. Create a account");
                    System.out.println("3. Exit");
                    System.out.print("Enter your Choice :");
                    choice=in.nextInt();
                    in.nextLine();
            
                switch(choice){
                case 1:
                auth=Login();
                if(auth==true){
                    main.main();
                    use=true;
                }
                break;
                case 2:
                Register();
                break;
                case 3:
                System.out.println("Exiting...");
                break;
                default:
                System.out.println("\nInvalid input! Please enter valid option.");
            }
       
        }while(choice!=3&&use==false);
        connection.close();

    }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}