package RadioInfo.Controller;

import RadioInfo.Model.ChannelEpisode;
import RadioInfo.Model.Model;
import RadioInfo.View.Window;

import javax.swing.*;
import java.net.URL;
import java.util.List;

/**
 * Description:     Thread that will search for a episode from the models
 *                  cached episodes. If the episode is found, thread will
 *                  publish the episodes description and URL.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class GetDescriptionWorker extends SwingWorker<Integer, Object[]> {

    private Model   model;
    private Window  window;
    private String  title;
    private String  channel;

    /**
     * Constructor for GetDescriptionWorker.
     * @param model the model with cached episodes.
     * @param window the window to be published to.
     * @param channel name of the channel that episode is associated with.
     * @param title name of the actual episode.
     */
    protected GetDescriptionWorker(Model model, Window window, String channel,
                                   String title) {

        this.model      = model;
        this.window     = window;
        this.channel    = channel;
        this.title      = title;
    }

    /**
     * Runs the actual thread. Searches for the episode. Published the episodes
     * title, description and image URL. If these are not found, publishes
     * null.
     * @return null.
     */
    @Override
    protected Integer doInBackground() {

        Object data[]            = {null, null, null};
        ChannelEpisode episode  = model.getEpisode(channel, title);
        if (episode != null) {

            data[0] = episode.getTitle();
            data[1] = episode.getDescription();
            data[2] = episode.getImageURL();
        }
        publish(data);
        return null;
    }

    /**
     * With the published data chunks, updates the window with the information
     * about the found episode.
     * @param chunks The information as an object array.
     */
    @Override
    protected void process(List<Object[]> chunks) {

        String title        = (String)  chunks.get(0)[0];
        String description  = (String)  chunks.get(0)[1];
        URL    imageURL     = (URL)     chunks.get(0)[2];
        window.updateEpisode(title, description, imageURL);
    }
}
