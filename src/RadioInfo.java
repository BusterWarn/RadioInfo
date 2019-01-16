import RadioInfo.Controller.Controller;

import javax.swing.*;

/**
 * Description:     Main class for RadioInfo. Starts a controller, and the
 *                  program, from the EDT.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class RadioInfo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller controller = new Controller(60);

                controller.showView();
            }
        });
    }
}
