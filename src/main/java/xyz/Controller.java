package xyz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by kab00m on 03.09.15.
 */
@org.springframework.stereotype.Controller
@RestController("sample")
public class Controller {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Row> doIt() throws IOException {

//        String html = "<ul><li><i><a class=\"mw-redirect\" title=\"title1\" href=\"yahoo.com\">used to be a best email</a></i>(1999)</li><li><i><a title=\"title2\" href=\"google.com\">Best search enginee We Will Go</a></i>(1999)</li><li><i><a title=\"title3\" href=\"apple.com\">Best Phone</a></i>(1990)</li></ul>";
//        Document doc = Jsoup.parse(html);
        Document doc = Jsoup.connect("http://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/targowek/v1c9008l3200018p1?fr=ownr&pr=,1600").get();

        Elements links = doc.select("section div div div ul li");
        Elements links2 = links.select("div.result-link");

        List<Row> list = new ArrayList<>();
        for (Element element : links) {
            System.out.format("%s %s %s\n", element.text(), element.attr("href"), element.attr("data-adid"));
            Row r = new Row();
            if (element.attr("data-adid") != null && !element.attr("data-adid").isEmpty()) {
                r.setId(new BigInteger(element.attr("data-adid")));
            }
            Element title = element.select("div.title a[href]").first();
            if (title != null) {
                r.setTitle(title.text());
                r.setLink("http://www.gumtree.pl"+title.attr("href"));
                System.out.println("-- title=" + title.text());
                System.out.println("-- href=" + title.attr("href"));
            }
            Element description = element.select("div.description").first();
            if (description != null) {
                r.setDescription(description.text());
                System.out.println("-- description=" + description.text());
            }
            Element price = element.select("div.price").first();
            if (price != null) {
                r.setPrice(price.text());
                System.out.println("-- price=" + price.text());
            }
            Element creationDate = element.select("div.creation-date").first();
            if (creationDate != null) {
                r.setCreationDate(creationDate.text());
                System.out.println("-- creationDate=" + creationDate.text());
            }
            if(r.getId() != null) {
                list.add(r);
            }
            Collections.sort(list, new Comparator<Row>() {
                @Override
                public int compare(Row o1, Row o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
        }

        /*for (Element element : links) {
            if (element.attr("class").startsWith("result")) {
                System.out.format("%s %s\n", element.attr("class"), element.attr("data-adid"));

                Elements e = element.select("div div");
                for (Element el : e) {

                    System.out.format("-- %s %s\n", element.attr("class"), element.attr("data-adid"));
                }

            }


        }
*/

        return list;
    }
}
