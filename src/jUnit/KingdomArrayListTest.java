package jUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import utility.collection.ArrayList;
import utility.collection.ListADT;
import singleton.SingletonValuables;
import producer_consumer_adapter.Deposit;
import producer_consumer_adapter.DepositValuables;
import singleton.SingletonLog;

/**
 * Tests for the ArrayList used in the Kingdom's Deposit system.
 * This ensures the proper functioning of the ArrayList implementation
 * that serves as the foundation for the Deposit adapter used by
 * Miners and ValuableTransporters in the kingdom simulation.
 */
public class KingdomArrayListTest {
  private ListADT<SingletonValuables> treasures;
  private DepositValuables<SingletonValuables> kingdomDeposit;
  private SingletonValuables singleton;

  @BeforeEach
  public void setUp() throws Exception {
    treasures = new ArrayList<>();
    kingdomDeposit = new Deposit<>(5); // A small deposit for testing purposes
    
    // Get the singleton instance
    singleton = SingletonValuables.getInstance();
    
    // Reset the singleton's state before each test
    singleton.resetTotalWorth();
  }

  /**
   * Test that initially the treasure collection is empty,
   * similar to how a newly created deposit starts empty.
   */
  @Test
  public void testEmptyTreasureCollection() {
    assertEquals(0, treasures.size());
    assertTrue(treasures.isEmpty());
    assertEquals("{}", treasures.toString());
  }  /**
   * Test adding valuables to the collection,
   * similar to how miners would add treasures to the deposit.
   */
  @Test
  public void testAddValuables() {
    treasures.add(SingletonValuables.item("Diamond"));
    treasures.add(SingletonValuables.item("GoldNugget"));

    assertEquals(2, treasures.size());
    assertFalse(treasures.isEmpty());
    assertEquals("Diamond", treasures.get(0).getValueType());
    assertEquals("GoldNugget", treasures.get(1).getValueType());
  }

  /**
   * Test inserting valuables at specific positions,
   * which may happen when organizing the kingdom's treasures.
   */
  @Test
  public void testAddValuableAtIndex() {
    treasures.add(0, SingletonValuables.item("Diamond"));
    treasures.add(1, SingletonValuables.item("GoldNugget"));
    treasures.add(1, SingletonValuables.item("Ruby"));

    assertEquals(3, treasures.size());
    assertEquals("Diamond", treasures.get(0).getValueType());
    assertEquals("Ruby", treasures.get(1).getValueType());
    assertEquals("GoldNugget", treasures.get(2).getValueType());
  }

  /**
   * Test checking if specific treasures exist in the collection,
   * similar to how the TreasureRoom might check its inventory.
   */
  @Test
  public void testContainsValuable() {
    SingletonValuables diamond = SingletonValuables.item("Diamond");
    treasures.add(diamond);

    assertTrue(treasures.contains(diamond));
    assertFalse(treasures.contains(SingletonValuables.item("Silver")));
  }

  /**
   * Test finding the position of a valuable in the collection,
   * useful for the king to locate specific treasures.
   */
  @Test
  public void testFindValuableIndex() {
    SingletonValuables diamond = SingletonValuables.item("Diamond");
    SingletonValuables gold = SingletonValuables.item("GoldNugget");
    SingletonValuables ruby = SingletonValuables.item("Ruby");

    treasures.add(diamond);
    treasures.add(gold);
    treasures.add(ruby);

    assertEquals(0, treasures.indexOf(diamond));
    assertEquals(1, treasures.indexOf(gold));
    assertEquals(2, treasures.indexOf(ruby));
    assertEquals(-1, treasures.indexOf(SingletonValuables.item("Silver")));
  }

  /**
   * Test removing a valuable by reference,
   * similar to how a ValuableTransporter might take a specific item.
   */
  @Test
  public void testRemoveValuableByReference() {
    SingletonValuables diamond = SingletonValuables.item("Diamond");
    SingletonValuables gold = SingletonValuables.item("GoldNugget");
    SingletonValuables ruby = SingletonValuables.item("Ruby");

    treasures.add(diamond);
    treasures.add(gold);
    treasures.add(ruby);

    SingletonValuables removed = treasures.remove(gold);
    assertEquals("GoldNugget", removed.getValueType());
    assertEquals(2, treasures.size());
    assertFalse(treasures.contains(gold));
  }

