package org.chelonix.jmdict4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Path p = Path.of("/Users/sirot/Downloads/JMdict_e");

        try {
            long size = p.toFile().length();
            long chunk = size / 100;
            ProgressInputStream is = new ProgressInputStream(new FileInputStream(p.toFile()), size);
            is.addPropertyChangeListener(evt -> {
                long nv = (long)evt.getNewValue();
                System.out.printf("Processing %d%%\r", nv*100/size);
            });
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(is);
            final Unmarshaller unmarshaller = JAXBContext.newInstance(JMDictEntry.class).createUnmarshaller();

            Iterator<JMDictEntry> it = new XmlIterator<JMDictEntry>(reader, unmarshaller, "entry");
            //Stream<JMDictEntry> entries = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false);
            //entries.peek(System.out::println);

            //Map<Long, JMDictEntry> dict = new HashMap<>();
            JMdict dict = new JMdict();

            while (it.hasNext()) {
                JMDictEntry entry = it.next();
                dict.add(entry);
            }

            System.out.println(dict.count());

            dict.lookup("明くる").forEach(System.out::println);

            //Optional<JMDictEntry> entry = dict.values().stream().filter(e -> e.contains("明日は我が身")).findFirst();
            //entry.ifPresent(System.out::println);

            //System.out.println(dict.get(1000225L));

            /*
            while (true) {
                JMDictEntry entry = it.next();
                if (entry.sequence == 1000230) {
                    System.out.println(entry);
                    return;
                }
            }

            for (int i = 0; i < 50; i++) {
                System.out.println(it.next());
            }
            */
        } catch (XMLStreamException xmle) {
            LOG.error("", xmle);
        } catch (JAXBException e) {
            LOG.error("", e);
        }

    }
}
