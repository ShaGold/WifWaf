package shagold.wifwaf.manager;

import com.github.nkzawa.socketio.client.Socket;

public class SocketManager {
    private static Socket mySocket;
    private static int idUser;

    public static synchronized Socket getMySocket(){
        return SocketManager.mySocket;
    }

    public static synchronized int getIdUser(){ return SocketManager.idUser; }

    public static synchronized void setMySocket(Socket s){
        SocketManager.mySocket = s;
    }

    public static synchronized void setIdUser(int id){ SocketManager.idUser = id; }

}
