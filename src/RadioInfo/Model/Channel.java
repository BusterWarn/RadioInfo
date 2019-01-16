package RadioInfo.Model;

import java.util.ArrayList;

/**
 * Description:     A representation of a whole channel from Sveriges Radio.
 *                  Contains the id, episodes, title and URL to its schedule.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class Channel implements CopySafe {

    private int             id;
    private int             nrEpisodes;
    private String          name;
    private String          scheduleURL;
    private ThreadSafeList  episodes;

    /**
     * Constructor for Channel.
     * @param id the id.
     * @param name the name.
     * @param scheduleURL the URL to the schedule.
     */
    public Channel(int id, String name, String scheduleURL) {

        this.id             = id;
        this.nrEpisodes     = 0;
        this.name           = name;
        this.scheduleURL    = scheduleURL;
        this.episodes       = null;
    }

    /**
     * Gets a 'safe' copy from the Channel by constructing a new one.
     * @return the copy
     */
    @Override
    public Channel copy() {

        Channel copy = new Channel(id, name, scheduleURL);
        if (episodes != null) {

            copy.setEpisodes(episodes.copy());
        }
        return copy;
    }

    /**
     * Check if the Channel equals another. Does this by comparing their id's.
     * @param o the Channel to be compared with.
     * @return If id's does not match or compared object is not a channel,
     * then false; else true.
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof Channel) {

            Channel c = (Channel) o;
            return this.id == c.getId();
        }
        return false;
    }

    /**
     * Gets the id of the Channel.
     * @return the id.
     */
    public int getId() {

        return id;
    }

    /**
     * Gets the number of episodes in the channels ThreadSafeList.
     * @return the number of episodes.
     */
    public int getNrEpisodes() {

        return nrEpisodes;
    }

    /**
     * Gets the name of the channel.
     * @return the name.
     */
    public String getName() {

        return name;
    }

    /**
     * Gets all the episodes from the ThreadSafeList as a copied ArrayList.
     * @return the ArrayList containing copies of the episode Objects.
     */
    public ArrayList<CopySafe> getEpisodes() {

        if (episodes != null) {

            return episodes.getChannelsAsArrayList();
        }
        return null;
    }

    /**
     * Sets the Channels episodes with a ThreadSafeList. ThreadSafeList must
     * have at least one episode, and it must be a ChannelEpisode.
     * @param episodes
     */
    protected void setEpisodes(ThreadSafeList episodes) {

        if (episodes.size() > 0 &&
                episodes.getItemByIndex(0) instanceof ChannelEpisode) {

            this.episodes = episodes;
            nrEpisodes = episodes.size();
        }
    }
}
