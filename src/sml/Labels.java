package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Class that holds a reference to labels names and addresses.
 * As such it contains methods that enable lookup of label addresses.
 *
 * @author Freddie Duffield
 */
public final class Labels {
    private final Map<String, Integer> labels = new HashMap<>();

    /**
     * Adds a label with the associated address to the map.
     *
     * @param label   the label
     * @param address the address the label refers to
     */
    public void addLabel(String label, int address) {
        Objects.requireNonNull(label);

        if (!isUnique(label)) return;

        labels.put(label, address);
    }

    private boolean isUnique(String label) {
        return labels.keySet().stream()
                .noneMatch(l -> l.equals(label));
    }

    /**
     * Returns the address associated with the label.
     *
     * @param label the label
     * @return the address the label refers to
     */
    public int getAddress(String label) throws NullPointerException {
        // TODO: Where can NullPointerException be thrown here?
        //       (Write an explanation.)
        // A NullPointerException could be thrown here if the there is no label store
        if (!labels.containsKey(label)) {
            throw new NullPointerException();
        }

        return labels.get(label);
    }

    /**
     * representation of this instance,
     * in the form "[label -> address, label -> address, ..., label -> address]"
     *
     * @return the string representation of the labels map
     */
    @Override
    public String toString() {
        return labels.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining(",", "[", "]"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Labels labels1 = (Labels) o;
        return Objects.equals(labels, labels1.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    /**
     * Removes the labels
     */
    public void reset() {
        labels.clear();
    }
}
