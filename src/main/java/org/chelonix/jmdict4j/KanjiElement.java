package org.chelonix.jmdict4j;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class KanjiElement {

    @XmlElement
    public String keb;

    @XmlElement(name="ke_inf")
    private String info;

    @XmlElement(name="ke_pri")
    public List<String> priority;

    @Override
    public String toString() {
        return "KanjiElement{" +
                "keb='" + keb + '\'' +
                ", info='" + info + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
