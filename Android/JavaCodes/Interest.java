package tr.edu.ozu.ozunaviclient;

/**
 * Created by idilutkusoy on 13.11.2017.
 */

public class Interest {
    private int id;
    private String name;
    private Integer rate;

    public Interest() {
    }

    public Interest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Interest(int id, String name, int rate) {
        this.id = id;
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
