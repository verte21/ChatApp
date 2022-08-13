package Sieciowe;

public class Response {
        String username;
        Boolean isLoggedIn;
        String status;
        String message;

        Response(String username, Boolean isLoggedIn, String status, String message){
            this.username = username;
            this.isLoggedIn = isLoggedIn;
            this.status = status;
            this.message = message;
        }

}
