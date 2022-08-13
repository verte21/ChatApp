package Sieciowe;

public class Request {
    String username;
    Boolean isLoggedIn;
    String mode;
    String path;

    Request(String username, Boolean isLoggedIn, String mode, String path){
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        this.mode = mode;
        this.path = path;
    }

}
