package RadioInfo.View;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class ChannelTableModel extends DefaultTableModel {

    /**
     * Description:     The table model for the table used in the view of
     *                  RadioInfo.
     * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
     * Course:          Applikationsutveckling i Java.
     * Written:         2019-01-08.
     */
    protected ChannelTableModel() {

        super();

        addColumn("Channel");
        addColumn("Episode");
        addColumn("Start date/time");
        addColumn("End date/time");
        addColumn("Status");
    }

    /**
     * Checks if a cell's content is editable.
     * @param row index of the cells row.
     * @param column index of the cells column.
     * @return always false, the cell is never editable.
     */
    @Override
    public boolean isCellEditable(int row, int column) {

        return false;
    }

    /**
     * Removes all current rows in the table model and adds new one from an
     * object matrix of data.
     * @param data the Object matrix containing all data. the matrix should
     *             have 5 with the following string in index...
     *                  0 - Channel name
     *                  1 - Episode title
     *                  2 - Starting date/time.
     *                  3 - Ending date/time.
     *                  4 - The current status of the episode.
     */
    protected void insertNewData(Object data[][]) {

        int nrRows = getRowCount();
        for (int i = nrRows - 1; i >= 0; i--) {

            removeRow(i);
        }
        for (int i = 0; i < data.length; i++) {

            addRow(data[i]);
        }
        newDataAvailable(new TableModelEvent(this));
    }
}