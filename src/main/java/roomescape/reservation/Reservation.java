package roomescape.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import roomescape.theme.Theme;
import roomescape.eventTime.EventTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_time_id")
    private EventTime eventTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public Reservation(Long id, String name, String date, EventTime eventTime, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.eventTime = eventTime;
        this.theme = theme;
    }

    public Reservation(String name, String date, EventTime eventTime, Theme theme) {
        this.name = name;
        this.date = date;
        this.eventTime = eventTime;
        this.theme = theme;
    }

    public Reservation() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public EventTime getTime() {
        return eventTime;
    }

    public Theme getTheme() {
        return theme;
    }
}
