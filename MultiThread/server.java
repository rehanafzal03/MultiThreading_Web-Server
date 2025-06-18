package MultiThread;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class server {
    public Consumer<Socket> getConsumer(){
        return(clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        };
    }
    public static void main(String[] args) throws IOException {
        int port = 8010;
        server server = new server();
        try{
            ServerSocket serverSocket= new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("server is listening on port : "+port);
            while(true){
                Socket acceptedSocket= serverSocket.accept();
                Thread  thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
