package ch.zli.m223.dto;

import java.time.Duration;
import java.time.LocalDate;

public class TimeSummary {

  private LocalDate date;
  private Duration duration;

  public TimeSummary(LocalDate date, Duration duration) {
    this.date = date;
    this.duration = duration;
  }

  public LocalDate getDate() {
    return date;
  }

  public Duration getDuration() {
    return duration;
  }
}
