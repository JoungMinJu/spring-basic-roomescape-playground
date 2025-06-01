package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private EventTimeRepository eventTimeRepository;
    private ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, EventTimeRepository eventTimeRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.themeRepository = themeRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        EventTime eventTime = eventTimeRepository.findById(reservationRequest.getTime())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간"));

        Theme theme = themeRepository.findById(reservationRequest.getTheme())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테마"));

        Reservation reservation = new Reservation(
            reservationRequest.getName(),
            reservationRequest.getDate(),
            eventTime,
            theme
        );

        return new ReservationResponse(reservation.getId(), reservationRequest.getName(), reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getValue());
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(it -> new ReservationResponse(it.getId(), it.getName(), it.getTheme().getName(), it.getDate(), it.getTime().getValue()))
                .toList();
    }
}
