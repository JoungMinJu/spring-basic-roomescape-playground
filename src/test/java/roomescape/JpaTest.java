package roomescape;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import roomescape.eventTime.EventTime;
import roomescape.eventTime.EventTimeRepository;

@DataJpaTest
public class JpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventTimeRepository eventTimeRepository;

    @Test
    void 사단계() {
        EventTime eventTime = new EventTime("10:00");
        entityManager.persist(eventTime);
        entityManager.flush();

        EventTime persistEventTime = eventTimeRepository.findById(eventTime.getId())
            .orElse(null);
        assertThat(persistEventTime.getValue())
            .isEqualTo(eventTime.getValue());
    }

}
