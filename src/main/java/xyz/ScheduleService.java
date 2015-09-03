package xyz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by kab00m on 03.09.15.
 */
@Service
public class ScheduleService {
    @Autowired
    private GumtreeParser gumtreeParser;

    //150000 - co 2,5 min
    @Scheduled(fixedDelay = 150000)
    public void doIt() {
        System.out.println("run...");
        try {
            gumtreeParser.parse(CoGdzie.GUMTREE.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
