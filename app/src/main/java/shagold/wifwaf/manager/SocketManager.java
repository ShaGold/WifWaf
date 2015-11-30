package shagold.wifwaf.manager;

import com.github.nkzawa.socketio.client.Socket;

public class SocketManager {
    private static Socket mySocket;

    public static synchronized Socket getMySocket(){
        return SocketManager.mySocket;
    }

    public static synchronized void setMySocket(Socket s){
        SocketManager.mySocket = s;
    }

}
