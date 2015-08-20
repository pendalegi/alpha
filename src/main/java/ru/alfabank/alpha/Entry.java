package ru.alfabank.alpha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public class Entry {

    private Entry parent;
    private Set<Entry> children;
    private final String word;

    public Entry(String word) {
        this.word = word.toUpperCase();
    }

    public Entry getParent() {
        return parent;
    }

    public void setParent(Entry parent) {
        this.parent = parent;
    }

    public Set<Entry> getChildren() {
        if (children == null) {
            children = new HashSet<Entry>();
        }
        return children;
    }

    public void addChild(Entry child) {
        if (child.getParent() != null) {
            if (child.getParent().getDepth() < this.getDepth()) {
                return;
            }
            child.getParent().getChildren().remove(child);
        }
        getChildren().add(child);
        child.setParent(this);
    }

    public String getWord() {
        return word;
    }

    public List<Entry> getTrace(Entry target) {
        List<Entry> trace = new ArrayList<Entry>();
        if (this.equals(target)) {
            trace.add(this);
        } else {
            for (Entry child : getChildren()) {
                trace.addAll(child.getTrace(target));
            }
            if (!trace.isEmpty()) {
                trace.add(0, this);
            }
        }
        return trace;
    }

    public int getDepth() {
        int i = 0;
        Entry e = this;
        while (e != null) {
            e = e.getParent();
            i++;
        }
        return i;
    }

    public void fillChildren(Set<Entry> dictionary) {
        for (Entry next : dictionary) {
            if (isChild(next)) {
                addChild(next);
            }
        }
        dictionary.removeAll(getChildren());

        for (Entry next : dictionary) {
            Set<Entry> sub = new HashSet<Entry>();
            sub.add(next);

            for (Entry child : getChildren()) {
                child.fillChildren(sub);
                if (sub.isEmpty()) {
                    break;
                }
            }
        }
    }

    public boolean isChild(Entry candidate) {
        if (candidate.word.length() == this.word.length()) {
            if (!candidate.word.equals(this.word)) {
                char[] cArray = candidate.word.toCharArray();
                char[] tArray = this.word.toCharArray();

                for (int i = 0; i < cArray.length; i++) {
                    char ce = cArray[i];
                    char te = tArray[i];
                    cArray[i] = '*';
                    tArray[i] = '*';
                    if (Arrays.equals(cArray, tArray)) {
                        return true;
                    }
                    cArray[i] = ce;
                    tArray[i] = te;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Entry) {
            return this.word.equals(((Entry) other).word);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.word);
        return hash;
    }

    @Override
    public String toString() {
        String res = word;
        for (Entry c : getChildren()) {
            res += "(" + c.toString() + ")";
        }
        return res;
    }
}
