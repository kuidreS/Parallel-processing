/*
Parallel processing:

 - Write an application, which reads the content in HTML format from that URL: https://en.wikipedia.org/wiki/Europe
 - Afterwards each link in that article MUST be stored and followed up, reads again the content from the link found and stores all of the URLs found in the content
 - All of the processing MUST happen parallel
 - At the end, a file MUST be generated with a list of all URLs being found in all articles AND a counter how often they have been appeared.
 - The delivery MUST be a Java 8 project, which can be built and run using Maven
 */

import config.Config;
import model.Page;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import output.Writer;
import output.XlsxWriter;
import util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vserdiuk
 */

public class SecondTestApp {

    final static Logger logger = Logger.getLogger(Config.class);

    public static void main(String[] args) {
        logger.info("An application has been started");

        Config config = new Config();

        //check all references
        String mainPageUrl = config.getPropertyValue("TEST_URL");
        Page mainPage = new Page(mainPageUrl);
        Map<Element, Long> mainPageLinkOccurenceMap = Util.getElementOccurrenceMap(mainPage.getLinks().get());
        Map<Element, Long> finalLinkOccurenceMap = new HashMap<>();
        finalLinkOccurenceMap.putAll(mainPageLinkOccurenceMap);
        Util.modifyOccurrenceMap(finalLinkOccurenceMap, mainPageLinkOccurenceMap);

        //save result in a file
        Writer writer = new XlsxWriter();
        writer.saveToFile(finalLinkOccurenceMap, config.getPropertyValue("OUTPUT_FILE"));
    }
}