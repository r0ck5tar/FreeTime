package fr.unice.polytech.freetimealgorithm.gui.listeners;

/**
 * Created by Hakim on 14/06/14.
 */
public interface CustomListener {
    public void onAddEvent(String title, long start, long end);

    void onDetectEmptySlotsButtonClick(long start, long end);

    void onCreateNewTask(String title, long timeEstimation, long startDate, long endDate, int priority);
}
