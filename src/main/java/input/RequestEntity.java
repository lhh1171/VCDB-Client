package input;


/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public abstract class RequestEntity {
    String uri;
    String method;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
