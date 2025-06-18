import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client1 {
    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try{
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    try(
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(),true);
                            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    )
                    {
                        toSocket.println("Hello World from Client1 ");
                        String line = fromSocket.readLine();
                        System.out.println("response from the server : "+line);
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
    }
    public static void main(String[] args) {
        Client1 Client1 = new Client1();
        for(int i=0;i<100;i++){
            try{
                Thread thread = new Thread(Client1.getRunnable());
                thread.start();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
