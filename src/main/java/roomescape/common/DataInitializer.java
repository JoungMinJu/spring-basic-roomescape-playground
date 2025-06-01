package roomescape.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final ThemeRepository themeRepository;
    private final EventTimeRepository eventTimeRepository;
    private final ReservationRepository reservationRepository;

    public DataInitializer(MemberRepository memberRepository,
                           ThemeRepository themeRepository,
                           EventTimeRepository eventTimeRepository,
                           ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.themeRepository = themeRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        memberRepository.save(new Member("어드민", "admin@email.com", "password", "ADMIN"));
        memberRepository.save(new Member("브라운", "brown@email.com", "password", "USER"));

        themeRepository.save(new Theme(null, "테마1", "테마1입니다."));
        themeRepository.save(new Theme(null, "테마2", "테마2입니다."));
        themeRepository.save(new Theme(null, "테마3", "테마3입니다."));

        eventTimeRepository.save(new EventTime("10:00"));
        eventTimeRepository.save(new EventTime("12:00"));
        eventTimeRepository.save(new EventTime( "14:00"));
        eventTimeRepository.save(new EventTime( "16:00"));
        eventTimeRepository.save(new EventTime("18:00"));
        eventTimeRepository.save(new EventTime( "20:00"));

        // 예약은 theme, eventTime 저장 후 조회해서 넣어야 하므로
        var theme1 = themeRepository.findById(1L).orElseThrow();
        var theme2 = themeRepository.findById(2L).orElseThrow();
        var theme3 = themeRepository.findById(3L).orElseThrow();

        var time1 = eventTimeRepository.findById(1L).orElseThrow();
        var time2 = eventTimeRepository.findById(2L).orElseThrow();
        var time3 = eventTimeRepository.findById(3L).orElseThrow();

        reservationRepository.save(new Reservation("어드민", "2024-03-01", time1, theme1));
        reservationRepository.save(new Reservation("어드민", "2024-03-01", time2, theme2));
        reservationRepository.save(new Reservation("어드민", "2024-03-01", time3, theme3));
    }
}
