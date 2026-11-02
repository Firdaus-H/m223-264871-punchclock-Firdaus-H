package ch.zli.m223.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.zli.m223.dto.TimeSummary;
import ch.zli.m223.model.Entry;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeSummaryService {

  public List<TimeSummary> calculateSummaryPerDay(List<Entry> entries) {

    Map<LocalDate, Duration> summaries = new TreeMap<>();

    for (Entry entry : entries) {

      LocalDate date = entry.getCheckIn().toLocalDate();

      Duration duration = Duration.between(
          entry.getCheckIn(),
          entry.getCheckOut());

      summaries.merge(date, duration, Duration::plus);
    }

    List<TimeSummary> results = new ArrayList<>();

    for (Map.Entry<LocalDate, Duration> summary : summaries.entrySet()) {

      results.add(new TimeSummary(
          summary.getKey(),
          summary.getValue()));
    }

    return results;
  }
}
