package RadioInfo.Controller;

import RadioInfo.View.Window;

import javax.swing.*;

import RadioInfo.Model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:     Thread that will call the Model to load information from
 *                  Sveriges Radio API. It will get all channels, and publish
 *                  their names by adding them to the window, which will create
 *                  them as JCheckBoxes.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class UpdateRadioInfoWorker extends SwingWorker<Integer, String> {

    private Model           model;
    private Window          window;

    /**
     * Constructor for UpdateRadioInfoWorker.
     * @param model The model to be used.
     * @param window The window to be published to.
     */
    protected UpdateRadioInfoWorker(Model model, Window window) {

        this.model   = model;
        this.window  = window;
    }

    /**
     * Runs the actual thread. Loads information in model from Sveriges Radio
     * API and calls publish() with channel names.
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    protected Integer doInBackground() throws ParserConfigurationException,
            SAXException, IOException {

        model.loadChannels();

        ArrayList<String> channelNames = model.getChannelNames();
        for (String channelName : channelNames) {

            publish(channelName);
        }

        return null;
    }

    /**
     * With the channel names, add them to window. Will be created there as
     * JCheckBoxes in its MultipleCheckBox.
     * @param chunks list of all String that can be published.
     */
    @Override
    protected void process(List<String> chunks) {

        for (String checkBox : chunks) {

            window.addCheckBox(checkBox);
        }
    }
}
