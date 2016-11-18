package night.core.impl.dao;

import java.util.List;

import night.core.persistencia.GenericDAO;
import night.dominio.Endereco;

public interface EnderecoDAO extends GenericDAO<Endereco,Long> { 
 
 /*
  * TODO : Add specific businesses daos here. 
  * These methods will be overwrited if you re-generate this interface. 
  * You might want to extend this interface and to change the dao factory to return  
  * an instance of the new implemenation in buildAddressDAO() 
  */ 
      
 /**
  * Find Endereco by clienteId 
  */ 
 public List<Endereco> findByEntidadeId(Long entidadeId); 
 
}