package Sieciowe;

import java.io.IOException;
import java.util.Locale;

public class ApiGateway {

    public static Response checkInput(Request req) throws Exception {
        Response toReturn = null;
          switch (req.mode.toLowerCase()){
                case "login":  toReturn = Services.userLogIn();
                break;
                case "register":  toReturn = Services.userRegister();
                break;
                case "enter chat": Services.enterChat(req.username);
                break;
                case "get file":
                break;
                case "send file": Services.sendFile(req.path);
                break;
                case "logout": toReturn = Services.userLogout();
                break;
                default: toReturn = new Response("", req.isLoggedIn, "500", "Please choose correct mode");
          }
        return toReturn;
    }


}
