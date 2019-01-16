package RadioInfo.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Description:     The view of RadioInfo. Consists of a frame with multiple
 *                  components. Width and height is hardcoded as final int's
 *                  and can not be edited.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class Window {

    private final int           width   = 1280;
    private final int           height  = 720;
    private JMenu               menu;
    private JFrame              frame;
    private JPanel              mainPanel;
    private JPanel              helpPanel;
    private JTable              channelTable;
    private JTextArea           nextUpdateTime;
    private CardLayout          cardLayout;
    private JScrollPane         checkBoxScroller;
    private JScrollPane         channelTableScroller;
    private EpisodePanel        episodePanel;
    private MultipleCheckBox    checkBoxArea;
    private ChannelTableModel   tableModel;

    /**
     * Constructor for Window.
     */
    public Window() {

        initiateFrame();
        initiateMenu();
        initiateCheckBoxArea();
        initiateTable();
        initiateHelpPanel();

        nextUpdateTime = null;

        //Create the episodepanel, with an ActionListener (back button).
        episodePanel = new EpisodePanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                displayChannelTable();
            }
        });
        mainPanel.add(episodePanel, "episode");

        displayHelpPanel();
        frame.pack();
    }

    /**
     * "Opens window" by setting frame to visible.
     */
    public void open() {

        frame.setVisible(true);
    }

    /**
     * Updates the table model with an object matrix of data.
     * @param data the Object matrix containing all data. the matrix should
     *             have 5 with the following string in index...
     *                  0 - Channel name
     *                  1 - Episode title
     *                  2 - Starting date/time.
     *                  3 - Ending date/time.
     *                  4 - The current status of the episode.
     */
    public void updateTable(Object data[][]) {

        tableModel.insertNewData(data);
        displayChannelTable();
    }

    /**
     * Updates the episode in the episode panel with the following information,
     * then displays the episode panel.
     * @param title the episode title.
     * @param description description of the episode.
     * @param imageURL an URL to an image associated with the episode.
     */
    public void updateEpisode(String title, String description, URL imageURL) {

        episodePanel.updateInformation(title, description, imageURL);
        displayEpisodePanel();
    }

    /**
     * Adds a JCheckBox to the check box panel. If processing text was present,
     * will remove this.
     * @param name the name of the JCheckBox.
     */
    public void addCheckBox(String name) {

        checkBoxArea.addCheckBox(name);
        checkBoxArea.repaint();
        checkBoxScroller.repaint();
        frame.repaint();
    }

    /**
     * Clears all JCheckBoxes in the check box panel and adds the processing
     * text.
     */
    public void clearCheckBoxes() {

        checkBoxArea.clearCheckBoxes();
    }

    /**
     * Adds a component in the JMenu. The added component will get the first
     * position in the JMenu.
     * @param c the component.
     */
    public void addMenuComponent(Component c) {

        menu.add(c, 0);
    }

    /**
     * Sets the text of when the next update will occur. Text is displayed in
     * help panel.
     * @param nextUpdate When the next update will occur.
     */
    public void setNextUpdateTime(String nextUpdate) {

        if (nextUpdateTime == null) {

            nextUpdateTime = new JTextArea();
            nextUpdateTime.setEditable(false);
            nextUpdateTime.setFont(new Font(Font.DIALOG, Font.ITALIC,19));
            helpPanel.add(nextUpdateTime, BorderLayout.NORTH);
        }
        nextUpdateTime.setText("\tNext automatic update: " + nextUpdate);
    }

    /**
     * Gets all selected JCheckBoxes from the check box panel.
     * @return The names of the selected JCheckBoxes as an ArrayList.
     */
    public ArrayList<String> getSelectedCheckBoxes() {

        return checkBoxArea.getSelectedCheckBoxes();
    }

    /**
     * Gets the selected channel name from the channel table.
     * @return If a row is selected, then its channel name; else null.
     */
    public String getSelectedChannel() {

        int row = channelTable.getSelectedRow();
        if (row >= 0) {

            String channel = (String) channelTable.getValueAt(row, 0);
            return channel;
        }
        return null;
    }

    /**
     * Gets the selected episode title from the channel table.
     * @return If a row is selected, then its episode title; else null.
     */
    public String getSelectedEpisode() {

        int row = channelTable.getSelectedRow();
        if (row >= 0) {

            String epsiode = (String) channelTable.getValueAt(row, 1);
            return epsiode;
        }
        return null;
    }

    /**
     * Displays the help panel.
     */
    private void displayHelpPanel() {

        cardLayout.show(mainPanel, "help");
    }

    /**
     * Displays the episode panel.
     */
    private void displayEpisodePanel() {

        cardLayout.show(mainPanel, "episode");
    }

    /**
     * Displays the channel table.
     */
    private void displayChannelTable() {

        cardLayout.show(mainPanel, "channelTable");
    }

    /**
     * Initiates the main frame.
     */
    private void initiateFrame() {

        frame = new JFrame("RadioInfo");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        cardLayout  = new CardLayout();
        mainPanel   = new JPanel(cardLayout);
        frame.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Initiates the menu bar.
     */
    private void initiateMenu() {

        menu                = new JMenu("Options");
        JMenuBar menuBar    = new JMenuBar();
        JMenuItem help      = new JMenuItem("Help");
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                displayHelpPanel();
            }
        });
        menu.add(help);
    }

    /**
     * Initiates the check box area.
     */
    private void initiateCheckBoxArea() {

        checkBoxArea = new MultipleCheckBox();
        checkBoxScroller = new JScrollPane(checkBoxArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        checkBoxScroller.setPreferredSize(new Dimension(width/4,height));
        frame.add(checkBoxScroller, BorderLayout.EAST);
    }

    /**
     * Initiates the table.
     */
    private void initiateTable() {

        tableModel = new ChannelTableModel();
        channelTable = new JTable(tableModel);
        channelTable.setRowHeight(30);
        channelTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        channelTable.getTableHeader().setReorderingAllowed(false);

        //Setting column widths
        channelTable.getColumnModel().getColumn(0).
                setPreferredWidth(width/8);
        channelTable.getColumnModel().getColumn(1).
                setPreferredWidth(width/5);
        channelTable.getColumnModel().getColumn(2).
                setPreferredWidth(width/6);
        channelTable.getColumnModel().getColumn(3).
                setPreferredWidth(width/6);

        channelTableScroller = new JScrollPane(channelTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(channelTableScroller, "channelTable");
    }

    /**
     * Initiates the help table.
     */
    private void initiateHelpPanel() {

        helpPanel = new JPanel();
        helpPanel.setLayout(new BorderLayout());

        JTextArea helpText  = new JTextArea("\n\n" +
                "   How to use:" +
                "\n" +
                "\t1. Select desired channels from the checkboxes on the " +
                "right." +
                "\n" +
                "\t2. From 'Options', press 'Get selected channels' in " +
                "preferred sorting order." +
                "\n\n" +
                "\tTo update the channel information from Sveriges Radio,\n" +
                "\tgo to 'Options' ->  'Update channel info'." +
                "\n\n" +
                "\tTo get more information about a program, select an episode"+
                " from the table,\n\t go to 'Options' -> 'More information." +
                "\n\n\n\n" +
                "   Author:" +
                "\n" +
                "\tBuster Hultgren Wärn" +
                "\tdv17bhn@cs.umu.se");
        helpText.setEditable(false);
        helpText.setFont(new Font(Font.DIALOG, Font.PLAIN,  19));

        JButton displayTableButton = new JButton("Display Table");
        displayTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                displayChannelTable();
            }
        });

        helpPanel.add(helpText, BorderLayout.CENTER);
        helpPanel.add(displayTableButton, BorderLayout.SOUTH);
        mainPanel.add(helpPanel, "help");
    }
}
