import android.content.Intent;
import android.test.ServiceTestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.unice.polytech.calendarmodule.FreeTimeCalendarService;
import fr.unice.polytech.calendarmodule.FreeTimeCalendarService.FreeTimeBinder;


/**
 * Created by Hakim on 11/06/2014.
 */

public class FreeTimeCalendarServiceTest extends ServiceTestCase<FreeTimeCalendarService>{
    private FreeTimeCalendarService ftcService;

    public FreeTimeCalendarServiceTest() {
        super(FreeTimeCalendarService.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        FreeTimeBinder binder = (FreeTimeBinder)bindService(new Intent(getContext(), FreeTimeCalendarService.class));
        ftcService = binder.getService();
    }

    public void testDemo() throws Exception {
        Calendar calStart = new GregorianCalendar(2014, 5, 11, 18, 0, 0);
        Calendar calEnd = new GregorianCalendar(2014, 5, 11, 19, 30, 0);
        ftcService.createEvent("Test Event", calStart, calEnd);
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}