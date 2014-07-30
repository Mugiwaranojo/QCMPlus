package thomascorfield.fr.qcmplusadmin.apiService.DAO;


import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;

/**
 * Created by Joan on 29/07/2014.
 */
public interface IDAO<T> {

    public void save(final T obj, final IAPIServiceResultListener<T> listener);
    public void delete(final T obj, final IAPIServiceResultListener<T> listener);
    public void find(final T obj, final IAPIServiceResultListener<T> listener);
}