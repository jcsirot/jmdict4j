package org.chelonix.jmdict4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JMDictEntry {

    @XmlElement(name="ent_seq")
    public long sequence;

    @XmlElement(name="k_ele")
    public List<KanjiElement> kEle;

    @XmlElement(name="r_ele")
    public List<ReadingElement> rEle;

    @XmlElement(name="sense")
    public List<Sense> sense;

    public boolean contains(String kanji) {
        return kEle != null && kEle.stream().filter(k -> k.keb.contains(kanji)).findFirst().isPresent();
    }

    public boolean startsWith(String kanji) {
        return kEle != null && kEle.stream().filter(k -> k.keb.startsWith(kanji)).findFirst().isPresent();
    }

    public boolean exactMatch(String kanji) {
        return kEle != null && kEle.stream().filter(k -> kanji.equals(k.keb)).findFirst().isPresent();
    }

    public String getMain() {
        if (kEle != null) {
            return kEle.get(0).keb;
        } else {
            return rEle.get(0).reb;
        }
    }

    private static final List<String> PRIO_0 = new ArrayList<>(){{
       add("spec1");
       add("news1");
       add("ichi1");
       for (int i = 1; i <= 10; i++) {
           add(String.format("nf%2d", i));
       }
    }};

    private static final List<String> PRIO_1 = new ArrayList<>(){{
        add("spec2");
        add("news2");
        add("ichi2");
        for (int i = 11; i <= 48; i++) {
            add(String.format("nf%2d", i));
        }
    }};

    public int frequencyInfo() {
        List<String> pri;
        if (kEle != null) {
            pri = kEle.get(0).priority;
        } else {
            pri = rEle.get(0).priority;
        }
        if (pri == null) {
            return Integer.MAX_VALUE;
        } else if (PRIO_0.stream().filter(pri::contains).count() != 0) {
            return 0;
        } else if (PRIO_1.stream().filter(pri::contains).count() != 0) {
            return 2;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public String toString() {
        return "JMDictEntry{" +
                "sequence=" + sequence +
                ", kEle=" + kEle +
                ", rEle=" + rEle +
                ", sense=" + sense +
                '}';
    }
}
