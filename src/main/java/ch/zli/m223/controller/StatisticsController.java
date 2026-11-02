package ch.zli.m223.controller;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.TimeSummary;
import ch.zli.m223.service.EntryService;

@Path("/statistics")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Statistics", description = "Statistics about recorded working time")
public class StatisticsController {

  @Inject
  EntryService entryService;

  @GET
  @Path("/time-summaries")
  @Operation(summary = "Returns time summaries per day.", description = "Returns the total recorded working time for each day.")
  public Map<String, String> getTimeSummaries() {

    List<TimeSummary> summaries = entryService.getTimeSummaries();

    Map<String, String> result = new LinkedHashMap<>();

    for (TimeSummary summary : summaries) {

      String date = summary.getDate().toString();

      String duration = formatDuration(summary.getDuration());

      result.put(date, duration);
    }

    return result;
  }

  private String formatDuration(Duration duration) {

    long seconds = duration.getSeconds();

    long hours = seconds / 3600;
    long minutes = (seconds % 3600) / 60;
    long remainingSeconds = seconds % 60;

    return String.format(
        "%02d:%02d:%02d",
        hours,
        minutes,
        remainingSeconds);
  }
}
