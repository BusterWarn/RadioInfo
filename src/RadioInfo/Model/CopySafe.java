package RadioInfo.Model;

/**
 * Description:     Interface for Classes which objects are "safe" to copy. For
 *                  an object which is safe to copy, a deep copy must be made
 *                  and no same references should be the same.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public interface CopySafe {

    /**
     * Makes a safe deep copy from the current object.
     * @return The copy.
     */
    public CopySafe copy();
}
