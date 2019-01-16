package RadioInfo.Model;

import java.net.URL;
import java.time.LocalDateTime;

/**
 * Description:     An episode of a Channel object. Should be part of its
 *                  schedule. Contains information about the episode.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class ChannelEpisode implements CopySafe {

    private int             episodeId;
    private URL             imageURL;
    private String          title;
    private String          channelName;
    private String          description;
    private LocalDateTime   endDateTime;
    private LocalDateTime   startDateTime;

    /**
     * Constructor for the episode.
     * @param episodeId the id of the episode.
     * @param title the title of the episode.
     * @param channelName the associated channels name of the episode.
     * @param description the description of the episode.
     * @param imageURL an URL to the chosen image of the episode.
     * @param startDateTime the starting LocalDateTime of the episode.
     * @param endDateTime the ending LocalDateTime of the episode.
     */
    public ChannelEpisode(int episodeId, String title, String channelName,
                          String description, URL imageURL,
                          LocalDateTime startDateTime,
                          LocalDateTime endDateTime) {

        this.imageURL       = imageURL;
        this.episodeId      = episodeId;
        this.title          = title;
        this.channelName    = channelName;
        this.description    = description;
        this.startDateTime  = startDateTime;
        this.endDateTime    = endDateTime;
    }

    /**
     * Gets a 'safe' copy from the ChannelEpisode by constructing a new one.
     * @return the copy
     */
    @Override
    public ChannelEpisode copy() {

        return new ChannelEpisode(episodeId, title, channelName, description,
                                  imageURL, startDateTime, endDateTime);
    }

    /**
     * Gets the title of the episode.
     * @return the title.
     */
    public String getTitle() {

        return title;
    }

    /**
     * Gets the associated channels name.
     * @return the name of the channel.
     */
    public String getChannelName() {

        return channelName;
    }

    /**
     * Gets the description of the episode.
     * @return the description.
     */
    public String getDescription() {

        return description;
    }

    /**
     * Gets the starting date time of the episode.
     * @return the starting date time.
     */
    public LocalDateTime getStartDateTime() {

        return startDateTime;
    }

    /**
     * Gets the ending date time of the episode.
     * @return the ending date time.
     */
    public LocalDateTime getEndDateTime() {

        return endDateTime;
    }

    /**
     * Gets the image URL of the episode.
     * @return the image URL.
     */
    public URL getImageURL() {

        return imageURL;
    }
}
