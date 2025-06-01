package roomescape.eventTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class EventTimeController {
    private EventTimeService eventTimeService;

    public EventTimeController(EventTimeService eventTimeService) {
        this.eventTimeService = eventTimeService;
    }

    @GetMapping("/times")
    public List<EventTime> list() {
        return eventTimeService.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<EventTime> create(@RequestBody EventTime eventTime) {
        if (eventTime.getValue() == null || eventTime.getValue().isEmpty()) {
            throw new RuntimeException();
        }

        EventTime newEventTime = eventTimeService.save(eventTime);
        return ResponseEntity.created(URI.create("/times/" + newEventTime.getId())).body(newEventTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<AvailableTime>> availableTimes(@RequestParam String date, @RequestParam Long themeId) {
        return ResponseEntity.ok(eventTimeService.getAvailableTime(date, themeId));
    }
}
