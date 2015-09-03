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
    private GumtreeParser gumtreeParser;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection doIt() throws IOException {

        String targowekGumtreeUrl = CoGdzie.GUMTREE.getUrl();
        return gumtreeParser.parse(targowekGumtreeUrl);
    }
}
