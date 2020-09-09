package org.chelonix.jmdict4j;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

public class ReadingElement {

    @XmlElement(name="reb")
    public String reb;

    @XmlElement(name="re_nokanji")
    @XmlJavaTypeAdapter(ReadingNoKanjiAdapter.class)
    private Boolean noKanji;

    @XmlElement(name="re_restr")
    private String restrict;

    @XmlElement(name="re_inf")
    private String info;

    @XmlElement(name="re_pri")
    public List<String> priority;

    @Override
    public String toString() {
        return "ReadingElement{" +
                "reading='" + reb + '\'' +
                ", noKanji=" + noKanji +
                ", restrict='" + restrict + '\'' +
                ", info='" + info + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
