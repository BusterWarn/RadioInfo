package RadioInfo.Model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Description:     Interface for the radio info model. Used for loading
 *                  channels from Sveriges Radio API, and then getting them.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class Model {

    private ThreadSafeList      channels;
    private APIChannelParser    parser;

    /**
     * Constructor for Model.
     */
    public Model() {

        channels    = null;
        parser      = new APIChannelParser();
    }

    /**
     * Loads information from Sveriges Radio API and stores information on a
     * ThreadSafeList. Should be used before getChannels().
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void loadChannels() throws IOException, SAXException,
                                                 ParserConfigurationException {

        channels = parser.parseChannels();
    }

    /**
     * Gets a copy of cached channels as an ArrayList.
     * @return The ArrayList copy.
     */
    public ArrayList<CopySafe> getChannels() {

        if (channels != null) {

            return channels.getChannelsAsArrayList();
        }
        return null;
    }

    /**
     * Gets the names of all cached channels as an ArrayList.
     * @return The channel names.
     */
    public ArrayList<String> getChannelNames() {

        ArrayList<CopySafe> channelsCopy = channels.getChannelsAsArrayList();
        ArrayList<String>   channelNames = new ArrayList<>();

        for (CopySafe channel : channelsCopy) {

            if (((Channel) channel).getNrEpisodes() > 0) {

                channelNames.add(((Channel) channel).getName());
            }
        }
        return channelNames;
    }

    /**
     * Gets a ChannelEpisode by its associated channel and its title.
     * @param channelName name of the channel.
     * @param title the ChannelEpisode title.
     * @return If found, the channel episode; else null.
     */
    public ChannelEpisode getEpisode(String channelName, String title) {

        ArrayList<CopySafe> channelsCopy = channels.getChannelsAsArrayList();

        for (CopySafe c : channelsCopy) {

            Channel channel = (Channel) c;
            if (channel.getName().equals(channelName)) {

                ArrayList<CopySafe> episodes = channel.getEpisodes();
                for (CopySafe e : episodes) {

                    ChannelEpisode episode = (ChannelEpisode) e;
                    if (episode.getTitle().equals(title)) {

                        return episode;
                    }
                }
                break;
            }
        }
        return null;
    }

    /**
     * From an ArrayList of channel names, gets that channels episodes.
     * @param channelNames the channel names.
     * @return The episodes as an ArrayList.
     */
    public ArrayList<ChannelEpisode> getEpisodesByChannelNames(
                                              ArrayList<String> channelNames) {

        ArrayList<ChannelEpisode> matchedEpisodes = new ArrayList<>();
        LocalDateTime minOffsetDateTime = LocalDateTime.now().minusHours(12);
        LocalDateTime maxOffsetDateTime = LocalDateTime.now().plusHours(12);

        ArrayList<CopySafe> channelsCopy = channels.getChannelsAsArrayList();

        for (CopySafe c : channelsCopy) {

            Channel channel = (Channel) c;
            if (channelNames.contains(channel.getName())) {

                for (CopySafe e : channel.getEpisodes()) {

                    ChannelEpisode episode = (ChannelEpisode) e;
                    LocalDateTime episodeDateTime = episode.getStartDateTime();
                    if (episodeDateTime.isAfter(minOffsetDateTime) &&
                            episodeDateTime.isBefore(maxOffsetDateTime)) {

                        matchedEpisodes.add(episode);
                    }
                }
            }
        }
        return matchedEpisodes;
    }
}
