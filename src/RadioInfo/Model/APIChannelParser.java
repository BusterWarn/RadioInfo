package RadioInfo.Model;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:     Parser for Sveriges Radoio API.
 *                  Channel and ChannelEpisode objects.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class APIChannelParser {

    private LocalDateTimeStringConverter converter;

    /**
     * Constructur for APIChannelParser.
     */
    protected APIChannelParser() {

        converter  = new javafx.util.converter.LocalDateTimeStringConverter(
                                        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Parses Sveriges Radoio API using DOM and builds Channel and
     * ChannelEpisode objects in a ThreadSafeList. Will start by parsing all
     * channels, then it will parse individual channels to read their episodes.
     * @return ThreadSafeList of all read Channel objects.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    protected ThreadSafeList parseChannels() throws
            ParserConfigurationException, IOException, SAXException {

        ThreadSafeList channelList = new ThreadSafeList();

        String url                  = "http://api.sr.se/api/v2/channels?" +
                                      "pagination=false";
        DocumentBuilderFactory f    = DocumentBuilderFactory.newInstance();
        DocumentBuilder b           = f.newDocumentBuilder();
        Document doc                = b.parse(url);

        doc.getDocumentElement().normalize();
        NodeList list = doc.getElementsByTagName("channel");

        for (int i = 0; i < list.getLength(); i++) {

            Element e        = (Element) list.item(i);
            String name      = e.getAttribute("name");
            int id           = Integer.parseInt(e.getAttribute("id"));
            Element schedule = (Element) e.getElementsByTagName("scheduleurl").
                                                                 item(0);
            String scheduleurl = null;
            if (schedule != null) {

                scheduleurl = schedule.getChildNodes().item(0).
                                                                getNodeValue();
            }
            Channel channel = new Channel(id, name, scheduleurl);
            channel.setEpisodes(parseChannelSchedule(id));
            channelList.add(channel);
        }
        return channelList;
    }

    /**
     * Parses an individual channel and building a ThreadSafeList of it's
     * episodes using DOM.
     * @param channelId ID of the individual channel.
     * @return ThreadSafeList of the channels episodes.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private ThreadSafeList parseChannelSchedule(int channelId)
            throws ParserConfigurationException, IOException, SAXException {

        String url = "http://api.sr.se/v2/scheduledepisodes?channelid=" +
                                               channelId + "&pagination=false";
        ThreadSafeList episodes = new ThreadSafeList();

        DocumentBuilderFactory f    = DocumentBuilderFactory.newInstance();
        DocumentBuilder b           = f.newDocumentBuilder();
        Document doc                = b.parse(url);

        NodeList list = doc.getElementsByTagName("scheduledepisode");
        for (int i = 0; i < list.getLength(); i++) {

            Element episodeId       = (Element) ((Element) list.item(i)).
                        getElementsByTagName("episodeid").item(0);
            Element episodeTitle    = (Element) ((Element) list.item(i)).
                        getElementsByTagName("title").item(0);
            Element episodeChannel  = (Element) ((Element) list.item(i)).
                        getElementsByTagName("channel").item(0);
            Element description     = (Element) ((Element) list.item(i)).
                        getElementsByTagName("description").item(0);
            Element imageurl        = (Element) ((Element) list.item(i)).
                        getElementsByTagName("imageurltemplate").item(0);
            Element starttimeutc    = (Element) ((Element) list.item(i)).
                        getElementsByTagName("starttimeutc").item(0);
            Element endtimeutc      = (Element) ((Element) list.item(i)).
                        getElementsByTagName("endtimeutc").item(0);

            int id                      = parseEpisodeId(episodeId);
            URL imageURL                = parseImageURL(imageurl);
            String title                = parseElementAsString(episodeTitle);
            String channel              = episodeChannel.getAttribute("name");
            String desc                 = parseElementAsString(description);
            LocalDateTime startDateTime = parseEpisodeDateTime(starttimeutc);
            LocalDateTime endDateTime   = parseEpisodeDateTime(endtimeutc);

            if (id != -1 && imageURL != null && title != null &&
                channel != null && desc != null &&
                startDateTime != null && endDateTime != null) {

                episodes.add(new ChannelEpisode(id, title, channel, desc,
                                 imageURL, startDateTime, endDateTime));
            }
        }
        return episodes;
    }

    /**
     * Parses an episodeid element and gets its integer.
     * @param episodeid the element.
     * @return if element is null, then -1; else the id.
     */
    private int parseEpisodeId(Element episodeid) {

        if (episodeid != null) {

            return Integer.parseInt(episodeid.getTextContent());
        }
        return -1;
    }

    /**
     * Parses a imageurl element and gets its URL.
     * @param imageurl the element.
     * @return if element is null, then null; else the URL.
     */
    private URL parseImageURL(Element imageurl) {

        URL url = null;
        if (imageurl != null) {

            try {

                return new URL(imageurl.getTextContent());
            } catch (MalformedURLException e) {

                e.printStackTrace();
                url = null;
            }
        }
        return url;
    }

    /**
     * Parses a element containing a string, and gets it.
     * @param element the element.
     * @return if element is null, then null; else the string.
     */
    private String parseElementAsString(Element element) {

        if (element != null) {

            return element.getTextContent();
        }
        return null;
    }

    /**
     * Parses a timeutc element and creates a LocalDateTime object from it.
     * @param timeutc the element.
     * @return if element is null, then null; else the parsed created date
     * time.
     */
    private LocalDateTime parseEpisodeDateTime(Element timeutc) {

        LocalDateTime localDateTime = null;

        if (timeutc != null) {

            String dt       = timeutc.getTextContent();
            dt              = dt.substring(0, Math.min(dt.length(), 19));
            localDateTime   = converter.fromString(dt);

            //Adding one hour since Sveriges Radio API show hours in UTC+0
            //while they actually run in UTC+1
            localDateTime   = localDateTime.plusHours(1);
        }
        return localDateTime;
    }
}
