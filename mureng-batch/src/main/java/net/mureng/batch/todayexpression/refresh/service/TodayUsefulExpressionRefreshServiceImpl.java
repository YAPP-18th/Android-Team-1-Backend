package net.mureng.batch.todayexpression.refresh.service;

import com.mchange.v1.identicator.IdHashSet;
import lombok.RequiredArgsConstructor;
import net.mureng.core.core.component.NumberRandomizer;
import net.mureng.core.core.exception.MurengException;
import net.mureng.core.todayexpression.entity.TodayUsefulExpression;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import net.mureng.core.todayexpression.repository.TodayUsefulExpressionRepository;
import net.mureng.core.todayexpression.repository.UsefulExpressionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mureng.core.core.message.ErrorMessage.EXPRESSION_REFRESH_FAIL;
import static net.mureng.core.core.message.ErrorMessage.QUESTION_REFRESH_FAIL;

@Service
@RequiredArgsConstructor
public class TodayUsefulExpressionRefreshServiceImpl implements TodayUsefulExpressionRefreshService {
    private static final int MAX_TOLERATE = 1000;

    private final UsefulExpressionRepository usefulExpressionRepository;
    private final TodayUsefulExpressionRepository todayUsefulExpressionRepository;
    private final NumberRandomizer numberRandomizer;

    @Override
    @Transactional
    public void reselectTodayUsefulExpression() {
        List<TodayUsefulExpression> todayUsefulExpressions = todayUsefulExpressionRepository.findAll();
        long[] randomIds = getRandomIds(todayUsefulExpressions.size());
        for (int i = 0; i < todayUsefulExpressions.size(); i++) {
            TodayUsefulExpression todayUsefulExpression = todayUsefulExpressions.get(i);
            todayUsefulExpression.setUsefulExpression(UsefulExpression.builder().expId(randomIds[i]).build());
            todayUsefulExpressionRepository.save(todayUsefulExpression);
        }
        todayUsefulExpressionRepository.flush();
    }

    private long[] getRandomIds(int count) {
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
