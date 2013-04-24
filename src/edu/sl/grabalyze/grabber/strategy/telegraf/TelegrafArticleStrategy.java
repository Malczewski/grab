package edu.sl.grabalyze.grabber.strategy.telegraf;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;

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
        int start = html.indexOf("article-text\">");
        if (start == -1)
            return;
        start += "article-text\">".length();
        int end = html.indexOf("<div class=\"clear\">", start);
        String content = html.substring(start, end);
        String clear = removeReadAlso(content);
        String textOnly = removeTags(clear).trim();

        articleDAO.updateContent(current, textOnly);
    }

    private String removeReadAlso(String html) {
        String also = "ЧИТАЙТЕ ТАКОЖ";
        int index = html.indexOf(also);
        if (index != -1) {
            return removeReadAlso(html.substring(0, index).concat(html.substring(html.indexOf("</a>", index), html.length())));
        }
        return html;
    }

    private String removeTags(String html) {
        return html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "");
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
