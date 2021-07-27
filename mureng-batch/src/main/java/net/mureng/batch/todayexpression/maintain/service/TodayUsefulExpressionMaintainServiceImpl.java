package net.mureng.batch.todayexpression.maintain.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.component.NumberRandomizer;
import net.mureng.core.core.exception.business.MurengException;
import net.mureng.core.todayexpression.entity.TodayUsefulExpression;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import net.mureng.core.todayexpression.repository.TodayUsefulExpressionRepository;
import net.mureng.core.todayexpression.repository.UsefulExpressionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mureng.core.core.message.ErrorMessage.EXPRESSION_REFRESH_FAIL;

@Service
@RequiredArgsConstructor
public class TodayUsefulExpressionMaintainServiceImpl implements TodayUsefulExpressionMaintainService {
    private static final int MAXIMUM_USEFUL_EXPRESSION_COUNT = 2;
    private static final int MAX_TOLERATE = 1000;

    private final UsefulExpressionRepository usefulExpressionRepository;
    private final TodayUsefulExpressionRepository todayUsefulExpressionRepository;
    private final NumberRandomizer numberRandomizer;

    // 2개까지 유지
    public void maintain() {
        List<TodayUsefulExpression> todayUsefulExpressions = todayUsefulExpressionRepository.findAll();
        if (todayUsefulExpressions.size() == MAXIMUM_USEFUL_EXPRESSION_COUNT) {
            return;
        }

        if (todayUsefulExpressions.size() < MAXIMUM_USEFUL_EXPRESSION_COUNT) {
            int rest = MAXIMUM_USEFUL_EXPRESSION_COUNT - todayUsefulExpressions.size();
            long[] randomIds = getRandomIds(rest);
            for (long randomId : randomIds) {
                TodayUsefulExpression todayUsefulExpression = TodayUsefulExpression.builder()
                        .usefulExpression(UsefulExpression.builder()
                                .expId(randomId).build())
                        .build();
                todayUsefulExpressionRepository.save(todayUsefulExpression);
            }
        } else {
            for (int i = todayUsefulExpressions.size() - 1; i > MAXIMUM_USEFUL_EXPRESSION_COUNT; i--) {
                todayUsefulExpressionRepository.delete(todayUsefulExpressions.get(i));
            }
        }
        todayUsefulExpressionRepository.flush();
    }

    private long[] getRandomIds(int count) { // TODO 중복 제거
        Set<Long> checked = new HashSet<>();
        long[] randomIds = new long[count];
        int retry = 0;
        int index = 0;
        while (retry < MAX_TOLERATE) {
            retry++;

            long randomId = getRandomId();
            if (checked.contains(randomId)) {
                continue;
            }

            checked.add(randomId);
            randomIds[index] = randomId;
            if (++index == count) {
                return randomIds;
            }
        }

        throw new MurengException(EXPRESSION_REFRESH_FAIL);
    }

    private long getRandomId() {
        int highestId = getHighestId();
        for (int i = 0; i < MAX_TOLERATE; i++) {
            long randomId = numberRandomizer.getRandomInt(highestId);
            if (! usefulExpressionRepository.existsById(randomId)) {
                continue;
            }

            return randomId;
        }
        return highestId;
    }

    private int getHighestId() {
        return (int)(long)usefulExpressionRepository.findAll(PageRequest.of(0, 1,
                Sort.by(Sort.Direction.DESC, "expId")))
                .getContent().get(0).getExpId();
    }
}
