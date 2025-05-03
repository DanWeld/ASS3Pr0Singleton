package readers_writers_proxy;

import singleton.SingletonValuables;
import singleton.SingletonLog;

import java.util.ArrayList;

public class TreasureRoomGuardsman implements TreasureRoomDoor{
  private TreasureRoom treasureRoom;
  public TreasureRoomGuardsman(TreasureRoom treasureRoom) {
    this.treasureRoom = treasureRoom;
  }
  @Override
  public synchronized void add(ArrayList<SingletonValuables> valuables,
      String userType) {
    if(userType.equals("transporter") || userType.equals("king"))
      treasureRoom.add(valuables, userType);

    else {
      SingletonLog.getInstance().addLog(Thread.currentThread().getName()
          + " Guardsman did not allow the access");
    }
  }
  @Override
  public synchronized SingletonValuables retrieve(int choice,
      String userType) {
    if(userType.equals("king"))
      return treasureRoom.retrieve(choice, userType);
    else {
      SingletonLog.getInstance().addLog(Thread.currentThread().getName()
          + " Guardsman did not allow the access");
    }
    return null;
  }
  @Override
  public int look(String userType) {
    if(userType.equals("accountant"))
      return treasureRoom.look(userType);
    else {
      SingletonLog.getInstance().addLog(Thread.currentThread().getName()
          + " Guardsman did not allow the access");
    }
    return 0;
  }
  @Override
  public synchronized int getTotalValuablesCount() {
    return treasureRoom.getTotalValuablesCount();
  }

  @Override
  public synchronized void acquireRead() {
    treasureRoom.acquireRead();
  }

  @Override
  public synchronized void releaseRead() {
    treasureRoom.releaseRead();
  }

  @Override
  public synchronized void acquireWrite() {
    treasureRoom.acquireWrite();
  }

  @Override
  public synchronized void releaseWrite() {
    treasureRoom.releaseWrite();
  }

}
