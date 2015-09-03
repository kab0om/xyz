package xyz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Collection parse(String url) throws IOException{
        idki.clear();
        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("section div div div ul li");
        Elements links2 = links.select("div.result-link");

        Iterable<Row> rows = rowRepository.findAll();
        for(Row r : rows) {
            idki.add(r.getId());
        }
        System.out.println("idki.size()=" + idki.size());

        List<Row> list = new ArrayList<>();
        for (Element element : links) {
            //System.out.format("%s %s %s\n", element.text(), element.attr("href"), element.attr("data-adid"));
            Row r = new Row();
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
            if(r.getId() != null && !idki.contains(r.getId())) {
                list.add(r);
                rowRepository.save(r);
            }
        }

        Collections.sort(list, new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        for (Row r : list){
            System.out.println(r);
        }

        return list;
    }
}