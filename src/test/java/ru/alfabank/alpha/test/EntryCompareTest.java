package ru.alfabank.alpha.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.alfabank.alpha.Entry;

/**
 *
 */
public class EntryCompareTest {

    @Test
    public void test1() {
        Entry e1 = new Entry("КОТ");
        Entry e2 = new Entry("ТОТ");
        Assert.assertTrue(e1.isChild(e2));
    }

    @Test
    public void test2() {
        Entry e1 = new Entry("ТОТ");
        Entry e2 = new Entry("ТОТ");
        Assert.assertFalse(e1.isChild(e2));
    }

    @Test
    public void test3() {
        Entry e1 = new Entry("ТОРТ");
        Entry e2 = new Entry("ТОТ");
        Assert.assertFalse(e1.isChild(e2));
    }

    @Test
    public void test4() {
        Entry e1 = new Entry("СОС");
        Entry e2 = new Entry("ТОТ");
        Assert.assertFalse(e1.isChild(e2));
    }

    @Test
    public void test5() {
        Set<Entry> dictionary = new HashSet<>();
        Entry root = new Entry("КОТ");
        dictionary.add(new Entry("КОС"));
        dictionary.add(new Entry("КИС"));
        dictionary.add(new Entry("КИТ"));
        dictionary.add(new Entry("КИЛ"));
        dictionary.add(new Entry("КИК"));
        dictionary.add(new Entry("КИМ"));
        dictionary.add(new Entry("КОК"));

        root.fillChildren(dictionary);
        List<Entry> trace = root.getTrace(new Entry("КИЛ"));

        Assert.assertTrue(trace.size() == 3);
    }

    @Test
    public void test6() {
        Set<Entry> dictionary = new HashSet<>();
        Entry root = new Entry("КОТ");

        dictionary.add(new Entry("ТОН"));
        dictionary.add(new Entry("НОТА"));
        dictionary.add(new Entry("КОТЫ"));
        dictionary.add(new Entry("РОТ"));
        dictionary.add(new Entry("РОТА"));
        dictionary.add(new Entry("ТОТ"));
        root.fillChildren(dictionary);

        List<Entry> trace = root.getTrace(new Entry("ТОН"));
//        for (Entry e : trace) {
//            System.out.println(e.getWord());
//        }
        Assert.assertTrue(trace.size() == 3);
    }

    @Test
    public void test7() {
        Set<Entry> dictionary = new HashSet<>();

        dictionary.add(new Entry("ТОН"));
        dictionary.add(new Entry("ТОН"));
        dictionary.add(new Entry("Тон"));
        dictionary.add(new Entry("тон"));

        Assert.assertTrue(dictionary.size() == 1);
    }

    @Test
    public void test8() {
        Set<Entry> dictionary = new HashSet<>();
        Entry root = new Entry("1");
        dictionary.add(new Entry("2"));
        dictionary.add(new Entry("3"));
        dictionary.add(new Entry("4"));
        dictionary.add(new Entry("5"));
        dictionary.add(new Entry("6"));
        dictionary.add(new Entry("7"));
        dictionary.add(new Entry("8"));

        root.fillChildren(dictionary);
        List<Entry> trace = root.getTrace(new Entry("8"));
        //System.out.println(root.toString());
        Assert.assertTrue(trace.size() == 2);
    }

}
