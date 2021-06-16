package net.mureng.core.core.component;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.setTime(new Date());
        calendar.add(field, amount);
        return calendar.getTime();
    }
}
