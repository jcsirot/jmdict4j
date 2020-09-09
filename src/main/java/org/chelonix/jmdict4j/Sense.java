package org.chelonix.jmdict4j;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Sense {

    @XmlElement
    private List<String> stagk;

    @XmlElement
    private List<String> stagr;

    @XmlElement
    private List<String> xref;

    @XmlElement
    private List<String> pos;

    @XmlElement
    private List<String> ant;

    @XmlElement
    private List<String> field;

    @XmlElement
    private List<String> misc;

    @XmlElement
    private List<String> dial;

    @XmlElement(name="s_inf")
    private List<String> sInf;

    @XmlElement
    private List<String> gloss;

    @Override
    public String toString() {
        return "Sense{" +
                "stagk=" + stagk +
                ", stagr=" + stagr +
                ", xref=" + xref +
                ", pos=" + pos +
                ", ant=" + ant +
                ", gloss=" + gloss +
                '}';
    }
}
