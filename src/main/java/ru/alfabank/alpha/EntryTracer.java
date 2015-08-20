package ru.alfabank.alpha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class EntryTracer {

    public static void main(String[] args) {
        if (args != null && args.length > 1) {
            EntryTracer tracer = new EntryTracer(args[0], args[1]);
            List<Entry> trace = tracer.trace();
            for (Entry e : trace) {
                System.out.println(e);
            }
        }
    }

    private String taskFile;
    private String dictFile;

    public EntryTracer(String taskFile, String dictFile) {
        this.dictFile = dictFile;
        this.taskFile = taskFile;
    }

    public List<Entry> trace() {
        Set<String> task;
        Set<String> dictionaryStr;
        try {
            task = getData(taskFile);
        } catch (Exception ex) {
            throw new IllegalStateException("can't read task file");
        }
        try {
            dictionaryStr = getData(dictFile);
        } catch (Exception ex) {
            throw new IllegalStateException("can't read dictionary file");
        }
        if (task.size() != 2) {
            throw new IllegalStateException("bad task data");
        }
        if (task.isEmpty()) {
            throw new IllegalStateException("bad dictionary");
        }

        Iterator<String> iterator = task.iterator();
        Entry root = new Entry(iterator.next());
        Entry target = new Entry(iterator.next());
        Set<Entry> dictionary = new HashSet<>();
        for (String s : dictionaryStr) {
            dictionary.add(new Entry(s));
        }
        dictionary.remove(root);

        root.fillChildren(dictionary);
        List<Entry> trace = root.getTrace(target);
        return trace;
    }

    private Set<String> getData(String fromFile) throws Exception {
        Set<String> rows = new HashSet<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fromFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    rows.add(line);
                }
            }
        }
        return rows;
    }

}
