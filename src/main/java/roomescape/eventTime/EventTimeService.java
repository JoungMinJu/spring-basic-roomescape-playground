package roomescape.eventTime;

import org.springframework.stereotype.Service;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationDao;

import java.util.List;

@Service
public class EventTimeService {
    private EventTimeRepository eventTimeRepository;
    private ReservationDao reservationDao;

    public EventTimeService(EventTimeRepository eventTimeRepository, ReservationDao reservationDao) {
        this.eventTimeRepository = eventTimeRepository;
        this.reservationDao = reservationDao;
    }

    public List<AvailableTime> getAvailableTime(String date, Long themeId) {
        List<Reservation> reservations = reservationDao.findByDateAndThemeId(date, themeId);
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
