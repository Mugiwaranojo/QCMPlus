<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.APIService.DAO;

import mydevmind.com.qcmplusstudent.APIService.IAPIServiceResultListener;
=======
package mydevmind.com.qcmplusstudent.apiService.dao;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6

/**
 * Created by Joan on 29/07/2014.
 */
public interface IDAO<T> {

    public void save(final T obj, final IAPIServiceResultListener<T> listener);
    public void delete(final T obj, final IAPIServiceResultListener<T> listener);
    public void find(final T obj, final IAPIServiceResultListener<T> listener);
}
