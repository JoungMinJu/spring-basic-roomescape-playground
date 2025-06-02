package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private EventTimeRepository eventTimeRepository;
    private ThemeRepository themeRepository;

    private MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, EventTimeRepository eventTimeRepository, ThemeRepository themeRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        EventTime eventTime = eventTimeRepository.findById(reservationRequest.getTime())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간"));

        Theme theme = themeRepository.findById(reservationRequest.getTheme())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테마"));

        Member member = memberRepository.findByName(reservationRequest.getName())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        Reservation reservation = new Reservation(
            member,
            reservationRequest.getDate(),
            eventTime,
            theme
        );

        reservationRepository.save(reservation);
        return new ReservationResponse(reservation.getId(), reservationRequest.getName(), reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getValue());
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(it -> new ReservationResponse(it.getId(), it.getMemberName(), it.getTheme().getName(), it.getDate(), it.getTime().getValue()))
                .toList();
    }

    public List<MyReservationResponse> findAllMine(Long memberId) {
        return reservationRepository.findAllByMemberId(memberId).stream()
            .map(MyReservationResponse::from)
            .toList();
    }
}
