package xyz;

/**
 * Created by kab00m on 04.09.15.
 */
public enum CoGdzie {
    GUMTREE("http://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/targowek/v1c9008l3200018p1?fr=ownr&pr=,1600");

    private String url;

    CoGdzie(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
