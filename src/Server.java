import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private PrintWriter pw;
    private BufferedReader br;
    
    public Server(int port) 
    { 
        // establish a connection 
        try
        { 
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            out = socket.getOutputStream();
            pw = new PrintWriter(out);
            in = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
        } 
        catch(UnknownHostException u) 
        { 
            u.printStackTrace(); 
        } 
        catch(IOException i) 
        { 
            i.printStackTrace();
        }
    }
    
    public void write(String s) {
        pw.write(s);
        pw.flush();
    }
    
    public String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void close() {
        pw.close();
        try {
            br.close();
            out.close();
            in.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) 
    { 
        System.out.println(-1);
        Server server = new Server(3008);
        System.out.println(0);
        System.out.println(server.read());
    } 
    
}
