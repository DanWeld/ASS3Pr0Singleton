package producer_consumer_adapter;

import singleton.SingletonValuables;

import java.util.Random;

public class Miner implements Runnable{
  //because deposit is a shared resource between Miner and Valuable Transporter, so Miner has reference to Deposit
  private DepositValuables<SingletonValuables>  depositValuables;

  public Miner(DepositValuables<SingletonValuables> depositValuables) {
    this.depositValuables = depositValuables;
  }

  private void sleep(){
    try {
      Thread.sleep(10000);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    Random random  = new Random();
    while(true){
      try {
        int value = random.nextInt(5) + 1;
        SingletonValuables valuable = null;
        
        if(value == 1){
          valuable = SingletonValuables.item("Diamond");
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " mining a Diamond");
        } else if(value == 2){
          valuable = SingletonValuables.item("GoldNugget");
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " mining a GoldNugget");
        } else if(value == 3){
          valuable = SingletonValuables.item("Jewel");
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " mining a Jewel");
        } else if(value == 4){
          valuable = SingletonValuables.item("Ruby");
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " mining a Ruby");
        } else {
          valuable = SingletonValuables.item("WoodenCoin");
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " mining a WoodenCoin");
        }
        
        singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " waiting to add " + valuable.getValueType() + " to deposit");
        depositValuables.add(valuable);
        singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " added " + valuable.getValueType() + " to deposit");
      } catch (RuntimeException e) {
        // Handle interruption
        if (e.getCause() instanceof InterruptedException) {
          singleton.SingletonLog.getInstance().addLog(Thread.currentThread().getName() + " was interrupted");
          break;
        }
        throw e;
      }
    }
  }
}
