import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class ServiceDiscovery {

    private static Logger logger = Logger.getLogger(ServiceDiscovery.class.getName());

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            ServiceTracker serviceTracker = new ServiceTracker(new HashMap<>());
            Socket server;
            serviceTracker.start();
            DataInputStream in;
            DataOutputStream out;
            String serviceId = "";
            while (true) {
                server = serverSocket.accept();
                in = new DataInputStream(server.getInputStream());
                serviceId = in.readUTF();
                serviceTracker.updateService(UUID.fromString(serviceId));
                out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("ACK for id: " + serviceId);
                out.flush();
                out.close();
                logger.info(String.format("heartbeat for service %s received",serviceId));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
