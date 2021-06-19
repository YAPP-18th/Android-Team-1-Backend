package net.mureng.core.core.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumberRandomizer {
    private final Random rand = new Random();

    /**
     *  무작위 양의 정수를 구한다.
     * @param maximum 나올 수 있는 최대 숫자
     * @return 랜덤 숫자
     */
    public int getRandomInt(int maximum) {
        rand.setSeed(System.currentTimeMillis());
        return rand.nextInt(maximum) + 1;
    }
}
