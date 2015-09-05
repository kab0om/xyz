package xyz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private Sender sender;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection doIt() throws IOException {

        log.debug("doIt...");
//        String targowekGumtreeUrl = CoGdzie.GUMTREE.getUrl();
//        return gumtreeParser.parse(targowekGumtreeUrl);
        sender.send(null);
        return null;
    }
}
