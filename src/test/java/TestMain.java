import com.imt.common.httpclient.HttpClientFactory;
import com.imt.common.httpclient.HttpClientRequest;

/**
 * Created by sunzhenkui on 2015/8/12.
 */
public class TestMain {
    public static void main(String args[]){
        try {
            String url = "";
            HttpClientFactory factory = new HttpClientFactory();
            new HttpClientRequest(factory,url).doGetSimpleResponse();
            factory.shutdown();
        }catch (Exception e){

        }finally {
        }
    }
}
