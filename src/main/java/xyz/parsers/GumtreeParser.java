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
public class GumtreeParser implements Parsable {

    @Autowired
    private RowRepository rowRepository;

    private List<BigInteger> idki = new ArrayList<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Collection parse(CoGdzie coGdzie) throws IOException{
        boolean pierwszeUruchomienie = false;
        idki.clear();
        Document doc = Jsoup.connect(coGdzie.getUrl()).get();
        Elements links = doc.select("section div div div ul li");
        Elements links2 = links.select("div.result-link");

        Iterable<Row> rows = rowRepository.findAll();
        for(Row r : rows) {
            idki.add(r.getId());
        }
        if (idki.isEmpty()) {
            pierwszeUruchomienie = true;
        }
        log.debug("idki.size()=" + idki.size());

        List<Row> list = new ArrayList<>();
        for (Element element : links) {
            //System.out.format("%s %s %s\n", element.text(), element.attr("href"), element.attr("data-adid"));
            Row r = new Row();
            r.setFilter(coGdzie.name());
            if(element.attr("data-adid") != null && !element.attr("data-adid").isEmpty()) {
                r.setId(new BigInteger(element.attr("data-adid").substring(0,17)));
            }
            Element title = element.select("div.title a[href]").first();
            if (title != null) {
                r.setTitle(title.text());
                r.setLink("http://www.gumtree.pl" + title.attr("href"));
            }
            Element description = element.select("div.description").first();
            if (description != null) {
                r.setDescription(description.text());
            }
            Element price = element.select("div.price").first();
            if (price != null) {
                r.setPrice(price.text());
            }
            Element creationDate = element.select("div.creation-date").first();
            if (creationDate != null) {
                r.setCreationDate(creationDate.text());
            }
            Element categoryLocation = element.select("div.category-location").first();
            if (categoryLocation != null) {
                r.setCategoryLocation(categoryLocation.text());
            }

            if(r.getId() != null && !idki.contains(r.getId())) {
                if (pierwszeUruchomienie
                        || (!pierwszeUruchomienie
                            && r.getCreationDate().endsWith("min temu"))) {
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

        for (Row r : list){
            log.debug(r.toString());
        }

        return list;
    }
}
