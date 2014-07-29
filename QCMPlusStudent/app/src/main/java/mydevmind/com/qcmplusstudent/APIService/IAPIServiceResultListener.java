package mydevmind.com.qcmplusstudent.APIService;

/**
 * Created by Joan on 29/07/2014.
 */
public interface IAPIServiceResultListener<T> {

    public void onApiResultListener(T obj, com.parse.ParseException e);
}
