package roomescape.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import roomescape.member.Member;
import roomescape.theme.Theme;
import roomescape.eventTime.EventTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_time_id")
    private EventTime eventTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(Long id, Member member, String date, EventTime eventTime, Theme theme) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.eventTime = eventTime;
        this.theme = theme;
        this.status = ReservationStatus.RESERVED;
    }

    public Reservation(Member member, String date, EventTime eventTime, Theme theme) {
        this.member = member;
        this.date = date;
        this.eventTime = eventTime;
        this.theme = theme;
        this.status = ReservationStatus.RESERVED;
    }

    public Reservation() {

    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return member.getName();
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

    public ReservationStatus getStatus() {
        return status;
    }
}
