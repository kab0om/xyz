package xyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.enums.CoGdzie;
import xyz.parsers.GumtreeParser;
import xyz.parsers.OlxParser;

import java.io.IOException;
import java.util.*;

/**
 * Created by kab00m on 03.09.15.
 */
@RestController
@RequestMapping(value = "/sample")
public class Controller {

    @Autowired
    private GumtreeParser gumtreeParser;
    @Autowired
    private OlxParser olxParser;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/sendMail", method = RequestMethod.GET)
    public @ResponseBody Collection doIt() throws IOException {

        log.debug("doIt...");
//        String targowekGumtreeUrl = CoGdzie.GUMTREE.getUrl();
//        return gumtreeParser.parse(targowekGumtreeUrl);
        //sender.send(null);
        return null;
    }

    @RequestMapping(value="/olx", method = RequestMethod.GET)
    public @ResponseBody Collection olx() throws IOException {
        return olxParser.parse(CoGdzie.OLX);
    }
}
