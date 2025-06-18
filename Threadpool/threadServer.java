package Threadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threadServer {
    public final ExecutorService threadPool;
    public threadServer(int poolsize){
        this.threadPool = Executors.newFixedThreadPool(poolsize);
    }
    public void handleClient(Socket ClientSocket){
        try{
            PrintWriter toSocket = new PrintWriter(ClientSocket.getOutputStream(),true);
            toSocket.println("Hello from the server :" +ClientSocket.getInetAddress());
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException {
        int port=8010;
        int poolsize = 10;
        threadServer server= new threadServer(poolsize);
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(7000);
            System.out.println("server is listening on port : "+port);

            while(true){
                Socket CLientSocket=serverSocket.accept();
                server.threadPool.execute(()->server.handleClient(CLientSocket));
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            server.threadPool.shutdown();
        }

    }
}
