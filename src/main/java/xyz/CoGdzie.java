package xyz;

/**
 * Created by kab00m on 04.09.15.
 */
public enum CoGdzie {
    GUMTREE("http://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?fr=ownr&pr=,1800");

    private String url;

    CoGdzie(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
