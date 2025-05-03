package producer_consumer_adapter;

import singleton.SingletonValuables;
import readers_writers_proxy.TreasureRoomDoor;
import singleton.SingletonLog;

import java.util.ArrayList;

public class ValuableTransporter implements Runnable {
  private DepositValuables<SingletonValuables> depositValuables;
  private int targetWorth;
  private int totalWorth = 0;
  private TreasureRoomDoor treasureRoomDoor;

  public ValuableTransporter(DepositValuables<SingletonValuables> depositValuables,
      int targetWorth, TreasureRoomDoor treasureRoomDoor) {
    this.depositValuables = depositValuables;
    this.targetWorth = targetWorth;
    this.treasureRoomDoor = treasureRoomDoor;
  }

  private void sleep() {
    try {
      Thread.sleep(2000);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    ArrayList<SingletonValuables> valuables = new ArrayList<>();
    while(true) {
      try {
        SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " waiting to take valuable from deposit");
        SingletonValuables items = depositValuables.take();
        valuables.add(items);
        SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " took " + items.getValueType() + " worth " + items.getValueWorth() + " from the deposit.");
        totalWorth += items.getValueWorth();

        if(totalWorth >= targetWorth) {
          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " total worth of valuables now at " + totalWorth + " which exceeds the target worth of " + targetWorth);
          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " is carrying valuables worth " + totalWorth + " to the treasure room");

          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " waiting to access treasure room");
          treasureRoomDoor.acquireWrite();
          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " adding valuables to treasure room");
          treasureRoomDoor.add(valuables, "transporter");
          treasureRoomDoor.releaseWrite();
          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " finished adding valuables to treasure room");

          sleep();
          valuables = new ArrayList<>();
          totalWorth = 0;
        }
      } catch (RuntimeException e) {
        if (e.getCause() instanceof InterruptedException) {
          SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " was interrupted");
          break;
        }
        throw e;
      }
    }
  }
}
