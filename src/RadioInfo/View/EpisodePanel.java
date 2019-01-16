package RadioInfo.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Description:     Panel showing more information about a single episode.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class EpisodePanel extends JPanel {

    private JTextArea   descriptionText;
    private JLabel      label;

    /**
     * Constructor for EpisodePanel.
     * @param listener listener to see if the 'back' button is clicked. Should
     *                 display another panel.
     */
    protected EpisodePanel (ActionListener listener) {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);

        descriptionText = new JTextArea();
        descriptionText.setEditable(false);
        descriptionText.setFont(new Font(Font.DIALOG, Font.PLAIN,19));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(listener);

        label = new JLabel();

        this.add(label,           BorderLayout.WEST);
        this.add(descriptionText, BorderLayout.CENTER);
        this.add(backButton,      BorderLayout.SOUTH);
    }

    /**
     * Updates and formats information in panel.
     * @param title the title of the episode.
     * @param description the episodes description.
     * @param imageURL URL to image associated with the episode.
     */
    protected void updateInformation(String title, String description,
                                     URL imageURL) {

        descriptionText.setText(formatString(title, description));
        ImageIcon icon = null;
        try {
            BufferedImage image = ImageIO.read(imageURL);
            icon = new ImageIcon(image.getScaledInstance(500, 300,
                    image.SCALE_SMOOTH));
        } catch (IOException e) {

        }
        if (icon != null) {

            label.setIcon(icon);
        }
    }

    /**
     * Formats a string to fit the panel size by adding new lines and other
     * whitespace characters.
     * @param title the episodes title.
     * @param description the episodes description.
     * @return The formatted string.
     */
    private String formatString(String title, String description) {

        StringBuilder builder = new StringBuilder(description);
        builder.insert(0, "\n\n\n\n  ");

        for (int i = 0; i < builder.length(); i += 47) {

            char currentChar = builder.charAt(i);
            if (currentChar == ' ' || currentChar == ',' ||
                currentChar == '.' || currentChar == '-')
            {
                builder.insert(i + 1,"\n  ");
            } else if (i != 0) {

                builder.insert(i,"-\n  ");
            }
        }
        return "\n\n\t" + title + "\n\n" + builder;
    }
}
