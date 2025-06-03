package roomescape.waiting;

import org.springframework.stereotype.Service;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;

@Service
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final EventTimeRepository eventTimeRepository;
    private final ThemeRepository themeRepository;
    private final MemberRepository memberRepository;

    public WaitingService(WaitingRepository waitingRepository,
                          EventTimeRepository eventTimeRepository,
                          ThemeRepository themeRepository,
                          MemberRepository memberRepository) {
        this.waitingRepository = waitingRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
    }

    public WaitingResponse create(WaitingRequest request) {
        EventTime time = eventTimeRepository.findById(request.getTime())
            .orElseThrow(() -> new IllegalArgumentException("시간 없음"));
        Theme theme = themeRepository.findById(request.getTheme())
            .orElseThrow(() -> new IllegalArgumentException("테마 없음"));
        Member member = memberRepository.findByName(request.getName())
            .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Waiting waiting = new Waiting(member, request.getDate(), time, theme);
        waitingRepository.save(waiting);
        return WaitingResponse.from(waiting);
    }

    public void delete(Long id) {
        waitingRepository.deleteById(id);
    }
}

