package roomescape.eventTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTimeRepository extends JpaRepository<EventTime, Long> {


}
