import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

 // initialize socket and input output streams 
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private PrintWriter pw;
    private BufferedReader br;
  
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) 
    { 
        Client client = new Client("192.168.1.49", 3008); 
        client.write("Hello World");
        System.out.println(0);
    } 
    
}
