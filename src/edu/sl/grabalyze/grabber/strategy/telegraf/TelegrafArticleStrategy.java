package edu.sl.grabalyze.grabber.strategy.telegraf;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.utils.Extractor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TelegrafArticleStrategy implements GrabberStrategy {

    private Map<Long, String> urls;
    private Iterator<Long> iterator;
    private long current;
    private int counter;
    private ArticleDAO articleDAO;

    public TelegrafArticleStrategy(Map<Long, String> urls) {
        this.urls = new HashMap<Long, String>(urls);
        iterator = this.urls.keySet().iterator();
        counter = 0;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void processHtml(String html) {
        Extractor ex = new Extractor();
        ex.setString("<full-text>", "<p class=\"class-pohozhie-temi\">");
        String content = ex.extractValue(html);
        String textOnly = removeTags(content).trim();

        if (!textOnly.isEmpty())
            articleDAO.updateContent(current, textOnly);
        else articleDAO.remove(current);
    }

    private String removeTags(String html) {
        return html.replaceAll("<[^>]*>", "");//.replaceAll("\\s\\s+", " ");
    }

    @Override
    public String nextUrl() {
        counter++;
        current = iterator.next();
        return urls.get(current);
    }

    @Override
    public boolean hasUrl() {
        return iterator.hasNext();
    }

    @Override
    public double getProgress() {
        return 1.0 * counter / urls.size();
    }
}
