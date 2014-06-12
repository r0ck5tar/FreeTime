import android.test.InstrumentationTestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.unice.polytech.calendarmodule.StringRecurrenceBuilder;

/**
 * Created by Yoann on 11/06/2014.
 */
public class RecurrenceBuilderTest extends InstrumentationTestCase {
    public void testRecurrenceBuilder(){
        StringRecurrenceBuilder rb = new StringRecurrenceBuilder();
        GregorianCalendar g = new GregorianCalendar();
        g.set(Calendar.YEAR,1997);
        g.set(Calendar.MONTH,11);
        g.set(Calendar.DAY_OF_MONTH,24);
        g.set(Calendar.HOUR,0);
        g.set(Calendar.MINUTE,0);
        g.set(Calendar.SECOND, 0);

        rb.freqByDay().until(g);
        assertEquals("RRULE:FREQ=DAILY;UNTIL=19971224T", rb.getRRule());
    }
}
