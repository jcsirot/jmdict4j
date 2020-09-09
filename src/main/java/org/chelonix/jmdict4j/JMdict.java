package org.chelonix.jmdict4j;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

public class JMdict {

    private List<JMDictEntry> seq = new ArrayList<>();
    private Multimap<String, JMDictEntry> keb = ArrayListMultimap.create();

    void add(JMDictEntry entry) {
        seq.add(entry);
        if (entry.kEle != null) {
            entry.kEle.stream().forEach(kel -> keb.put(kel.keb, entry));
        }
    }

    public int count() {
        return seq.size();
    }

    public Collection<JMDictEntry> lookup(String str) {
        List<JMDictEntry> entries = seq.stream().filter(e -> e.startsWith(str)).collect(Collectors.toList());
        Collections.sort(entries, new LookupComparator(str));
        return entries;
    }

    static class LookupComparator implements Comparator<JMDictEntry> {

        private final String exact;

        LookupComparator(String exact) {
            this.exact = exact;
        }

        @Override
        public int compare(JMDictEntry e1, JMDictEntry e2) {
            if (e1.getMain().equals(e2.getMain())) {
                return 0;
            } else if (exact.equals(e1.getMain())) {
                return -1;
            }  else if (exact.equals(e2.getMain())) {
                return 1;
            } else {
                return Comparator.<Integer>naturalOrder().compare(e1.frequencyInfo(), e2.frequencyInfo());
            }
        }
    }
}
