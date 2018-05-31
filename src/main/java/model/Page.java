package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Page {

    private Optional<Elements> links;

    public Page(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            this.links = Optional.ofNullable(document.select("a[href]"));
        }
        this.links = Optional.ofNullable(document.select("a[href]"));
    }

    public Optional<Elements> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(links, page.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(links);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Page: [").append("links= {");
        links.get().stream().forEach(l -> stringBuilder.append(l.attr("abs:href")).append(", "));
        stringBuilder.append("} ]");
        return stringBuilder.toString();
    }
}
