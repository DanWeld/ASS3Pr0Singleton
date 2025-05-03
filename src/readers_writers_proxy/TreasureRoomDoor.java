package readers_writers_proxy;

import singleton.SingletonValuables;

import java.util.ArrayList;

public interface TreasureRoomDoor extends TreasureRoomDoorAccess {
  public void add(ArrayList<SingletonValuables> valuables, String userType);
  public SingletonValuables retrieve(int choice, String userType);
  public int look(String userType);

  int getTotalValuablesCount();
}
