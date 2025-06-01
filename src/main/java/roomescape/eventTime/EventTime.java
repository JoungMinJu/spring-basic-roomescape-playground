package roomescape.eventTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EventTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String timeValue;

    public EventTime(Long id, String timeValue) {
        this.id = id;
        this.timeValue = timeValue;
    }

    public EventTime(String timeValue) {
        this.timeValue = timeValue;
    }

    public EventTime() {

    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return timeValue;
    }
}
