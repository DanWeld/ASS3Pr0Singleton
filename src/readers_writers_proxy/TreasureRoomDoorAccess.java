package readers_writers_proxy;

public interface TreasureRoomDoorAccess {
  public void acquireRead();
  public void releaseRead();
  public void acquireWrite();
  public void releaseWrite();
//  void acquireKingAccess();
//  void releaseKingAccess();
}
