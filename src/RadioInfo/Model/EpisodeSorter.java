package RadioInfo.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Description:     Sorts an ArrayList of ChannelEpisode in different ways.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class EpisodeSorter {

    /**
     * Options for which way to sort episode. Sorts by...
     *      ChannelAsc      - Channel name ascending.
     *      ChannelDesc     - Channel name descending.
     *      EpisodeAsc      - Episode name ascending.
     *      EpisdodeDesc    - Episode name descending.
     *      DateTimeAsc     - Date and time ascending.
     *      DateTimeDesc    - Date and time descending.
     */
    public enum SortBy {

        ChannelAsc,
        ChannelDesc,
        EpisodeAsc,
        EpisodeDesc,
        DateTimeAsc,
        DateTimeDesc
    }

    /**
     * Sorts an ArrayList of ChannelEpisode objects. SortBy enum is used for
     * determining by what attribute episodes are sortet.
     * @param episodes The ArrayList with ChannelEpisodes objects to be sorted.
     * @param sortBy How the episodes should be sorted.
     */
    public static void sortEpisodes(ArrayList<ChannelEpisode> episodes,
                                    SortBy sortBy) {

        switch (sortBy) {

            case ChannelAsc:
                sortByChannelAsc(episodes);
                break;
            case ChannelDesc:
                sortByChannelDesc(episodes);
                break;
            case EpisodeAsc:
                sortByEpisodeAsc(episodes);
                break;
            case EpisodeDesc:
                sortByEpisodeDesc(episodes);
                break;
            case DateTimeAsc:
                sortByDateTimeAsc(episodes);
                break;
            case DateTimeDesc:
                sortByDateTimeDesc(episodes);
                break;
        }
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by channel name ascending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByChannelAsc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o1.getChannelName().compareTo(o2.getChannelName());
            }
        });
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by channel name ascending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByChannelDesc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o2.getChannelName().compareTo(o1.getChannelName());
            }
        });
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by episode name ascending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByEpisodeAsc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by episode name descending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByEpisodeDesc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by date & time ascending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByDateTimeAsc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o1.getStartDateTime().compareTo(o2.getStartDateTime());
            }
        });
    }

    /**
     * Sorts ArryList with ChannelEpisode objects by date & time descending.
     * @param episodes The ArrayList with ChannelEpisode objects that is to be
     *                 sorted.
     */
    private static void sortByDateTimeDesc(ArrayList<ChannelEpisode> episodes) {

        Collections.sort(episodes, new Comparator<ChannelEpisode>() {
            @Override
            public int compare(ChannelEpisode o1, ChannelEpisode o2) {

                return o2.getStartDateTime().compareTo(o1.getStartDateTime());
            }
        });
    }

    // Constructor is private as no actual EpisodeSorter object should be
    // created.
    private EpisodeSorter(){}
}
