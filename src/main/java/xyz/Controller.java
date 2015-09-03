package xyz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by kab00m on 03.09.15.
 */
@org.springframework.stereotype.Controller
@RestController("sample")
public class Controller {

    @Autowired
    private RowRepository rowRepository;

    private List<BigInteger> idki = new ArrayList<>();


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Row> doIt() throws IOException {
        idki.clear();
//        String html = "<ul><li><i><a class=\"mw-redirect\" title=\"title1\" href=\"yahoo.com\">used to be a best email</a></i>(1999)</li><li><i><a title=\"title2\" href=\"google.com\">Best search enginee We Will Go</a></i>(1999)</li><li><i><a title=\"title3\" href=\"apple.com\">Best Phone</a></i>(1990)</li></ul>";
//        Document doc = Jsoup.parse(html);
        Document doc = Jsoup.connect("http://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/targowek/v1c9008l3200018p1?fr=ownr&pr=,1600").get();

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
