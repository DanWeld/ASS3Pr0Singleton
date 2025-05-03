package readers_writers_proxy;

import singleton.SingletonValuables;

import java.util.ArrayList;

public class TreasureRoom implements TreasureRoomDoor {
  private ArrayList<SingletonValuables> valuables;
  private int activeAccountant = 0;
  private int waitingWriter = 0;

  private int activeWriter = 0;
  private int diamondCount = 0;
  private int jewelCount = 0;
  private int rubyCount = 0;
  private int woodenCoin = 0;
  private int goldNugget = 0;
  private int treasureWorth = 0;



  public TreasureRoom() {
    valuables = new ArrayList<>();
  }

  @Override
  public synchronized void add(ArrayList<SingletonValuables> gems,
      String userType) {
    for (int i = 0; i < gems.size(); i++) {
      SingletonValuables gem = gems.get(i);
      String type = gem.getValueType();
      
      if (type.equals("Diamond")) {
        diamondCount++;
      }
      else if (type.equals("Jewel")) {
        jewelCount++;
      }
      else if (type.equals("Ruby")) {
        rubyCount++;
      }
      else if (type.equals("WoodenCoin")) {
        woodenCoin++;
      }
      else if (type.equals("GoldNugget")) {
        goldNugget++;
      }
      
      int value = gem.getValueWorth();
      treasureWorth += value;
      valuables.add(gem);
    }
  }

  @Override
  public synchronized SingletonValuables retrieve(int choice, String userType) {
    SingletonValuables valuable = null;
    
    // Wait until we have sufficient valuables
    while(valuables.size() <= 2){
      try {
        singleton.SingletonLog.getInstance().addLog(userType + " is waiting for more treasures to accumulate");
        wait();
      }
      catch (InterruptedException e) {
        singleton.SingletonLog.getInstance().addLog(userType + " was interrupted while waiting to retrieve valuables");
        Thread.currentThread().interrupt(); // Restore interrupt flag
        throw new RuntimeException(e);
      }
    }
    
    // Create the appropriate valuable type based on choice
    if (choice == 1 && diamondCount > 0){
        valuable = SingletonValuables.item("Diamond");
        diamondCount--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a Diamond from the treasure room");
      }
      else if (choice == 2 && jewelCount > 0) {
        valuable = SingletonValuables.item("Jewel");
        jewelCount--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a Jewel from the treasure room");
      }
      else if (choice == 3 && rubyCount > 0) {
        valuable = SingletonValuables.item("Ruby");
        rubyCount--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a Ruby from the treasure room");
      }
      else if (choice == 4 && woodenCoin > 0) {
        valuable = SingletonValuables.item("WoodenCoin");
        woodenCoin--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a WoodenCoin from the treasure room");
      }
      else if (choice == 5 && goldNugget > 0) {
        valuable = SingletonValuables.item("GoldNugget");
        goldNugget--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a GoldNugget from the treasure room");
      }
      else {
        // Default to a valuable we definitely have (the list has at least 3 items at this point)
        valuable = SingletonValuables.item("WoodenCoin");
        woodenCoin--;
        singleton.SingletonLog.getInstance().addLog(userType + " retrieved a WoodenCoin (default) from the treasure room");
      }
      
      // Remove one valuable from the list to keep counts consistent
      if (!valuables.isEmpty()) {
          valuables.remove(0);
      }
      
      return valuable;
  }

  @Override
  public int look(String userType) {
    return getTreasureWorth();
  }

  public synchronized int getTotalValuablesCount() {
    return getDiamondCount() + getJewelCount() + getRubyCount() + getWoodenCoin() + getGoldNugget();
  }

  @Override
  public synchronized void acquireRead() {
    while(activeWriter > 0 || waitingWriter > 0){
      try {
        wait();
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    activeAccountant++;
  }

  @Override
  public synchronized void releaseRead() {
    activeAccountant--;
    if(activeAccountant == 0){
      notifyAll();
    }
  }

  @Override
  public synchronized void acquireWrite() {
    waitingWriter++;
    while(activeWriter > 0 || activeAccountant > 0){
      try {
        wait();
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    waitingWriter--;
    activeWriter++;
  }

  @Override
  public synchronized void releaseWrite() {
    activeWriter--;
    if(activeWriter == 0)
      notifyAll();
  }


  public synchronized int getDiamondCount() {
    return diamondCount;
  }

  public synchronized int getJewelCount() {
    return jewelCount;
  }

  public synchronized int getRubyCount() {
    return rubyCount;
  }

  public synchronized int getWoodenCoin() {
    return woodenCoin;
  }

  public synchronized int getGoldNugget() {
    return goldNugget;
  }

  public synchronized int getTreasureWorth() {
    return treasureWorth;
  }


}
