package Service;

import java.util.LinkedList;

public class RecentList <T> {
    private LinkedList<T> list = new LinkedList<>();
    private final int MAX_SIZE = 10;


    // Adds to front
    // Removes last item when over limit
    // Limit: 10
    public void addRecent(T item) {
        if (list.size() >= MAX_SIZE) {
            list.removeLast();
        }
        list.addFirst(item);
    }

    public void printRecent(int maxToShow) {
        if (list.isEmpty()) {
            System.out.println("Nothing has been recently viewed.");
        }
        else {
            // If the user enters a number larger than the current amount of available items in list, it will choose the actual size
            int realMaximum = Math.min(maxToShow, list.size());

            for (int i = 0; i < realMaximum; i++) {
                System.out.println(list.get(i));
            }
        }
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
