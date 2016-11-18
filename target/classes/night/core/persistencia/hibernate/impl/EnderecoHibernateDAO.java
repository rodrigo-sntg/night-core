package night.core.persistencia.hibernate.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import night.core.impl.dao.EnderecoDAO;
import night.core.persistencia.hibernate.AbstractHibernateDAO;
import night.dominio.Endereco;

public class EnderecoHibernateDAO extends 
  AbstractHibernateDAO<Endereco, Long> implements 
  EnderecoDAO { 
 
	 /**
	  * Find Address by contactId 
	  */ 
	 @SuppressWarnings("unchecked") 
	 public List<Endereco> findByEntidadeId(Long entidadeId) { 
	  return findByCriteria(Restrictions.eq("entidade.id", entidadeId)); 
	 } 
  
 
}