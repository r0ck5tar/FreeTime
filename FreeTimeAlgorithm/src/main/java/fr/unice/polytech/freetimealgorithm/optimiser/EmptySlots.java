package fr.unice.polytech.freetimealgorithm.optimiser;

import fr.unice.polytech.freetimealgorithm.gui.listeners.OptimiserListener;

import java.util.ArrayList;

/**
 * Created by Hakim on 13/06/14.
 */
public class EmptySlots{
    public class EmptySlot {
        private long startTime, endTime;
        public EmptySlot(long start, long end) { startTime = start; endTime = end; }
        public long getStartTime() { return startTime; }
        public long getEndTime() { return endTime; }
    }

    private ArrayList<EmptySlot> emptySlots = new ArrayList<EmptySlot>();

    public void addEmptySlot(long start, long end) {
        emptySlots.add(new EmptySlot(start, end));
    }

    public void clearEmptySlots() { emptySlots.clear(); }

    public ArrayList<EmptySlot> getEmptySlots() {
        return emptySlots;
    }

}
