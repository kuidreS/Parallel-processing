package output;

import org.jsoup.nodes.Element;
import java.util.Map;

/**
 * Created by vserdiuk
 */

public interface Writer {

    /**
     * Save links and occurrences in a file
     *
     * @param linkOccurrenceMap link and its occurrence counter
     * @param fileName output file name
     */
    void saveToFile(Map<Element, Long> linkOccurrenceMap, String fileName);

}
