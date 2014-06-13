import android.test.InstrumentationTestCase;


import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.unice.polytech.calendarmodule.IndexDayOutOfBoundException;
import fr.unice.polytech.calendarmodule.IndexMonthOutOfBoundException;
import fr.unice.polytech.calendarmodule.RecurrenceStringBuilder;

/**
 * Created by Yoann on 11/06/2014.
 */
public class RecurrenceStringBuilderTest extends InstrumentationTestCase {
    public void testRecurrenceBuilder(){
        GregorianCalendar g1 = new GregorianCalendar(1997,11,24,0,0,0);
        GregorianCalendar g2 = new GregorianCalendar(1997,11,24,14,0,0);


        RecurrenceStringBuilder rb1 = new RecurrenceStringBuilder().freqByDay().repetition(10);

        RecurrenceStringBuilder rb2 = new RecurrenceStringBuilder().freqByDay().until(g1);

        RecurrenceStringBuilder rb3 = new RecurrenceStringBuilder().freqByDay().interval(2);

        RecurrenceStringBuilder rb4 = new RecurrenceStringBuilder().freqByDay().interval(2).repetition(5);

        RecurrenceStringBuilder rb5=null;
        try {
            rb5 = new RecurrenceStringBuilder().freqByYear().until(g2).byMonth(RecurrenceStringBuilder.JANUARY)
                    .byDay(RecurrenceStringBuilder.SUNDAY).byDay(RecurrenceStringBuilder.MONDAY)
                    .byDay(RecurrenceStringBuilder.TUESDAY).byDay(RecurrenceStringBuilder.WEDNESDAY)
                    .byDay(RecurrenceStringBuilder.THURSDAY)
                    .byDay(RecurrenceStringBuilder.FRIDAY).byDay(RecurrenceStringBuilder.SATURDAY);
        } catch (IndexDayOutOfBoundException e) {
            e.printStackTrace();
        } catch (IndexMonthOutOfBoundException e) {
            e.printStackTrace();
        }

        RecurrenceStringBuilder rb6 = null;
        try {
            rb6 = new RecurrenceStringBuilder().freqByDay().until(g2).byMonth(RecurrenceStringBuilder.JANUARY);
        } catch (IndexMonthOutOfBoundException e) {
            e.printStackTrace();
        }

        RecurrenceStringBuilder rb7 = new RecurrenceStringBuilder().freqByWeek().repetition(10);

        RecurrenceStringBuilder rb8 = new RecurrenceStringBuilder().freqByWeek().until(g1);

        RecurrenceStringBuilder rb9 = null;
        try {
            rb9 = new RecurrenceStringBuilder().freqByDay().interval(2).weekStart(RecurrenceStringBuilder.SUNDAY);
        } catch (IndexDayOutOfBoundException e) {
            e.printStackTrace();
        }

        RecurrenceStringBuilder rb10 = null;
        try {
            rb10 = new RecurrenceStringBuilder().freqByWeek().until(g1)
                    .byDay(RecurrenceStringBuilder.TUESDAY).byDay(RecurrenceStringBuilder.THURSDAY);
        } catch (IndexDayOutOfBoundException e) {
            e.printStackTrace();
        }

        RecurrenceStringBuilder rb11 = null;
        try {
            rb11 = new RecurrenceStringBuilder().freqByWeek().repetition(10).weekStart(RecurrenceStringBuilder.SUNDAY)
                    .byDay(RecurrenceStringBuilder.TUESDAY).byDay(RecurrenceStringBuilder.THURSDAY);
        } catch (IndexDayOutOfBoundException e) {
            e.printStackTrace();
        }

        RecurrenceStringBuilder rb12 = null;
        try {
            rb12 = new RecurrenceStringBuilder().freqByWeek().interval(2).until(g1)
                    .weekStart(RecurrenceStringBuilder.SUNDAY)
                    .byDay(RecurrenceStringBuilder.MONDAY).byDay(RecurrenceStringBuilder.WEDNESDAY)
                    .byDay(RecurrenceStringBuilder.FRIDAY);
        } catch (IndexDayOutOfBoundException e) {
            e.printStackTrace();
        }

        assertEquals("RRULE:COUNT=10;FREQ=DAILY", rb1.getRRule());
        assertEquals("RRULE:FREQ=DAILY;UNTIL=19971224T000000Z", rb2.getRRule());
        assertEquals("RRULE:INTERVAL=2;FREQ=DAILY", rb3.getRRule());
        assertEquals("RRULE:COUNT=5;INTERVAL=2;FREQ=DAILY", rb4.getRRule());
        assertEquals("RRULE:FREQ=YEARLY;BYMONTH=1;UNTIL=19971224T140000Z;BYDAY=SU,MO,TU,WE,TH,FR,SA", rb5.getRRule());
        assertEquals("RRULE:FREQ=DAILY;BYMONTH=1;UNTIL=19971224T140000Z", rb6.getRRule());
        assertEquals("RRULE:COUNT=10;FREQ=WEEKLY", rb7.getRRule());
        assertEquals("RRULE:FREQ=WEEKLY;UNTIL=19971224T000000Z", rb8.getRRule());
        assertEquals("RRULE:INTERVAL=2;FREQ=DAILY;WKST=SU", rb9.getRRule());
        assertEquals("RRULE:FREQ=WEEKLY;UNTIL=19971224T000000Z;BYDAY=TU,TH", rb10.getRRule());
        assertEquals("RRULE:COUNT=10;FREQ=WEEKLY;WKST=SU;BYDAY=TU,TH", rb11.getRRule());
        assertEquals("RRULE:WKST=SU;UNTIL=19971224T000000Z;BYDAY=MO,WE,FR;INTERVAL=2;FREQ=WEEKLY", rb12.getRRule());
    }
    //@Test(expected = IndexDayOutOfBoundException.class)
    public void testIndexDayOutOfBoundException()throws IndexDayOutOfBoundException{
        RecurrenceStringBuilder rb = new RecurrenceStringBuilder();
        rb.byDay(7);
    }
    //@Test(expected = IndexMonthOutOfBoundException.class)
    public void testIndexMonthOutOfBoundException()throws IndexMonthOutOfBoundException{
        RecurrenceStringBuilder rb = new RecurrenceStringBuilder();
        rb.byMonth(12);
    }

}
