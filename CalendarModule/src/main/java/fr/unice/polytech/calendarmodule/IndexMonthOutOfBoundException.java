package fr.unice.polytech.calendarmodule;

/**
 * Created by Yoann on 11/06/2014.
 */
public class IndexMonthOutOfBoundException extends Throwable {
    public IndexMonthOutOfBoundException(int month) {
        super("#The month("+month+") is out of bound (which should be between 0-11)");
    }
}
