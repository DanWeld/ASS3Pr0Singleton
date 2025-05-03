import singleton.SingletonValuables;
import producer_consumer_adapter.Deposit;
import producer_consumer_adapter.DepositValuables;
import producer_consumer_adapter.Miner;
import producer_consumer_adapter.ValuableTransporter;
import readers_writers_proxy.*;
import singleton.SingletonLog;


public class Main {
  public static void main(String[] args) {
    System.out.println("Kingdom Simulation Starting...");
    
    // Register shutdown hook to finalize JSON log
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Finalizing log file...");
      SingletonLog.getInstance().finalizeLogFile();
    }));
    
    // Initialize the SingletonLog
    SingletonLog log = SingletonLog.getInstance();
    log.addLog("Kingdom simulation starting at " + java.time.LocalDateTime.now());
    
    // Create the deposit where miners will place their valuables
    DepositValuables<SingletonValuables> valuables = new Deposit<>(50);
    log.addLog("Created mining deposit with capacity 50");
    
    // Create the treasure room with its guardsman proxy
    TreasureRoomDoor treasureRoomDoor = new TreasureRoomGuardsman(new TreasureRoom());
    log.addLog("Created royal treasure room with guardsman");
    
    // Create miners (producers)
    Miner miner1 = new Miner(valuables);
    Miner miner2 = new Miner(valuables);
    Miner miner3 = new Miner(valuables);
    log.addLog("Hired 3 miners to extract valuable resources");
    
    // Create transporters (consumers)
    ValuableTransporter transporter1 = new ValuableTransporter(valuables, 150, treasureRoomDoor);
    ValuableTransporter transporter2 = new ValuableTransporter(valuables, 150, treasureRoomDoor);
    log.addLog("Assigned 2 transporters to move valuables to the treasure room");
    
    // Create the king (writer to treasure room)
    King king = new King(treasureRoomDoor);
    log.addLog("The King is ready to inspect the royal treasury");
    
    // Create accountants (readers of treasure room)
    Accountant accountant1 = new Accountant(treasureRoomDoor);
    Accountant accountant2 = new Accountant(treasureRoomDoor);
    log.addLog("Appointed 2 royal accountants to track kingdom wealth");


    // Create and name all threads
    Thread minerThread1 = new Thread(miner1, "Miner1");
    Thread minerThread2 = new Thread(miner2, "Miner2");
    Thread minerThread3 = new Thread(miner3, "Miner3");
    
    Thread transporterThread1 = new Thread(transporter1, "Transporter1");
    Thread transporterThread2 = new Thread(transporter2, "Transporter2");
    
    Thread kingThread = new Thread(king, "King");
    
    Thread accountantThread1 = new Thread(accountant1, "Accountant1");
    Thread accountantThread2 = new Thread(accountant2, "Accountant2");

    // Start all threads
    log.addLog("Starting all kingdom activities");
    minerThread1.start();
    minerThread2.start();
    minerThread3.start();
    transporterThread1.start();
    transporterThread2.start();
    kingThread.start();
    accountantThread1.start();
    accountantThread2.start();

    log.addLog("Kingdom simulation running successfully");
    
    // Run for a specific time (60 seconds)
    try {
        log.addLog("Kingdom will run for 60 seconds");
        Thread.sleep(60000);
        
        // Stop all threads gracefully
        log.addLog("Ending simulation - stopping all kingdom activities");
        minerThread1.interrupt();
        minerThread2.interrupt();
        minerThread3.interrupt();
        transporterThread1.interrupt();
        transporterThread2.interrupt();
        kingThread.interrupt();
        accountantThread1.interrupt();
        accountantThread2.interrupt();
        
        // Wait for threads to finish
        Thread.sleep(1000);
        
    } catch (InterruptedException e) {
        log.addLog("Kingdom simulation was interrupted: " + e.getMessage());
    }
    
    log.addLog("Kingdom simulation completed at " + java.time.LocalDateTime.now());
    System.out.println("Kingdom Simulation Completed.");
  }
}