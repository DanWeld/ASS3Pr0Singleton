# Assignment3Pro - Kingdom Simulation

## Overview
This project demonstrates the implementation of the Singleton design pattern in a Kingdom simulation application. The simulation models a kingdom with Miners extracting valuables, Transporters moving them to a treasury, and various actors interacting with the treasure room.

## Project Structure

```
Assignment3Pro/
├── src/
│   ├── jUnit/                       # JUnit test classes
│   │   ├── KingdomArrayListTest.java # Tests for ArrayList implementation
│   │   └── SimpleArrayListKingdomTest.java # Simplified tests
│   ├── singleton/                   # Singleton implementations
│   │   ├── LogLine.java             # Helper class for logging
│   │   ├── SingletonLog.java        # Logging system as Singleton
│   │   └── SingletonValuables.java  # Valuable items as Singleton
│   ├── producer_consumer_adapter/   # Producer-Consumer pattern implementation
│   │   ├── Deposit.java             # Thread-safe deposit adapter
│   │   ├── DepositValuables.java    # Deposit interface
│   │   ├── Miner.java               # Producer of valuables
│   │   └── ValuableTransporter.java # Consumer of valuables
│   ├── readers_writers_proxy/       # Readers-Writers pattern implementation
│   │   ├── TreasureRoom.java        # Central storage for valuables
│   │   ├── TreasureRoomDoor.java    # Interface for accessing the treasure room
│   │   ├── TreasureRoomGuardsman.java # Proxy for controlling access
│   │   ├── Accountant.java          # Reader role
│   │   └── King.java                # Writer role
│   └─── README.md                        # This file
```

## Design Patterns Implementation

### Singleton Pattern
The project implements two Singleton classes:
1. **SingletonValuables**: 
   - Central manager for valuable items in the kingdom
   - Thread-safe implementation with double-checked locking
   - Provides methods to set/get valuable properties
   - Includes instance reset capability for testing

2. **SingletonLog**:
   - Centralized logging system for all activities
   - Thread-safe implementation with synchronized access
   - Logs activities to console and JSON file
   - Used by all actors to record their actions

### Producer-Consumer Pattern with Adapter
1. **Deposit (Adapter)**:
   - Adapts ArrayList to the DepositValuables interface
   - Provides thread-safe access to the collection
   - Implements blocking behavior for producers and consumers

2. **Miner (Producer)**:
   - Produces valuable items
   - Adds them to the Deposit
   - Uses the Singleton pattern to log activities

3. **ValuableTransporter (Consumer)**:
   - Consumes valuable items from the Deposit
   - Transports them to the Treasure Room
   - Collects items until reaching a target value

### Readers-Writers Pattern with Proxy
1. **TreasureRoomGuardsman (Proxy)**:
   - Controls access to the TreasureRoom
   - Implements the TreasureRoomDoor interface
   - Ensures thread-safe access to the treasure room

2. **Treasure Room Access**:
   - Accountant (Reader): Views but doesn't modify treasure
   - King (Writer): Takes items from the treasure room

## JUnit Testing
The project includes JUnit tests to verify the functionality of:
1. **ArrayList Implementation**: Tests for the custom ArrayList
2. **Deposit Functionality**: Tests for the adapter implementation
3. **Kingdom Integration**: Tests for the complete system

## Running the Tests
To run all tests:
```
.\run_tests.ps1
```

## Advanced Singleton Features
The implementation includes several advanced features:
1. **Thread-Safety**: Using double-check locking with volatile keyword
2. **Reset Capability**: For testing purposes
3. **Central Logging**: For monitoring all activities in the kingdom

## Conclusion
This project demonstrates how design patterns can be applied to create a thread-safe, well-structured simulation. The Singleton pattern provides centralized resources, the Producer-Consumer pattern with Adapter handles resource collection and movement, and the Proxy pattern controls access to shared resources.
