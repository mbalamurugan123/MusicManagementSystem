import java.io.IOException;
import java.sql.*;
import java.util.*;

class Encapsule{
  private String title;
  private String artist;
  private String duration;
  private String path;
  public void setTitle(String title){
    this.title=title;
  }
  public String getTitle(){
    return title;
  }
  public void setArtist(String artist){
    this.artist=artist;
  }
  public String getArtist(){
    return artist;
  }
  public void setDuration(String duration){
    this.duration=duration;
  }
  public String getDuration(){
    return duration;
  }
  public void setPath(String path){
    this.path=path;
  }
  public String getPath(){
    return path;
  }
}
class MusicManagement {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/music";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Bhamesh@123";
    Scanner in = new Scanner(System.in);
    
    
	public static void addSong() {
    Encapsule encapsule=new Encapsule();
        Scanner in=new Scanner(System.in);
        System.out.print("Song Name : ");
        encapsule.setTitle(in.nextLine());
        System.out.print("Artist Name : ");
        encapsule.setArtist(in.nextLine());
        System.out.print("Duration of song : ");
        encapsule.setDuration(in.nextLine());
        System.out.print("Path of the song : ");
        encapsule.setPath(in.nextLine());
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO songs (title, artist, duration, path) VALUES (?, ?, ?, ?)"
                );
                
