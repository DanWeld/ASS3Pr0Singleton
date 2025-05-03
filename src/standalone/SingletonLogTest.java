package standalone;

import singleton.SingletonLog;

/**
 * Standalone test class to specifically test the Singleton Log implementation.
 */
public class SingletonLogTest {
    
    public static void main(String[] args) {
        System.out.println("=== Testing SingletonLog Implementation ===");
        
        // Test 1: Verify that getInstance returns the same instance
        System.out.println("\nTEST 1: Multiple getInstance calls return same instance");
        SingletonLog instance1 = SingletonLog.getInstance();
        SingletonLog instance2 = SingletonLog.getInstance();
        
        // Check if references are the same (should be true for a singleton)
        boolean sameReference = (instance1 == instance2);
        System.out.println("Same reference: " + sameReference + " (expected: true)");
        
        // Test 2: Test logging functionality
        System.out.println("\nTEST 2: Log message functionality");
        instance1.addLog("Test log message from instance1");
        instance2.addLog("Test log message from instance2 (should be same instance)");
        
        // Test 3: Test resetting the singleton (for testing purposes)
        System.out.println("\nTEST 3: Reset singleton for testing");
        
        // Reset the singleton
        SingletonLog.resetInstance();
        
        // Get a new instance
        SingletonLog newInstance = SingletonLog.getInstance();
        
        // Check we have a new instance
        boolean differentReference = (instance1 != newInstance);
        System.out.println("New instance created after reset: " + differentReference + " (expected: true)");
        
        // Test 4: Test old-style log method
        System.out.println("\nTEST 4: Basic log method");
        newInstance.log();
        
        // Finalize the log file (important for proper JSON format)
        newInstance.finalizeLogFile();
        
        System.out.println("\nAll SingletonLog tests completed!");
    }
}
