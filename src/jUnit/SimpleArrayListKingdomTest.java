package jUnit;

import utility.collection.ArrayList;
import utility.collection.ListADT;
import singleton.SingletonValuables;
import producer_consumer_adapter.Deposit;
import producer_consumer_adapter.DepositValuables;

/**
 * This is a simplified version of the ArrayList test that doesn't rely on JUnit.
 */
public class SimpleArrayListKingdomTest {
    
    public static void main(String[] args) {
        System.out.println("Running simplified Kingdom ArrayList tests...");
        
        // Create the test objects
        ListADT<SingletonValuables> treasures = new ArrayList<>();
        DepositValuables<SingletonValuables> kingdomDeposit = new Deposit<>(5);
        
        // Test 1: Empty collection tests
        System.out.println("\nTest 1: Empty collection");
        System.out.println("Size: " + treasures.size() + " (expected: 0)");
        System.out.println("Is Empty: " + treasures.isEmpty() + " (expected: true)");
        System.out.println("ToString: " + treasures.toString() + " (expected: {})");
          // Test 2: Adding valuables
        System.out.println("\nTest 2: Adding valuables");
        SingletonValuables instance = SingletonValuables.getInstance();
        instance.setValueType("Diamond");
        SingletonValuables diamond = SingletonValuables.item("Diamond");
        instance.setValueType("GoldNugget");
        SingletonValuables gold = SingletonValuables.item("GoldNugget");
        treasures.add(diamond);
        treasures.add(gold);
        
        System.out.println("Size after adding: " + treasures.size() + " (expected: 2)");
        System.out.println("Is Empty after adding: " + treasures.isEmpty() + " (expected: false)");
        System.out.println("First item type: " + treasures.get(0).getValueType() + " (expected: Diamond)");
        System.out.println("Second item type: " + treasures.get(1).getValueType() + " (expected: GoldNugget)");
          // Test 3: Adding valuables at specific positions
        System.out.println("\nTest 3: Adding at index");
        instance.setValueType("Ruby");
        SingletonValuables ruby = SingletonValuables.item("Ruby");
        treasures = new ArrayList<>(); // Reset for this test
        treasures.add(0, diamond);
        treasures.add(1, gold);
        treasures.add(1, ruby);
        
        System.out.println("Size after adding at index: " + treasures.size() + " (expected: 3)");
        System.out.println("First item type: " + treasures.get(0).getValueType() + " (expected: Diamond)");
        System.out.println("Second item type: " + treasures.get(1).getValueType() + " (expected: Ruby)");
        System.out.println("Third item type: " + treasures.get(2).getValueType() + " (expected: GoldNugget)");
        
        // Test 4: Contains
        System.out.println("\nTest 4: Contains");
        System.out.println("Contains Diamond: " + treasures.contains(diamond) + " (expected: true)");
        instance.setValueType("Silver");
        System.out.println("Contains Silver: " + treasures.contains(SingletonValuables.item("Silver")) + " (expected: false)");
          // Test 5: Finding index
        System.out.println("\nTest 5: indexOf");
        System.out.println("Index of Diamond: " + treasures.indexOf(diamond) + " (expected: 0)");
        System.out.println("Index of Gold: " + treasures.indexOf(gold) + " (expected: 2)");
        System.out.println("Index of Ruby: " + treasures.indexOf(ruby) + " (expected: 1)");
        instance.setValueType("Silver");
        System.out.println("Index of Silver: " + treasures.indexOf(SingletonValuables.item("Silver")) + " (expected: -1)");
        
        // Test 6: Removing by reference
        System.out.println("\nTest 6: Remove by reference");
        treasures = new ArrayList<>(); // Reset for this test
        treasures.add(diamond);
        treasures.add(gold);
        treasures.add(ruby);
        
        SingletonValuables removed = treasures.remove(gold);
        System.out.println("Removed value type: " + removed.getValueType() + " (expected: GoldNugget)");
        System.out.println("Size after removal: " + treasures.size() + " (expected: 2)");
        System.out.println("Contains Gold after removal: " + treasures.contains(gold) + " (expected: false)");
        
        // Test 7: Removing by index
        System.out.println("\nTest 7: Remove by index");
        treasures = new ArrayList<>();
        treasures.add(diamond);
        treasures.add(gold);
        treasures.add(ruby);
        
        removed = treasures.remove(1);
        System.out.println("Removed value type: " + removed.getValueType() + " (expected: GoldNugget)");
        System.out.println("Size after removal: " + treasures.size() + " (expected: 2)");
        System.out.println("First item: " + treasures.get(0).getValueType() + " (expected: Diamond)");
        System.out.println("Second item: " + treasures.get(1).getValueType() + " (expected: Ruby)");
        
        // Test 8: Replacing values
        System.out.println("\nTest 8: Set/replace");
        treasures = new ArrayList<>();
        treasures.add(diamond);
        treasures.add(gold);
        
        SingletonValuables original = treasures.get(1);
        treasures.set(1, ruby);
        System.out.println("Original value type: " + original.getValueType() + " (expected: GoldNugget)");
        System.out.println("Size after replace: " + treasures.size() + " (expected: 2)");
        System.out.println("First item after replace: " + treasures.get(0).getValueType() + " (expected: Diamond)");
        System.out.println("Second item after replace: " + treasures.get(1).getValueType() + " (expected: Ruby)");
          // Test 9: Error handling (just report attempts, don't crash the test)
        System.out.println("\nTest 9: Error handling");
        treasures = new ArrayList<>();
        try {
            treasures.get(0);
            System.out.println("ERROR: Should have thrown exception when getting from empty list");
        } catch (Exception e) {
            System.out.println("Correctly threw exception when getting from empty list: " + e.getClass().getName());
        }
        
        treasures.add(diamond);
        try {
            treasures.get(1);
            System.out.println("ERROR: Should have thrown exception when getting invalid index");
        } catch (Exception e) {
            System.out.println("Correctly threw exception when accessing invalid index: " + e.getClass().getName());
        }
        
        treasures = new ArrayList<>();
        try {
            treasures.remove(0);
            System.out.println("ERROR: Should have thrown exception when removing from empty list");
        } catch (Exception e) {
            System.out.println("Correctly threw exception when removing from empty list: " + e.getClass().getName());
        }
        
        treasures.add(diamond);
        try {
            treasures.set(1, ruby);
            System.out.println("ERROR: Should have thrown exception when setting invalid index");
        } catch (Exception e) {
            System.out.println("Correctly threw exception when setting invalid index: " + e.getClass().getName());
        }
        
        // Test 10: Testing the Deposit
        System.out.println("\nTest 10: Deposit");
        kingdomDeposit.add(diamond);
        kingdomDeposit.add(gold);
          System.out.println("Deposit size: " + kingdomDeposit.getSize() + " (expected: 2)");
        System.out.println("First item: " + kingdomDeposit.get(0).getValueType() + " (expected: Diamond)");
        
        removed = kingdomDeposit.take();
        System.out.println("Removed item: " + removed.getValueType() + " (expected: Diamond)");
        System.out.println("Deposit size after removal: " + kingdomDeposit.getSize() + " (expected: 1)");
        
        System.out.println("\nAll tests completed!");
    }
}
