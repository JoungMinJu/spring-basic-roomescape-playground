package roomescape.reservation;

import roomescape.theme.Theme;
import roomescape.eventTime.EventTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private EventTime eventTime;
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
