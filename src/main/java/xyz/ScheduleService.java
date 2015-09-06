package xyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.enums.CoGdzie;
import xyz.enums.Source;
import xyz.mail.EmailUsingGMailSMTPService;
import xyz.parsers.GumtreeParser;
import xyz.parsers.OlxParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kab00m on 03.09.15.
 */
@Service
public class ScheduleService {
    @Autowired
    private GumtreeParser gumtreeParser;
    @Autowired
    private OlxParser olxParser;
    @Autowired
    private EmailUsingGMailSMTPService mailService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //150000 - co 2,5 min
    //600000 - co 10 min
    //@Scheduled(fixedDelay = 600000)
    public void doIt() {
        log.debug("run...");
        Collection rows = new ArrayList<Row>();
        for (CoGdzie c : CoGdzie.values()) {
            if (c.getSource() == Source.GUMTREE) {
                rows.addAll(gumtreeParser.parse(c));
            }
            if (c.getSource() == Source.OLX) {
                rows.addAll(olxParser.parse(c));
            }
        }
        if (!rows.isEmpty()) {
            String subject = "test";
            StringBuilder messageBody = new StringBuilder("<ul>");
            List<Row> wiersze = (ArrayList<Row>)rows;
            for(Row r : wiersze) {
                messageBody.append(r.toString());
                messageBody.append("\n---------------\n");
            }
            messageBody.append("ilość ogłoszeń: " + wiersze.size());
            mailService.send(subject,messageBody.toString());

        }


    }
}
