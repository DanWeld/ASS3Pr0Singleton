package readers_writers_proxy;

import singleton.SingletonValuables;
import singleton.SingletonLog;

import java.util.ArrayList;
import java.util.Random;

public class King implements Runnable{
  private TreasureRoomDoor treasureRoomDoor;

  public King(TreasureRoomDoor treasureRoomDoor) {
    this.treasureRoomDoor = treasureRoomDoor;
  }

  @Override
  public void run() {
    ArrayList<SingletonValuables> valuables = new ArrayList<>();
    Random random = new Random();
    int targetForParty;
    int collectedValue = 0;

    while(true){
      targetForParty = random.nextInt(100) + 50;
      int number = random.nextInt(5) + 1;
      treasureRoomDoor.acquireWrite();

      while (true) {
        SingletonValuables valuable = treasureRoomDoor.retrieve(number, "king");
        valuables.add(valuable);
        collectedValue += valuable.getValueWorth();
        if(collectedValue >= targetForParty ||treasureRoomDoor.getTotalValuablesCount() < 10){
          break;
        } else {
          try {
            Thread.sleep(5000);
          }
          catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
      treasureRoomDoor.releaseWrite();
      if(collectedValue >= targetForParty){
        SingletonLog.getInstance().addLog("King threw a party of worth " + collectedValue);
      } else {
        treasureRoomDoor.add(valuables, "king");
        SingletonLog.getInstance().addLog("King cancelled the party because of not valuables in the Treasury");
      }
    }
  }
}
