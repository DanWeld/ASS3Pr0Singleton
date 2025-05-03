package standalone;

import singleton.SingletonValuables;

/**
 * Standalone test class to specifically test the Singleton behavior
 * of the SingletonValuables class.
 */
public class SingletonTest {
    
    public static void main(String[] args) {
        System.out.println("=== Testing Singleton Pattern Implementation ===");
        
        // Test 1: Verify that getInstance returns the same instance
        System.out.println("\nTEST 1: Multiple getInstance calls return same instance");
        SingletonValuables instance1 = SingletonValuables.getInstance();
        SingletonValuables instance2 = SingletonValuables.getInstance();
        
        // Check if references are the same (should be true for a singleton)
        boolean sameReference = (instance1 == instance2);
        System.out.println("Same reference: " + sameReference + " (expected: true)");
        
        // Test 2: Verify that state is shared between references
        System.out.println("\nTEST 2: State is shared between references");
        
        // Set a value type with one reference
        instance1.setValueType("Platinum");
        
        // Check value type from other reference
        String valueType = instance2.getValueType();
        System.out.println("Value type from instance2: " + valueType + " (expected: Platinum)");
        
        // Test 3: Test the "remembered type" mechanism for testing
        System.out.println("\nTEST 3: Remembered type mechanism for testing");
        
        // Create a virtual instance with a specific type
        SingletonValuables diamond = SingletonValuables.item("Diamond");
        
        // Change the singleton's current type
        SingletonValuables.getInstance().setValueType("Gold");
        
        // Verify that the diamond reference still remembers its type
        System.out.println("Diamond value type: " + diamond.getValueType() + " (expected: Diamond)");        // Test 4: Verify value worth tracking
        System.out.println("\nTEST 4: Worth calculations");
        
        // Reset the instance to start with a clean state
        SingletonValuables.resetInstance();
        SingletonValuables instance = SingletonValuables.getInstance();
        instance.resetTotalWorth();
        
        System.out.println("Initial total worth: " + instance.getTotalWorthInTreasure() + " (expected: 0)");
        
        // Get worth of a few valuables using the singleton
        instance.setValueType("Diamond");
        int diamond1Worth = instance.getValueWorth();
        System.out.println("Diamond 1 worth: " + diamond1Worth);
        
        instance.setValueType("GoldNugget");
        int goldWorth = instance.getValueWorth();
        System.out.println("Gold worth: " + goldWorth);
        
        // Check the running total
        int expectedTotal = diamond1Worth + goldWorth;
        int actualTotal = instance.getTotalWorthInTreasure();
        System.out.println("Total worth: " + actualTotal + " (expected: " + expectedTotal + ")");
        System.out.println("Total worth matches expected: " + (actualTotal == expectedTotal) + " (expected: true)");
        
        // Test 5: Reset for testing
        System.out.println("\nTEST 5: Reset for testing");
        
        // Reset the singleton
        SingletonValuables.resetInstance();
        
        // Get a new instance
        SingletonValuables newInstance = SingletonValuables.getInstance();
        
        // Check that the total worth has been reset
        System.out.println("Total worth after reset: " + newInstance.getTotalWorthInTreasure() + " (expected: 0)");
        
        // Check we have a new instance
        boolean differentReference = (instance1 != newInstance);
        System.out.println("New instance created: " + differentReference + " (expected: true)");
        
        System.out.println("\nAll Singleton tests completed!");
    }
}
