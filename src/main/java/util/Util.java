package util;

import model.Page;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

/**
 * Created by vserdiuk
 */

public class Util {

    final static Logger logger = Logger.getLogger(Util.class);

    /**
     * Method count each element occurence according to an attribute key value
     * ( "a[href]" - link key; "[src]" - media key; "link[href]" - import key )
     *
     * @param elements list of elements (links)
     * @return map with count of each element occurrence
     */
    public static Map<Element, Long> getElementOccurrenceMap(Elements elements) {
        return elements.parallelStream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    /**
     * Method updates elemet occurence map in that way:
     * if element exist in a map - increase counter,
     * otherwise - put element to a map
     *
     * @param elementOccurrenceMap
     * @param element
     * @param elementExist
     * @return
     */
    public static Map<Element, Long> updateMap(Map<Element, Long> elementOccurrenceMap,  Element element, boolean elementExist) {
        if (elementExist) {
            elementOccurrenceMap.computeIfPresent(element, (key, value) -> value + 1);
        } else {
            elementOccurrenceMap.putIfAbsent(element, 1l);
        }
        return  elementOccurrenceMap;
    }

    /**
     * Method modifies a final link occurrence map from current page
     *
     * @param finalLinkOccurrenceMap final links occurrence statistics
     * @param occurrenceMap current occurence map
     * @return final links occurrents map
     */
    public static Map<Element, Long> modifyOccurrenceMap(Map<Element, Long> finalLinkOccurrenceMap, Map<Element, Long> occurrenceMap) {
        occurrenceMap.entrySet().parallelStream().forEach(l -> {
            Page page = new Page(l.getKey().baseUri());
            if (page.getLinks().isPresent()) {
                Elements links = page.getLinks().get();
                links.parallelStream().forEach(s -> {
                    boolean exist = finalLinkOccurrenceMap.containsKey(s);
                    Util.updateMap(finalLinkOccurrenceMap, s, exist);
                });
            }
        });
        logger.info("The final links occurrence statistics have been prepared");
        return finalLinkOccurrenceMap;
    }

}
