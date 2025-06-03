package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;
import roomescape.waiting.WaitingRepository;
import roomescape.waiting.WaitingResponse;
import roomescape.waiting.WaitingWithRank;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private EventTimeRepository eventTimeRepository;
    private ThemeRepository themeRepository;
    private MemberRepository memberRepository;
    private WaitingRepository waitingRepository;

    private ReservationService(ReservationRepository reservationRepository, EventTimeRepository eventTimeRepository,
                               ThemeRepository themeRepository, MemberRepository memberRepository,
                               WaitingRepository waitingRepository) {
        this.reservationRepository = reservationRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
        this.waitingRepository = waitingRepository;
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
        return new ReservationResponse(reservation.getId(), reservationRequest.getName(),
                                       reservation.getTheme().getName(), reservation.getDate(),
                                       reservation.getTime().getValue());
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
            .map(it -> new ReservationResponse(it.getId(), it.getMemberName(), it.getTheme().getName(), it.getDate(),
                                               it.getTime().getValue()))
            .toList();
    }

    public List<MyReservationResponse> findAllMine(Long memberId) {
        List<Reservation> reservations = reservationRepository.findAllByMemberId(memberId);
        List<MyReservationResponse> reservationResponses = reservations.stream()
            .map(MyReservationResponse::from)
            .toList();

        List<WaitingWithRank> waitings = waitingRepository.findAllWithRankByMemberId(memberId);
        List<MyReservationResponse> waitingResponses = waitings.stream()
            .map(WaitingResponse::from)
            .map(MyReservationResponse::from)
            .toList();

        List<MyReservationResponse> all = new ArrayList<>();
        all.addAll(reservationResponses);
        all.addAll(waitingResponses);
        return all;
    }
}
