package net.mureng.core.core.component;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateFactory {
    public Date now() {
        return new Date();
    }

    /**
     * 현재 날짜로부터 더한(혹은 뺀) 결과를 리턴
     * @param field {@link Calendar} 의 상수 필드 (ex: Calendar.MONTH)
     * @param amount 변동할 양
     * @return 결과 Date
     * @see Calendar
     */
    public Date addFromNow(int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(field, amount);
        return cal.getTime();
    }
}
