package standalone;

import producer_consumer_adapter.Deposit;

/**
 * A simple standalone test that focuses only on the Deposit class and its get method
 */
public class SimpleDepositTest {
    
    public static void main(String[] args) {
        System.out.println("Starting Simple Deposit Test...");
        
        // Create a simple deposit with String items to avoid dependencies
        Deposit<String> deposit = new Deposit<>(5);
        
        // Add some items
        deposit.add("First Item");
        deposit.add("Second Item");
        
        // Check the size
        System.out.println("Deposit size: " + deposit.getSize());
        
        // Try to get items by index
        try {
            String item0 = deposit.get(0);
            System.out.println("Successfully got item at index 0: " + item0);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to get item at index 0: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            String item1 = deposit.get(1);
            System.out.println("Successfully got item at index 1: " + item1);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to get item at index 1: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Test a clearly invalid index
        try {
            String invalidItem = deposit.get(-1);
            System.out.println("This should not print! Got invalid item: " + invalidItem);
        } catch (Exception e) {
            System.out.println("Correctly failed with invalid index -1: " + e.getMessage());
        }
        
        System.out.println("Simple Deposit Test completed");
    }
}
