package ru.alfabank.alpha.test;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.alfabank.alpha.Entry;
import ru.alfabank.alpha.EntryTracer;

/**
 *
 */
public class EntryTraceTest {

    @Test
    public void test1() throws IOException {
        EntryTracer tracer = new EntryTracer("task.txt", "dictionary.txt");
        List<Entry> trace = tracer.trace();
        for (Entry e : trace) {
            System.out.println(e.getWord());
        }
        Assert.assertTrue(trace.size() == 3);
    }

}
