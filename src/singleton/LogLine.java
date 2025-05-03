package singleton;

import java.time.LocalDateTime;

public class LogLine {
  private String text;
  private LocalDateTime timestamp;
  private String threadName;


  public LogLine(String text)
  {
    this.text = text;
    this.timestamp = LocalDateTime.now();
    this.threadName = Thread.currentThread().getName();
  }

  public String getText()
  {
    return text;
  }
  
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
  
  public String getThreadName() {
    return threadName;
  }

  @Override public String toString()
  {
    return "[" + timestamp + "] " + threadName + ": " + text;
  }
}
