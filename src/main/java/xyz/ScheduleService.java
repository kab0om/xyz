package xyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.enums.CoGdzie;
import xyz.enums.Source;
import xyz.parsers.GumtreeParser;
import xyz.parsers.OlxParser;

import java.io.IOException;

/**
 * Created by kab00m on 03.09.15.
 */
@Service
public class ScheduleService {
    @Autowired
    private GumtreeParser gumtreeParser;
    @Autowired
    private OlxParser olxParser;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //150000 - co 2,5 min
    //600000 - co 10 min
    @Scheduled(fixedDelay = 600000)
    public void doIt() {
        log.debug("run...");
        try {
            for (CoGdzie c : CoGdzie.values()) {
                if (c.getSource() == Source.GUMTREE) {
                    gumtreeParser.parse(c);
                }
                if (c.getSource() == Source.OLX) {
                    olxParser.parse(c);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
