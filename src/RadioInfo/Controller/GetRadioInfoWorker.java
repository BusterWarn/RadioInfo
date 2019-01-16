package RadioInfo.Controller;

import RadioInfo.Model.ChannelEpisode;
import RadioInfo.Model.EpisodeSorter;
import RadioInfo.Model.Model;
import RadioInfo.View.Window;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:     Thread that will get cached information about all episodes
 *                  from the model, sort them and publish their information to
 *                  the windows table.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class GetRadioInfoWorker extends SwingWorker <Integer, Object[][]> {

    private Model                       model;
    private Window                      window;
    private EpisodeSorter.SortBy        sortBy;
    private ArrayList<String>           selectedChannels;

    /**
     * Constructor for GetRadioInfoWorker.
     * @param model the model with cached episodes.
     * @param window the window to be published to.
     * @param sortBy option for how the episodes should be sorted.
     */
    protected GetRadioInfoWorker(Model model, Window window,
                                 EpisodeSorter.SortBy sortBy) {

        this.model              = model;
        this.window             = window;
        this.selectedChannels   = window.getSelectedCheckBoxes();
        this.sortBy             = sortBy;
    }

    /**
     * Runs the actual thread. Gets the episodes and sorts them, then converts
     * them as an Object matrix. Publishes this object matrix.
     * @return
     */
    @Override
    protected Integer doInBackground() {

        ArrayList<ChannelEpisode> episodes = model.getEpisodesByChannelNames(
                                                             selectedChannels);
        EpisodeSorter.sortEpisodes(episodes, sortBy);
        Object data[][] = convertEpisodesToMatrix(episodes);
        publish(data);

        return null;
    }

    /**
     * With the object matrix in chunks, update the windows table.
     * @param chunks List containing the object matrix in its firsts position.
     */
    @Override
    protected void process(List<Object[][]> chunks) {

        window.updateTable(chunks.get(0));
    }

    /**
     * Converts the episodes to an object matrix.
     * @return The object matrix.
     */
    private Object[][] convertEpisodesToMatrix(
                                          ArrayList<ChannelEpisode> episodes) {

        int nrEpisodes = episodes.size();
        Object data[][] = new Object[nrEpisodes][5];

        for (int i = 0; i < nrEpisodes; i++) {

            ChannelEpisode e    = episodes.get(i);

            data[i][0]          = e.getChannelName();
            data[i][1]          = e.getTitle();
            data[i][2]          = getDateAsString(e.getStartDateTime());
            data[i][3]          = getDateAsString(e.getEndDateTime());
            data[i][4]          = getStatus(e.getStartDateTime(),
                                            e.getEndDateTime());
        }
        return data;
    }

    /**
     * Converts a LocalDateTime to a string with a more easy to read format.
     * @param dateTime the LocalDateTime object.
     * @return The converted string.
     */
    private String getDateAsString(LocalDateTime dateTime) {

        return String.format("%02d:%02d - %tA", dateTime.getHour(),
                dateTime.getMinute(), dateTime.getDayOfWeek());
    }

    /**
     * Gets the status of an episodes scheduled date/time, and returns it the
     * status as a string.
     * @param startDateTime the episodes starting date/time.
     * @param endDateTime the episodes ending date/time.
     * @return "Finished", "Running" or "N/A" depending on if the program has
     * already run, running or has not yet begun.
     */
    private String getStatus(LocalDateTime startDateTime,
                             LocalDateTime endDateTime) {

        if (endDateTime.isBefore(LocalDateTime.now())) {

            return "Finished";
        } else if (startDateTime.isBefore(LocalDateTime.now())) {

            return "Runnig";
        }
        return "Upcoming";
    }
}
