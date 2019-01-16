package RadioInfo.Controller;

import RadioInfo.Model.EpisodeSorter;
import RadioInfo.Model.Model;
import RadioInfo.View.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:     Controller class for RadioInfo. Should be run from the
 *                  Event Dispatch Thread (EDT). From here, the main flow of
 *                  the program  is handled. View (Window) and model are
 *                  initiated. SwingWorker threads are started here.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class Controller {

    private int         minutesBetweenUpdate;
    private Timer       timer;
    private Model       model;
    private Window      window;

    /**
     * Constructor for Controller.
     * @param minutesBetweenUpdate how often, in minutes, an automatic update
     *                             should commence.
     */
    public Controller(int minutesBetweenUpdate) {

        model                     = new Model();
        window                    = new Window();
        timer                     = new Timer();
        this.minutesBetweenUpdate = minutesBetweenUpdate;
    }

    /**
     * Initiates listeners and opens the window.
     */
    public void showView() {

        initiateDescriptionListener();
        initiateUpdateListener();
        initiateCheckBoxListener();
        setTimer();
        window.open();
    }

    /**
     * Sets the timer for automatic updates. Timer will update the timer window
     * and call updateRadioInfo().
     */
    private void setTimer() {

        TimerTask task  = new TimerTask() {

            @Override
            public void run() {

                LocalDateTime plusOneHour = LocalDateTime.now().
                        plusMinutes(minutesBetweenUpdate);
                String nextUpdate = String.format("%02d:%02d:%02d",
                        plusOneHour.getHour(),
                        plusOneHour.getMinute(),
                        plusOneHour.getSecond());
                window.setNextUpdateTime(nextUpdate);
                updateRadioInfo();
            }
        };
        timer.scheduleAtFixedRate(task, 0,
                            60000 * minutesBetweenUpdate);
    }

    /**
     * Calls the window to remove its checkboxes, then creates and executes an
     * UpdateRadioInfo worker that will update the model and then the window
     * with new checkboxes.
     */
    private void updateRadioInfo() {

        window.clearCheckBoxes();
        UpdateRadioInfoWorker w = new UpdateRadioInfoWorker(model, window);
        w.execute();
    }

    /**
     * Initiates the "More information" JMenuItem, adds a listener to it and
     * adds it to the window.
     */
    private void initiateDescriptionListener() {

        JMenuItem description = new JMenuItem("More information");
        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedChannel = window.getSelectedChannel();
                String selectedEpisode = window.getSelectedEpisode();

                if (selectedChannel != null && selectedEpisode != null) {

                    GetDescriptionWorker worker = new GetDescriptionWorker(
                            model, window, selectedChannel, selectedEpisode);
                    worker.execute();
                }
            }
        });
        window.addMenuComponent(description);
    }

    /**
     * Initiates the "Update channel info" JMenuItem, adds a listener to it and
     * adds it to the window.
     */
    private void initiateUpdateListener() {

        JMenuItem updateMenuItem = new JMenuItem("Update channel " +
                "information");
        updateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateRadioInfo();
            }
        });
        window.addMenuComponent(updateMenuItem);
    }

    /**
     * Initiates the "Get selected channels" JMenu with different JMenuItems,
     * adds listeners to each JMenuItem and adds the JMenu to the window.
     */
    private void initiateCheckBoxListener() {

        JMenu menu = new JMenu("Get selected channels");

        EpisodeSorter.SortBy sortBy[] = {
                EpisodeSorter.SortBy.ChannelAsc,
                EpisodeSorter.SortBy.ChannelDesc,
                EpisodeSorter.SortBy.EpisodeAsc,
                EpisodeSorter.SortBy.EpisodeDesc,
                EpisodeSorter.SortBy.DateTimeAsc,
                EpisodeSorter.SortBy.DateTimeDesc
        };
        String sortByName[]           = {
                "Sort by channels ascending",
                "Sort by channels descending",
                "Sort by episodes ascending",
                "Sort by episodes descending",
                "Sort by date/time ascending",
                "Sort by date/time descending",
        };

        for (int i = 0; i < sortBy.length; i++) {

            JMenuItem menuItem  = new JMenuItem(sortByName[i]);
            final int index     = i;
            menuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    GetRadioInfoWorker worker = new GetRadioInfoWorker(model,
                            window, sortBy[index]);
                    worker.execute();
                }
            });
            menu.add(menuItem);
        }
        window.addMenuComponent(menu);
    }
}
