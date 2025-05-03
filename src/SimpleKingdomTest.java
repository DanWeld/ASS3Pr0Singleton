import singleton.SingletonValuables;
import producer_consumer_adapter.Deposit;
import producer_consumer_adapter.DepositValuables;
import producer_consumer_adapter.Miner;
import producer_consumer_adapter.ValuableTransporter;
import readers_writers_proxy.Accountant;
import readers_writers_proxy.King;
import readers_writers_proxy.TreasureRoom;
import readers_writers_proxy.TreasureRoomDoor;
import readers_writers_proxy.TreasureRoomGuardsman;
import singleton.SingletonLog;

/**
 * Simple standalone test class for the Kingdom simulation.
 * This class fulfills the assignment requirement to:
 * "Test the full system in a main method with a Deposit, one King and a couple of each 
 * of the other actors: Miners, ValuableTransporters and Accountants"
 */
public class SimpleKingdomTest {

    public static void main(String[] args) {
        System.out.println("===== KINGDOM SIMULATION TEST =====");
        
        // Configure log finalization when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Finalizing log file...");
            SingletonLog.getInstance().finalizeLogFile();
        }));
        
        // Initialize components
        SingletonLog log = SingletonLog.getInstance();
        log.addLog("Creating kingdom components");
          // Create deposit (shared resource for miners and transporters)
        DepositValuables<SingletonValuables> deposit = new Deposit<>(10);
        log.addLog("Created deposit with capacity 10");
        
        // Create treasure room with guardsman proxy
        TreasureRoom treasureRoom = new TreasureRoom();
        TreasureRoomDoor guardsman = new TreasureRoomGuardsman(treasureRoom);
        log.addLog("Created treasure room with guardsman");
        
        // Create miners (producers)
        Miner miner1 = new Miner(deposit);
        Miner miner2 = new Miner(deposit);
        log.addLog("Created 2 miners");
        
        // Create transporters (consumers)
        ValuableTransporter transporter1 = new ValuableTransporter(deposit, 20, guardsman);
        ValuableTransporter transporter2 = new ValuableTransporter(deposit, 20, guardsman);
        log.addLog("Created 2 transporters");
        
        // Create king (treasure room writer)
        King king = new King(guardsman);
        log.addLog("Created the king");
        
        // Create accountants (treasure room readers)
        Accountant accountant1 = new Accountant(guardsman);
        Accountant accountant2 = new Accountant(guardsman);
        log.addLog("Created 2 accountants");
        
        // Create and start threads
        log.addLog("Starting all kingdom actors");
        
        Thread minerThread1 = new Thread(miner1, "Miner1");
        Thread minerThread2 = new Thread(miner2, "Miner2");
        
        Thread transporterThread1 = new Thread(transporter1, "Transporter1");
        Thread transporterThread2 = new Thread(transporter2, "Transporter2");
        
        Thread kingThread = new Thread(king, "King");
        
        Thread accountantThread1 = new Thread(accountant1, "Accountant1");
        Thread accountantThread2 = new Thread(accountant2, "Accountant2");
        
        // Start threads
        minerThread1.start();
        minerThread2.start();
        transporterThread1.start();
        transporterThread2.start();
        kingThread.start();
        accountantThread1.start();
        accountantThread2.start();
          log.addLog("All kingdom actors are now active");
        System.out.println("Simulation running...");
        System.out.println("(This will run for 10 seconds)");
        
        // Let the simulation run for 10 seconds (reduced from 20s for faster testing)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.addLog("Simulation interrupted: " + e.getMessage());
        }
        
        // Stop threads
        log.addLog("Stopping all kingdom activities");
        minerThread1.interrupt();
        minerThread2.interrupt();
        transporterThread1.interrupt();
        transporterThread2.interrupt();
        kingThread.interrupt();
        accountantThread1.interrupt();
        accountantThread2.interrupt();
        
        try {
            Thread.sleep(1000); // Give threads time to clean up
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        log.addLog("Kingdom simulation completed");
        System.out.println("===== SIMULATION COMPLETE =====");
        System.out.println("Check kingdom_simulation_log.json for detailed activity log");
    }
}
