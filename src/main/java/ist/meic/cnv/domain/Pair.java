package ist.meic.cnv.domain;

/**
 * Created by Diogo on 05/04/2017.
 */
public class Pair {

    private String key;
    private String value;

    public Pair(){
    }

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
