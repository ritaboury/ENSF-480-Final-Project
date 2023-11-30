package boundary;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPagePanel extends JPanel {
    private JButton viewFlightsButton;
    private JButton logoutButton;
    private JScrollPane originScrollPane;
    private JScrollPane destinationScrollPane;
    private JList<String> originList;
    private JList<String> destinationList;
    private String user;

    public UserPagePanel(String name, ActionListener viewMyFlightsListener, ActionListener viewFlightsListener, ActionListener logoutListener, ArrayList<String> locationStrings) {
        this.user = name;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        initializeComponents(viewMyFlightsListener, viewFlightsListener, logoutListener, locationStrings);
    }

    private void initializeComponents(ActionListener viewMyFlightsListener, ActionListener viewFlightsListener, ActionListener logoutListener, ArrayList<String> locationStrings) {
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + (user.isEmpty() ? "Guest" : user) + "!");
        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(welcomeLabel);

        // View Flights Button
        if (!user.isEmpty()) {
            JButton myFlightsButton = styleButton(new JButton("View My Flights"));
            myFlightsButton.addActionListener(viewMyFlightsListener);
            add(myFlightsButton);
            add(Box.createVerticalStrut(10));
        }

        JLabel enterLabel = new JLabel("Please select an origin and a destination below.");
        enterLabel.setFont(new Font(enterLabel.getFont().getName(), Font.PLAIN, 16));
        enterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(enterLabel);
        add(Box.createVerticalStrut(10));

        // Location selection
        originList = new JList<>(locationStrings.toArray(new String[0]));
        originList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        originScrollPane = createScrollableList(originList, "Origin Locations");

        destinationList = new JList<>(locationStrings.toArray(new String[0]));
        destinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        destinationScrollPane = createScrollableList(destinationList, "Destination Locations");

        JPanel locationSelectionPanel = new JPanel();
        locationSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        locationSelectionPanel.setBackground(Color.WHITE);
        locationSelectionPanel.add(originScrollPane);
        locationSelectionPanel.add(destinationScrollPane);
        add(locationSelectionPanel);
        add(Box.createVerticalStrut(10));

        viewFlightsButton = styleButton(new JButton("View Available Flights"));
        viewFlightsButton.addActionListener(viewFlightsListener);
        add(viewFlightsButton);
        add(Box.createVerticalStrut(10));

        // Logout Button
        logoutButton = styleButton(new JButton(user.isEmpty() ? "Return to Home Page" : "Logout"));
        logoutButton.addActionListener(logoutListener);
        add(logoutButton);
        add(Box.createVerticalStrut(20));
    }

    private JButton styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0, 102, 204)); // Blue background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, 16));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JScrollPane createScrollableList(JList<String> list, String title) {
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), titledBorder));
        return scrollPane;
    }

    public String getSelectedOrigin() {
        return originList.getSelectedValue();
    }

    public String getSelectedDestination() {
        return destinationList.getSelectedValue();
    }
}
