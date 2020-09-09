package org.chelonix.jmdict4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class XmlIterator<T> implements Iterator<T> {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    XMLEventReader reader;

    XMLEvent event;

    Unmarshaller unmarshaller;
    String name;

    public XmlIterator(XMLEventReader reader, Unmarshaller unmarshaller, String name) {
        this.reader = reader;
        this.unmarshaller = unmarshaller;
        this.name = name;
        try {
            reader.next();
            this.event = reader.peek();
        } catch (XMLStreamException e) {
            LOG.error("", e);
            event = null;
        }
    }

    @Override
    public boolean hasNext() {
        try {
            while (event != null && !(event.isStartElement() && name.equals(event.asStartElement().getName().getLocalPart()))) {
                Object a = reader.next();
                event = reader.peek();
            }
            return event != null;

        } catch (XMLStreamException e) {
            LOG.error("", e);
            event = null;
        }
        return event != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            T next = (T) unmarshaller.unmarshal(reader);
            event = reader.peek();
            return next;
        } catch (JAXBException e) {
            LOG.error("error during unmarshalling ", e);
            return null;
        } catch (XMLStreamException e) {
            LOG.error("error during stream ", e);
            return null;
        }
    }
}