                preparedStatement.setString(1, encapsule.getTitle());
                preparedStatement.setString(2, encapsule.getArtist());
            preparedStatement.setString(3, encapsule.getDuration());
            preparedStatement.setString(4, encapsule.getPath());
            preparedStatement.executeUpdate();
            System.out.println("Song added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
    }
    public static void ShowSongs(){
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement preparedStatement=connection.prepareStatement(
                "SELECT * FROM songs"
                );
            ResultSet set=preparedStatement.executeQuery();
            System.out.println("List of Songs");
            while(set.next()){
                System.out.println(set.getInt("id")+". "+set.getString("title"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    public static void playSong() {
        Scanner in = new Scanner(System.in);
        ShowSongs();
        System.out.print("Enter the song ID to play: ");
        int songId = in.nextInt();
        in.nextLine();
        
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM songs WHERE id = ?"
                    );

                    preparedStatement.setInt(1, songId);
                    
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String title =resultSet.getString("title");
                        String artist=resultSet.getString("artist");
                        String duration=resultSet.getString("duration");
                        System.out.print("\n\nPlaying Song...\nTitle : "+title+"\nArtist : "+artist+"\nDuration : "+duration);
                String path = resultSet.getString("path");
                play(path);
            } else {
                System.out.println("Song not found with ID: " + songId);
            }
            
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public static void play(String path) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("afplay", path);
            Process process = processBuilder.start();
            
            
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createPlaylist(){
        Scanner in=new Scanner(System.in);
        System.out.print("Enter the playlist name : ");
        String playName=in.nextLine();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO playlists (name) VALUES (?)"
                );
                
                preparedStatement.setString(1, playName);
                preparedStatement.executeUpdate();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addSongtoPlaylist(playName);
        }
        public static void searchSongs(){
            Scanner in=new Scanner(System.in);

        System.out.print("Enter the song name to Search : ");
        String songName=in.nextLine();

        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
        "SELECT id,title FROM songs WHERE title LIKE ?"
                    );

                preparedStatement.setString(1, "%" + songName + "%");


                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                            String Searchname = resultSet.getString("title");
                            int id=resultSet.getInt("id");
                            System.out.println(id+" "+Searchname);
                    }
                    System.out.print("Enter the song id to play :");
                    int playid=in.nextInt();
                    preparedStatement = connection.prepareStatement(
                    "SELECT * FROM songs WHERE id = ?"
                    );

                    preparedStatement.setInt(1, playid);
                    
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        String title =resultSet.getString("title");
                        String artist=resultSet.getString("artist");
                        String duration=resultSet.getString("duration");
                        System.out.print("\n\nPlaying Song...\nTitle : "+title+"\nArtist : "+artist+"\nDuration : "+duration);
                        String path = resultSet.getString("path");
                        play(path);
                    }
            
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }
        private static void user(){
          System.out.print("Enter the administative password: ");
          Scanner in=new Scanner(System.in);
          String pass=in.nextLine();
          if(pass.equals("bala")){

            try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
              PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM register"
                );
                ResultSet resultSet = preparedStatement.executeQuery();
                int count=0;
                while(resultSet.next()){
                      
                      String firstname=resultSet.getString("firstname");
                      String lastname=resultSet.getString("lastname");
                      String email=resultSet.getString("email");
                      String contact=resultSet.getString("contact");
                      String password=resultSet.getString("password");
                      
                      UserDetails det=new User(firstname,lastname,email,contact,password);
                      count++;
                      System.out.println("\n"+count+":"+"\n");
                      det.gUser();
                    }
            resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      else{
        System.out.println("Failed! Password is incorrect......");
      }
        
      }
      public static void addSongtoPlaylist(String playname){
        int playlistid=1;
         try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
              PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM playlists where name = ?"
                );
                preparedStatement.setString(1, playname);
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                      
                      playlistid=resultSet.getInt("playlist_id");
                     
                    }
              addSongtoPlaylist(playlistid);
            resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      	public static void addSongtoPlaylist(int playlistid) {
        Scanner in=new Scanner(System.in);
        String ch;
        // do{
        System.out.print("Do you want to add songs to playlist (y/n): ");
        ch=in.nextLine();
        // if(ch=="y"){

          System.out.print("Enter the song id :");
          int songid=in.nextInt();
          try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
              "INSERT INTO playlistsongs (playlist_id,song_id) VALUES (?,?)"
              );
              
              preparedStatement.setInt(1, playlistid);
              preparedStatement.setInt(2, songid);
            preparedStatement.executeUpdate();
            System.out.println("Song added successfully.");
          } catch (SQLException e) {
            e.printStackTrace();
          }
          
        // }
      // }while(ch=="y");
      }
        public void main() {
          String header =
      "$$\\      $$\\                     $$\\           $$\\  $$\\  $$\\    \n"+
      "$$$\\    $$$ |                    \\__|          \\$$\\ \\$$\\ \\$$\\   \n"+
      "$$$$\\  $$$$ |$$\\   $$\\  $$$$$$$\\ $$\\  $$$$$$$\\  \\$$\\ \\$$\\ \\$$\\  \n"+
      "$$\\$$\\$$ $$ |$$ |  $$ |$$  _____|$$ |$$  _____|  \\$$\\ \\$$\\ \\$$\\ \n"+
      "$$ \\$$$  $$ |$$ |  $$ |\\$$$$$$\\  $$ |$$ /        $$  |$$  |$$  |\n"+
      "$$ |\\$  /$$ |$$ |  $$ | \\____$$\\ $$ |$$ |       $$  /$$  /$$  / \n"+
      "$$ | \\_/ $$ |\\$$$$$$  |$$$$$$$  |$$ |\\$$$$$$$\\ $$  /$$  /$$  /  \n"+
      "\\__|     \\__| \\______/ \\_______/ \\__| \\_______|\\__/ \\__/ \\__/   \n";
                                               

        System.out.println(header);
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          
          Scanner in = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\nMenu:");
                System.out.println("1. User Details");
                System.out.println("2. Add Song");
                System.out.println("3. Create Playlist");
                System.out.println("4. Show Songs");
                System.out.println("5. Play Songs");
                System.out.println("6. Search Songs");
                System.out.println("7. Exit");
                System.out.print("Enter your choice : ");
                choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                    user();
                    break;
                    case 2:
                    addSong();
                    break;
                    case 3:
                    createPlaylist();
                    break;
                    case 4:
                    ShowSongs();
                    break;
                    case 5:
                    playSong();
                    break;
                    case 6:
                    searchSongs();
                    break;
                    case 7:
                    System.out.println("Exiting...\n");
                    break;
                    default:
                    System.out.println("Invalid choice. Try again...");
                    break;
                }
            } while (choice != 7);

            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}