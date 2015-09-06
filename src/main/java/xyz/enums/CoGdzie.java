package xyz.enums;

/**
 * Created by kab00m on 04.09.15.
 */
public enum CoGdzie {

    GUMTREE(Source.GUMTREE, "http://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?fr=ownr&pr=,1800"),
    OLX(Source.OLX, "http://olx.pl/nieruchomosci/mieszkania/wynajem/warszawa/?search[filter_float_price%3Ato]=1600&search[private_business]=private&search[dist]=5&search[district_id]=377");

    private String url;
    private Source source;

    CoGdzie(Source source, String url) {
        this.url = url;
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public Source getSource() {
        return source;
    }
}
