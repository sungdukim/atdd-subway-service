package study.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.application.SectionService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("단위 테스트 - SpringExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class SpringExtensionTest {

    @MockBean
    private LineRepository lineRepository;
    @MockBean
    private SectionService sectionService;

    @Test
    void findAllLines() {
        // given
        when(lineRepository.findAll()).thenReturn(Lists.newArrayList(new Line()));
        LineService lineService = new LineService(lineRepository, sectionService);

        // when
        List<LineResponse> responses = lineService.findLines();

        // then
        assertThat(responses).hasSize(1);
    }
}
