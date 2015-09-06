package xyz.parsers;

import xyz.enums.CoGdzie;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by kab00m on 03.09.15.
 */
public interface Parsable {
    Collection parse(CoGdzie coGdzie);
}
