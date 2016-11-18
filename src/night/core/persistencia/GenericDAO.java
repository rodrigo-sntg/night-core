package night.core.persistencia;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface GenericDAO<T, ID extends Serializable> {

	T getById(ID id, boolean lock);

	T getById(ID id);

	T loadById(ID id);

	List<T> findAll();

	List<T> findByCriteria(Map criterias);

	public List<T> findByExample(T exampleInstance, String[] excludeProperty);

	void save(T entity);

	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(T entity);

	void deleteById(ID id);

	List<T> findByStoreDate(Timestamp storeDate);

}