package fr.unice.polytech.freetimealgorithm.gui;

import fr.unice.polytech.freetimealgorithm.gui.listeners.DummyCalendarListener;
import fr.unice.polytech.freetimealgorithm.model.Event;
import fr.unice.polytech.freetimealgorithm.tools.DateTools;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Hakim on 14/06/14.
 */
public class CalendarEventsPanel extends JPanel implements DummyCalendarListener {
    private JTextPane text = new JTextPane();

    public CalendarEventsPanel() {
        setLayout(new GridBagLayout());
        initializeComponent();
    }

    private void initializeComponent() {
        text.setContentType("text/html");
        JScrollPane scrollText = new JScrollPane(text);
        scrollText.setPreferredSize(new Dimension(400, 780));

        JLabel panelTitle = new JLabel("Calendar events");
        panelTitle.setFont(new Font("Arial", Font.BOLD, 16));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        add(panelTitle, gbc);

        gbc.gridy++; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1;
        add(scrollText, gbc);

    }

    @Override
    public void updateEvents(List<Event> events) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><font size =  2 face = \"Courier New\">");
        sb.append("***************************************************<br/>");
        for(Event e : events) {
            sb.append("<font color=red>"+e.getTitle()+"</font>"
                    + "<br/>  start: " + DateTools.print(e.getStartTime())
                    + "<br/>  end: &nbsp " + DateTools.print(e.getEndTime()) + "\n");
            sb.append("***************************************************<br/>");
        }
        sb.append("</font></html>");
        sb.trimToSize();
        text.setText(sb.toString());
    }
}