  /**
   * Test removing a valuable by index,
   * similar to how the king might take the first available valuable.
   */
  @Test
  public void testRemoveValuableByIndex() {
    treasures.add(SingletonValuables.item("Diamond"));
    treasures.add(SingletonValuables.item("GoldNugget"));
    treasures.add(SingletonValuables.item("Ruby"));

    SingletonValuables removed = treasures.remove(1);
    assertEquals("GoldNugget", removed.getValueType());
    assertEquals(2, treasures.size());
    assertEquals("Diamond", treasures.get(0).getValueType());
    assertEquals("Ruby", treasures.get(1).getValueType());
  }

  /**
   * Test replacing a valuable with another,
   * which might happen during inventory management.
   */
  @Test
  public void testReplaceValuable() {
    treasures.add(SingletonValuables.item("Diamond"));
    treasures.add(SingletonValuables.item("GoldNugget"));

    // Save the original before replacing
    SingletonValuables original = treasures.get(1);
    // Replace with a Ruby
    treasures.set(1, SingletonValuables.item("Ruby"));

    assertEquals("GoldNugget", original.getValueType());
    assertEquals(2, treasures.size());
    assertEquals("Diamond", treasures.get(0).getValueType());
    assertEquals("Ruby", treasures.get(1).getValueType());
  }

  /**
   * Test error handling when trying to access valuables that don't exist,
   * like when the TreasureRoom is empty.
   */
  @Test
  public void testAccessEmptyTreasure() {
    assertThrows(IllegalStateException.class, () -> {
      treasures.get(0);
    });
  }

  /**
   * Test error handling when trying to access valuables outside the collection,
   * similar to requesting unavailable treasures.
   */
  @Test
  public void testAccessInvalidTreasureIndex() {
    treasures.add(SingletonValuables.item("Diamond"));
    assertThrows(IllegalStateException.class, () -> {
      treasures.get(1);
    });
  }

  /**
   * Test error handling when trying to remove from an empty collection,
   * like when a ValuableTransporter tries to take from an empty deposit.
   */
  @Test
  public void testRemoveFromEmptyTreasureCollection() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      treasures.remove(0);
    });
  }

  /**
   * Test error handling when trying to replace a valuable that doesn't exist,
   * similar to trying to modify a non-existent treasure.
   */
  @Test
  public void testReplaceNonexistentTreasure() {
    treasures.add(SingletonValuables.item("Diamond"));
    assertThrows(IndexOutOfBoundsException.class, () -> {
      treasures.set(1, SingletonValuables.item("Ruby"));
    });
  }

  /**
   * Test how the collection's string representation works,
   * useful for logging the kingdom's inventory.
   */
  @Test
  public void testTreasureCollectionToString() {
    assertEquals("{}", treasures.toString());

    treasures.add(SingletonValuables.item("Diamond"));
    // This will show the toString of the Diamond SingletonValuable
    assertTrue(treasures.toString().startsWith("{"));
    assertTrue(treasures.toString().endsWith("}"));

    treasures.add(SingletonValuables.item("GoldNugget"));
    assertTrue(treasures.toString().contains(","));
  }

  /**
   * Test that the Deposit adapter properly uses the ArrayList
   * to store treasures.
   */
  @Test
  public void testDepositUsingArrayList() {
    // Add some treasures to the deposit
    kingdomDeposit.add(SingletonValuables.item("Diamond"));
    kingdomDeposit.add(SingletonValuables.item("GoldNugget"));

    // Check the size and contents
    assertEquals(2, kingdomDeposit.size());
    assertEquals("Diamond", kingdomDeposit.get(0).getValueType());
    assertEquals("GoldNugget", kingdomDeposit.get(1).getValueType());

    // Test removal from deposit
    SingletonValuables removed = kingdomDeposit.take();
    assertEquals("Diamond", removed.getValueType());
    assertEquals(1, kingdomDeposit.size());
  }
}
