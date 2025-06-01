package roomescape.eventTime;

import org.springframework.stereotype.Service;
import roomescape.reservation.Reservation;

import java.util.List;
import roomescape.reservation.ReservationRepository;

@Service
public class EventTimeService {
    private EventTimeRepository eventTimeRepository;
    private ReservationRepository reservationRepository;

    public EventTimeService(EventTimeRepository eventTimeRepository, ReservationRepository reservationRepository) {
        this.eventTimeRepository = eventTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<AvailableTime> getAvailableTime(String date, Long themeId) {
        List<Reservation> reservations = reservationRepository.findByDateAndThemeId(date, themeId);
        List<EventTime> eventTimes = eventTimeRepository.findAll();

        return eventTimes.stream()
                .map(time -> new AvailableTime(
                        time.getId(),
                        time.getValue(),
                        reservations.stream()
                                .anyMatch(reservation -> reservation.getTime().getId().equals(time.getId()))
                ))
                .toList();
    }

    public List<EventTime> findAll() {
        return eventTimeRepository.findAll();
    }

    public EventTime save(EventTime eventTime) {
        return eventTimeRepository.save(eventTime);
    }

    public void deleteById(Long id) {
        eventTimeRepository.deleteById(id);
    }
}
