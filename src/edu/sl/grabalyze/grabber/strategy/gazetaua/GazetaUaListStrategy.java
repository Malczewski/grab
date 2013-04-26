package edu.sl.grabalyze.grabber.strategy.gazetaua;

import edu.sl.grabalyze.dao.ArticleDAO;
import edu.sl.grabalyze.entity.Article;
import edu.sl.grabalyze.grabber.strategy.GrabberStrategy;
import edu.sl.grabalyze.grabber.strategy.utils.Extractor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GazetaUaListStrategy implements GrabberStrategy {

    private static final String HOST = "http://www.gazeta.ua";
    private static final String NEWS_LINK = HOST + "/news/%s/any/%d";

    private static final String CONTENT_END = "<a href=\"/news/%s/any/2\">Наступна";
    private static final String CONTENT_EXIST = "class=\"list-item\"";
    private static final String START_STRING = "list-item-top-border";
    private static final String END_STRING = "pagination";

    private static final String LIST_ITEM = "list-preview";

    private static final long DAY = 1000 * 60 * 60 * 24;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private Date dateTo, dateFrom, startDate;
    private int page;
    private int pageCount;

    private ArticleDAO articleDAO;

    public GazetaUaListStrategy(Date from, Date to) {
        this.dateFrom = new Date(from.getTime());
        this.startDate = new Date(from.getTime());
        this.dateTo = new Date(to.getTime());
        page = 1;
        pageCount = -1;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public void processHtml(String html) {
        if (html == null || html.isEmpty() || !html.contains(CONTENT_EXIST)) {
            System.out.println("Page is empty");
            dateFrom.setTime(dateFrom.getTime() + DAY);
            page = 1;
            pageCount = -1;
            return;
        }
        String content = html.substring(html.indexOf(START_STRING), html.indexOf("ui.datepicker-uk.js"));
        if (pageCount == -1) {
            if (!html.contains(END_STRING)) {
                pageCount = 1;
            } else {
                content = html.substring(0, html.indexOf(String.format(CONTENT_END, getDate())));
                int last = content.lastIndexOf("</a>");
                String countStr = content.substring(content.indexOf('>', last - 5) + 1, last);
                pageCount = Integer.valueOf(countStr);
                content = content.substring(0, content.indexOf(END_STRING));
            }
        }

        List<Article> result = new ArrayList<Article>();
        Extractor ex = new Extractor();
        ex.setIndex(0);
        while (true) {
            ex.setIndex(content.indexOf(LIST_ITEM, ex.getIndex()));
            if (ex.getIndex() == -1)
                break;

            Article art = new Article();

            ex.setString("<a href=\"", "\">");
            art.setUrl(HOST + ex.extractValue(content));
            art.setCategoryCode(art.getUrl().replaceAll(".*articles/", "").replaceAll("/.*", ""));

            art.setId(Long.valueOf(art.getUrl().substring(art.getUrl().lastIndexOf('/') + 1)));

            ex.setString("class=\"marked\">", "</a>");
            art.setCategoryName(ex.extractValue(content));

            ex.setString(art.getId() + "\">", "</a>");
            art.setTitle(ex.extractValue(content));


            art.setDate(dateFrom);

            result.add(art);
        }

        articleDAO.batchInsert(result);
    }

    @Override
    public double getProgress() {
        return (1.0 * (dateFrom.getTime() - startDate.getTime()) + 1.0 * (page - 1) / pageCount * DAY)
                / (dateTo.getTime() - startDate.getTime());
    }

    @Override
    public String nextUrl() {
        if (pageCount == -1)
            return getUrl();
        else {
            if (page > pageCount) {
                dateFrom.setTime(dateFrom.getTime() + DAY);
                pageCount = -1;
                page = 1;
                return nextUrl();
            } else {
                return getUrl();
            }
        }
    }

    @Override
    public boolean hasUrl() {
        return dateFrom.before(dateTo) || dateFrom.equals(dateTo) && (page <= pageCount || pageCount == -1);
    }

    private String getDate() {
        return format.format(dateFrom);
    }

    private String getUrl() {
        return String.format(NEWS_LINK, format.format(dateFrom), page++);
    }
}
