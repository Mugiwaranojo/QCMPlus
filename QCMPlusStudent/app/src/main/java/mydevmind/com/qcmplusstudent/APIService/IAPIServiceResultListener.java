package mydevmind.com.qcmplusstudent.apiService;

/**
 * Interface du listener du service DAO
 */
public interface IAPIServiceResultListener<T> {

    public void onApiResultListener(T obj, com.parse.ParseException e);
}
