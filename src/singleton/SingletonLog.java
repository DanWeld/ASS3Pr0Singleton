package singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Implementation of the Singleton pattern for logging system activity.
 * This class ensures that only one instance of the logging system exists
 */
public class SingletonLog {
  private static volatile SingletonLog instance;
  private static final Object lock = new Object();
  private ArrayList<LogLine> logLines;
  private String logFilePath;
  private DateTimeFormatter formatter;

  /**
   * Private constructor to prevent external instantiation.
   * Initializes the log storage and JSON file.
   */
  private SingletonLog(){
    logLines = new ArrayList<>();
    logFilePath = "kingdom_simulation_log.json";
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Initialize JSON log file
    try (FileWriter writer = new FileWriter(logFilePath)) {
      writer.write("[\n"); // Start of JSON array
      writer.flush();
    } catch (IOException e) {
      System.err.println("Error initializing log file: " + e.getMessage());
    }
  }

  /**
   * Returns the singleton instance of the logging system.
   * Uses double-checked locking pattern for thread safety and performance.
   */
  public static SingletonLog getInstance(){
    if(instance == null){
      synchronized (lock){
        if(instance == null){
          instance = new SingletonLog();
        }
      }
    }
    return instance;
  }

  /**
   * Adds a log entry to both the in-memory log and the JSON file.
   * Records the current time and thread name automatically.
   */
  public void addLog(String text){
    LogLine log = new LogLine(text);
    logLines.add(log);
    System.out.println(text);
    
    // Add to JSON log file
    writeLogToJsonFile(log);
  }
  
  /**
   * Writes a log entry to the JSON file.
   */
  private void writeLogToJsonFile(LogLine logLine) {
    String timestamp = LocalDateTime.now().format(formatter);
    String threadName = Thread.currentThread().getName();
    String logText = logLine.getText().replace("\"", "\\\""); // Escape quotes for JSON
    
    String jsonEntry = String.format(
        "%s{\"timestamp\": \"%s\", \"thread\": \"%s\", \"message\": \"%s\"}",
        logLines.size() > 1 ? ",\n" : "", // Add comma if not first entry
        timestamp,
        threadName,
        logText
    );
    
    try (FileWriter writer = new FileWriter(logFilePath, true)) {
      writer.write(jsonEntry);
      writer.flush();
    } catch (IOException e) {
      System.err.println("Error writing to log file: " + e.getMessage());
    }
  }
  
  /**
   * Finalizes the JSON log file by closing the JSON array.
   */
  public void finalizeLogFile() {
    try (FileWriter writer = new FileWriter(logFilePath, true)) {
      writer.write("\n]"); // End of JSON array
      writer.flush();
    } catch (IOException e) {
      System.err.println("Error finalizing log file: " + e.getMessage());
    }
  }

  /**
   * Simple log method for backward compatibility.
   */
  public void log(){
    System.out.println("This is singleton log");
  }


}

