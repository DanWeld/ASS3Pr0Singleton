package standalone;

import singleton.SingletonValuables;
import utility.collection.ArrayList;
import utility.collection.ListADT;

/**
 * Simple test for the ArrayList and SingletonValuables without JUnit dependencies
 */
public class StandaloneArrayListTest {
    
    public static void main(String[] args) {
        System.out.println("=== Testing ArrayList with SingletonValuables ===");
        
        ListADT<SingletonValuables> treasures = new ArrayList<>();
        
        // Test initial state
        System.out.println("Initial state:");
        System.out.println("  Size: " + treasures.size() + " (expected: 0)");
        System.out.println("  Is Empty: " + treasures.isEmpty() + " (expected: true)");
          // Create SingletonValuables instances
        SingletonValuables instance = SingletonValuables.getInstance();
        instance.setValueType("Diamond");
        SingletonValuables diamond = SingletonValuables.item("Diamond");
        instance.setValueType("GoldNugget");
        SingletonValuables gold = SingletonValuables.item("GoldNugget");
        instance.setValueType("Ruby");
        SingletonValuables ruby = SingletonValuables.item("Ruby");
        
        // Test adding valuables
        treasures.add(diamond);
        treasures.add(gold);
        
        System.out.println("\nAfter adding two items:");
        System.out.println("  Size: " + treasures.size() + " (expected: 2)");
        System.out.println("  Is Empty: " + treasures.isEmpty() + " (expected: false)");
        System.out.println("  First item: " + treasures.get(0).getValueType() + " (expected: Diamond)");
        System.out.println("  Second item: " + treasures.get(1).getValueType() + " (expected: GoldNugget)");
        
        // Test contains
        System.out.println("\nContains tests:");
        System.out.println("  Contains Diamond: " + treasures.contains(diamond) + " (expected: true)");
        System.out.println("  Contains Ruby: " + treasures.contains(ruby) + " (expected: false)");
        
        // Test removing
        SingletonValuables removed = treasures.remove(0);
        System.out.println("\nAfter removing first item:");
        System.out.println("  Removed item: " + removed.getValueType() + " (expected: Diamond)");
        System.out.println("  Size: " + treasures.size() + " (expected: 1)");
        System.out.println("  First item: " + treasures.get(0).getValueType() + " (expected: GoldNugget)");
        
        // Test adding at index
        treasures.add(0, ruby);
        System.out.println("\nAfter adding Ruby at index 0:");
        System.out.println("  Size: " + treasures.size() + " (expected: 2)");
        System.out.println("  First item: " + treasures.get(0).getValueType() + " (expected: Ruby)");
        System.out.println("  Second item: " + treasures.get(1).getValueType() + " (expected: GoldNugget)");
        
        System.out.println("\nAll ArrayList tests completed!");
    }
}
