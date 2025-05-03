package producer_consumer_adapter;

public interface DepositValuables<T> {
  void add(T element);
  T take();
  T get(int index);
  boolean contains(T element);
  boolean isEmpty();
  int getSize();
  int getCapacity();
  int size();
}
