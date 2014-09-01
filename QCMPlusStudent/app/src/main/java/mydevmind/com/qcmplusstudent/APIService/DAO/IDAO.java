package mydevmind.com.qcmplusstudent.apiService.DAO;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;

/**
 * Interface DAO
 * Comportements d'échange avec parse.com pour les différents Models métiers
 */
public interface IDAO<T> {

    public void save(final T obj, final IAPIServiceResultListener<T> listener);
    public void delete(final T obj, final IAPIServiceResultListener<T> listener);
    public void find(final T obj, final IAPIServiceResultListener<T> listener);
}
