package producer_consumer_adapter;

import singleton.SingletonLog;
import utility.collection.ArrayList;
import utility.collection.ListADT;

public class Deposit<T> implements DepositValuables<T> {
  private ListADT<T> list;
  private int capacity;

  public Deposit(int capacity) {
    this.capacity = capacity;
    list = new ArrayList<>();
  }

  @Override
  public synchronized void add(T element) {
    while(list.size() >= capacity){
      try {
        SingletonLog.getInstance().addLog(
            Thread.currentThread().getName() +
                " is waiting for space in the deposit.");
        wait();
      }
      catch (InterruptedException e) {
        SingletonLog.getInstance().addLog(
            Thread.currentThread().getName() +
                " was interrupted while waiting to add to deposit.");
        Thread.currentThread().interrupt(); // Restore interrupt flag
        throw new RuntimeException(e);
      }
    }
    list.add(element);
    SingletonLog.getInstance().addLog(
        Thread.currentThread().getName() +
            " added an item to the deposit. Current size: " + list.size() + "/" + capacity);
    notifyAll();
  }

  @Override
  public synchronized T take() {
    while(list.isEmpty()){
      try {
        SingletonLog.getInstance().addLog(
            Thread.currentThread().getName() +
                " is waiting for valuables in the deposit.");
        wait();
      }
      catch (InterruptedException e) {
        SingletonLog.getInstance().addLog(
            Thread.currentThread().getName() +
                " was interrupted while waiting to take from deposit.");
        Thread.currentThread().interrupt(); // Restore interrupt flag
        throw new RuntimeException(e);
      }
    }
    T valuable = list.get(0);
    list.remove(0);
    SingletonLog.getInstance().addLog(
        Thread.currentThread().getName() +
            " took an item from the deposit. Remaining: " + list.size() + "/" + capacity);
    notifyAll();
    return valuable;
  }

  @Override
  public synchronized T get(int index) {
    // Validate the index is within bounds
    if(index < 0 || index >= list.size()) {
      System.err.println("ERROR: Invalid index " + index + " (valid range: 0-" + (list.size()-1) + ")");
      throw new IllegalArgumentException("Invalid index: " + index);
    }
    
    // Index is valid, return the item
    return list.get(index);
  }

  @Override
  public synchronized boolean contains(T element) {
    return list.contains(element);
  }

  @Override
  public synchronized boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public synchronized int getSize() {
    return list.size();
  }

  @Override
  public int getCapacity() {
    return capacity;
  }

  @Override 
  public int size() {
    return list.size(); // Make sure both methods return the same value
  }
}
