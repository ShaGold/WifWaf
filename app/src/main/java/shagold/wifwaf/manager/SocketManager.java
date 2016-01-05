package shagold.wifwaf.manager;

import com.github.nkzawa.socketio.client.Socket;

import shagold.wifwaf.dataBase.User;

public class SocketManager {
    private static Socket mySocket;
    private static User myUser;

    public static synchronized Socket getMySocket(){
        return SocketManager.mySocket;
    }

    public static synchronized User getMyUser(){ return SocketManager.myUser; }

    public static synchronized void setMySocket(Socket s){
        SocketManager.mySocket = s;
    }

    public static synchronized void setMyUser(User myUser){ SocketManager.myUser = myUser;}

    public static synchronized void setMyUserWithoutPic(User myUser){
        User newU = new User( myUser.getIdUser(), myUser.getEmail(), myUser.getNickname(), myUser.getPassword(), myUser.getBirthday(), myUser.getPhoneNumber(), myUser.getDescription(), myUser.getPhoto());
        SocketManager.myUser = newU;
    }

}
