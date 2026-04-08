// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Service;

import Exceptions.EntityNotFoundException;
import Interfaces.Identifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T extends Identifiable & Comparable<? super T>> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public T find(String id) throws EntityNotFoundException {
        for (T item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new EntityNotFoundException("Entity with ID: " + id + " does not exist");
    }

    public List<T> filter(Predicate<T> predicate) {
        List<T> filteredResult = new ArrayList<>();

        for (T item: items) {
            // predicate.test is a yes or no question
            // If the answer is yes (aka true), then we add it to the list
            if (predicate.test(item)) {
                filteredResult.add(item);
            }
        }
        return filteredResult;
    }

    public List<T> getSorted() {
        List<T> sortedList = new ArrayList<>(items);
        Collections.sort(sortedList); // Collections.sort() uses the compareTo() methods of each object in the list to autosort it
        return sortedList;
    }
}
