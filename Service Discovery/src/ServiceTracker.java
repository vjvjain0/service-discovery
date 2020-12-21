import java.time.Instant;
import java.util.*;

public class ServiceTracker extends Thread {

    private Map<UUID, Instant> liveServers;

    public ServiceTracker(Map<UUID, Instant> map) {
        this.liveServers = map;
    }

    public void updateService(UUID serverId) {
        Instant currentTime = Instant.now();
        liveServers.replace(serverId, currentTime);
        liveServers.putIfAbsent(serverId, currentTime);
    }

    @Override
    public void run() {
        System.out.println("Service Tracker started");
        while (true) {
            try {
                Set<UUID> deadServers = new HashSet();
                for(Map.Entry<UUID, Instant> entry : liveServers.entrySet()) {
                    UUID serverId = entry.getKey();
                    Instant lastHeartBeat = entry.getValue();
                    Instant currentTime = Instant.now();
                    if(currentTime.getEpochSecond() - lastHeartBeat.getEpochSecond() > 15) {
                        System.out.printf("Server with id %s died\n", serverId);
                        deadServers.add(serverId);
                    }
                }
                for(UUID serverId: deadServers) {
                    liveServers.remove(serverId);
                }
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
