package readers_writers_proxy;

import singleton.SingletonLog;

public class Accountant implements Runnable{
  private TreasureRoomDoor treasureRoomDoor;

  public Accountant(TreasureRoomDoor treasureRoomDoor) {
    this.treasureRoomDoor = treasureRoomDoor;
  }

  @Override
  public void run() {
    while(true){
      treasureRoomDoor.acquireRead();
      int totalValue = treasureRoomDoor.look("accountant");
      SingletonLog.getInstance().addLog("The total value of the treasure room is " + totalValue);
      treasureRoomDoor.releaseRead();
      try {
        Thread.sleep(10);
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
