package org.chelonix.jmdict4j;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ReadingNoKanjiAdapter extends XmlAdapter<ReadingNoKanjiAdapter.ReadingNoKanji, Boolean> {

    @Override
    public Boolean unmarshal(ReadingNoKanji v) throws Exception {
        return v != null;
    }

    @Override
    public ReadingNoKanji marshal(Boolean v) throws Exception {
        if(v) {
            return new ReadingNoKanji();
        }
        return null;
    }

    @XmlRootElement(name="re_nokanji")
    public static class ReadingNoKanji { }
}
