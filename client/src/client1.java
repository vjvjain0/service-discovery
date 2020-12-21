import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;

class connection extends Thread
{
    private UUID id;
    connection(UUID id)
    {
        this.id = id;
    }
    public void run()
    {
        while(true) {
            try {
                Socket client = new Socket("localhost", 9000);
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeUTF(id.toString());
                out.flush();
                out.close();
                client.close();
                Thread.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
public class client1 {
    public static void main(String[] args) {
        connection connection1 = new connection(UUID.randomUUID());
        connection connection2 = new connection(UUID.randomUUID());
        connection connection3 = new connection(UUID.randomUUID());
        try {
            connection1.start();
            Thread.sleep(4000);
            connection2.start();
            Thread.sleep(10_000);
            connection3.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}