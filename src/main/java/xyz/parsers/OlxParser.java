package xyz.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.enums.CoGdzie;
import xyz.Row;
import xyz.RowRepository;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by kab00m on 03.09.15.
 */
@Service
public class OlxParser implements Parsable {

    @Autowired
    private RowRepository rowRepository;

    private List<String> idki = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Collection parse(CoGdzie coGdzie) {
        boolean pierwszeUruchomienie = false;
        idki.clear();
        List<Row> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(coGdzie.getUrl()).get();
            Elements links = doc.select("table[summary$=Ogłoszenie]");//img[src$=.png]

            Iterable<Row> rows = rowRepository.findAll();
            for (Row r : rows) {
                idki.add(r.getLink());
            }
            if (idki.isEmpty()) {
                pierwszeUruchomienie = true;
            }

            log.debug(coGdzie + "::idki.size()=" + idki.size());

            for (Element element : links) {
                // log.debug("-- text=" + element.text() + ", href=" + element.attr("href"));
                Row r = new Row();
                r.setFilter(coGdzie.name());
                r.setId(BigInteger.ZERO);
                Element title = element.select("td div h3 a[href]").first();
                //log.debug("title=" + title.text());
                //log.debug("href=" + title.attr("href"));
                r.setTitle(title.text());
                r.setLink(title.attr("href"));

                Element price = element.select("td div p[class$=price]").first();
                //log.debug("price=" + price.text());
                r.setPrice(price.text());

                Element location = element.select("td div p[class] small span").first();
                //log.debug("location=" + location.text());
                r.setCategoryLocation(location.text());

                Element date = element.select("td div p[class$=x-normal]").first();
                //log.debug("date=" + date.text());
                r.setCreationDateString(date.text());

                if (date.text() != null && date.text().startsWith("dzisiaj ")) {
                    String h = date.text().substring(8, 10);
                    String m = date.text().substring(11, 13);
                    int hour = Integer.parseInt(h);
                    int minute = Integer.parseInt(m);
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, minute);
                    //log.debug("dateInMilis=" + c.getTimeInMillis());
                    r.setId(BigInteger.valueOf(c.getTimeInMillis()));
                    r.setCreationDate(c.getTime());
                }

                if (r.getLink() != null && !idki.contains(r.getLink())) {
                    if (pierwszeUruchomienie
                            || (!pierwszeUruchomienie
                            && r.getCreationDateString().startsWith("dzisiaj "))) {
                        list.add(r);
                        rowRepository.save(r);
                    }
                }
            }

            Collections.sort(list, new Comparator<Row>() {
                @Override
                public int compare(Row o1, Row o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });

            for (Row r : list) {
                log.debug(r.toString());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return list;
    }
}
