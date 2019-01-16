package RadioInfo.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Description:     Modified panel with added JCheckBoxes. If non are added, a
 *                  'processing' text will, explaining to the user that
 *                  information is getting fetched.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class MultipleCheckBox extends JPanel {

    private JPanel                  buttonPanel;
    private JPanel                  checkBoxPanel;
    private JButton                 changeSelected;
    private JTextArea               processing;
    private ArrayList<JCheckBox>    checkBoxes;

    /**
     * Constructor for MultipleCheckBox.
     */
    protected MultipleCheckBox() {

        this.setBackground(Color.white);
        checkBoxes = new ArrayList<>();
        initiatePanels();
        initiateButton();

        processing = new JTextArea("\n\n\nFetching information from\n" +
                                          "Sveriges Radio...\n\n Please wait");
        processing.setLayout(new BorderLayout());
        processing.setEditable(false);
        processing.setFont(new Font(Font.DIALOG, Font.BOLD,  20));
        buttonPanel.add(processing, BorderLayout.SOUTH);
    }

    /**
     * Adds a JCheckBox to the panel. If processing text is showing, then the
     * text will disappear.
     * @param name the name of the JCheckBox.
     */
    protected void addCheckBox(String name) {

        if (checkBoxes.isEmpty()) {

            processing.setVisible(false);
        }
        JCheckBox checkBox = new JCheckBox(name);
        checkBoxes.add(checkBox);
        checkBox.setLayout(new BorderLayout());
        checkBoxPanel.add(checkBox, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Removes all current JCheckBoxes and adds the processing text.
     */
    protected void clearCheckBoxes() {

        for (JCheckBox checkBox : checkBoxes) {

            checkBoxPanel.remove(checkBox);
        }
        checkBoxes.clear();
        processing.setVisible(true);
    }

    /**
     * Gets the name of all selected JCheckBoxes as an ArrayList.
     * @return the ArrayList filled with names of all selected JCheckBoxes.
     */
    protected ArrayList<String> getSelectedCheckBoxes() {

        ArrayList<String> selected = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxes) {

            if (checkBox.isSelected()) {

                selected.add(checkBox.getText());
            }
        }
        return selected;
    }

    /**
     * Initiates all panels in MultipleCheckBox.
     */
    private void initiatePanels() {

        this.setLayout(new BorderLayout());

        buttonPanel = new JPanel(new BorderLayout(0, 0));
        this.add(buttonPanel, BorderLayout.NORTH);

        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel,
                                              BoxLayout.PAGE_AXIS));
        this.add(checkBoxPanel, BorderLayout.SOUTH);
    }

    /**
     * Initiates the 'Select/Deselect all' button.
     */
    private void initiateButton() {

        changeSelected = new JButton("Select/Deselect all");
        buttonPanel.add(changeSelected, BorderLayout.CENTER);

        changeSelected.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!checkBoxes.isEmpty()) {

                    if (someIsSelected()) {

                        deselectAll();
                    } else {

                        selectAll();
                    }
                }
            }
        });
    }

    /**
     * Checks if one or more JCheckBox are selected.
     * @return True if one or more is; else false.
     */
    private boolean someIsSelected() {

        for (JCheckBox checkBox : checkBoxes) {

            if (!checkBox.isSelected()) {

               return false;
            }
        }
        return true;
    }

    /**
     * Sets all JCheckBoxes to selected.
     */
    private void selectAll() {

        for (JCheckBox checkBox : checkBoxes) {

            checkBox.setSelected(true);
        }
    }

    /**
     * Sets all JCheckBoxes to NOT selected.
     */
    private void deselectAll() {

        for (JCheckBox checkBox : checkBoxes) {

            checkBox.setSelected(false);
        }
    }
}
