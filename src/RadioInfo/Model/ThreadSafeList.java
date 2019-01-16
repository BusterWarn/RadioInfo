package RadioInfo.Model;

import java.util.ArrayList;

/**
 * Description:     A list, which all of it's methods are thread safe.
 *                  ThreadSafeList uses an ArrayList of CopySafe objects. It's
 *                  methods are all synchronized, and all returned items from
 *                  the list are copies and not actual references. Thus the
 *                  returned objects can be manipulated without changing the
 *                  actual objects in ThreadSafeList.
 * Author:          Buster Hultgren Wärn - dv17bhn@cs.umu.se.
 * Course:          Applikationsutveckling i Java.
 * Written:         2019-01-08.
 */
public class ThreadSafeList implements CopySafe {

    private ArrayList<CopySafe> list;

    /**
     * Constructor for ThreadSafeList.
     */
    public ThreadSafeList() {

        list = new ArrayList<>();
    }

    /**
     * Gets a copy from the ThreadSafeList.
     * @return safe copy of the ThreadSafeList.
     */
    @Override
    public synchronized ThreadSafeList copy() {

        ThreadSafeList copy = new ThreadSafeList();
        for (CopySafe copySafeObject : list) {
            copy.add(copySafeObject.copy());
        }
        return copy;
    }

    /**
     * Adds an object to the ThreadSafeList. The object is copied, and is not
     * kept as a reference.
     * @param copySafeObject the object.
     */
    public synchronized void add(CopySafe copySafeObject) {

        list.add(copySafeObject.copy());
    }

    /**
     * Gets an object from the ThreadSafeList with in the index position.
     * @param index the position.
     * @return
     */
    public synchronized CopySafe getItemByIndex(int index) {

        return list.get(index).copy();
    }

    /**
     * Gets the ThreadSafeList as a new instance of an ArrayList. All objects
     * in the ArrayList are copies.
     * @return the ArrayList.
     */
    public synchronized ArrayList<CopySafe> getChannelsAsArrayList() {

        ArrayList<CopySafe> copy = new ArrayList<>();
        for (CopySafe copySafeObject : list) {

            copy.add(copySafeObject.copy());
        }
        return copy;
    }

    /**
     * Removes an object from the ThreadSafeList with in the index position.
     * @param index the position.
     */
    public synchronized void removeItemByIndex(int index) {

        list.remove(index);
    }

    /**
     * Gets the size of the ThreadSafeList.
     * @return the size - number of objects in the list.
     */
    public synchronized int size() {

        return list.size();
    }
}
