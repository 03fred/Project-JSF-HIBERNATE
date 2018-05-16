package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {
	private static final long serialVersionUID = 1L;
	private static SessionFactory sessionfactory = HibernateUtil.getSessionFactory();

	// parte jdbc do spring : gerencia de dependencia
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;

	@Autowired
	private SimplejdbcTemplate simpleJdbcTemplate;

	@Autowired
	private SimpleJdbcInserImpl simpleJdbcInsert;

	@Override
	public void save(T obj) throws Exception {

		validaSessionFactory();
		sessionfactory.getCurrentSession().save(obj);
		executeFlushSession();

	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		sessionfactory.getCurrentSession().persist(obj);
		executeFlushSession();

	}

	@Override
	public void saveOrpUpdate(T obj) throws Exception {
		validaSessionFactory();
		sessionfactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();

	}

	@Override
	public void update(T obj) throws Exception {

		validaSessionFactory();
		sessionfactory.getCurrentSession().update(obj);
		executeFlushSession();
	}

	@Override
	public void delete(T obj) throws Exception {

		validaSessionFactory();
		sessionfactory.getCurrentSession().delete(obj);
		executeFlushSession();
	}

	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) sessionfactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return obj;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {
		validaSessionFactory();
		StringBuilder query = new StringBuilder();
		query.append("select distinct(entity) from ").append(entidade.getSimpleName()).append("entity");
		List<T> lista = sessionfactory.getCurrentSession().createQuery(query.toString()).list();
		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, long id) throws Exception {
		validaSessionFactory();

		Object obj = sessionfactory.getCurrentSession().get(getClass(), id);

		return obj;
	}

	@Override
	public T findByporId(Class<T> entidade, long id) throws Exception {
		validaSessionFactory();

		T obj = (T) sessionfactory.getCurrentSession().get(getClass(), id);

		return obj;
	}

	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionfactory.getCurrentSession().createQuery(s).list();
		return lista;

	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {

		validaSessionFactory();
		sessionfactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();

	}

	@Override
	public void ExecuteUpdateSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionfactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();

	}

	@Override
	public void clearSession() throws Exception {
		sessionfactory.getCurrentSession().clear();

	}

	@Override
	public void evict(Object objs) throws Exception {

		validaSessionFactory();
		sessionfactory.getCurrentSession().evict(objs);
	}

	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return sessionfactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {

		validaSessionFactory();
		List<?> lista = sessionfactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimplejdbcTemplate() {
		// TODO Auto-generated method stub
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimplejdbcInsert() {
		// TODO Auto-generated method stub
		return simpleJdbcInsert;
	}

	@Override
	public long totalRegistro(String table) throws Exception {
     StringBuilder sql = new StringBuilder();
     sql.append("select count(1) from").append(table);
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		validaSessionFactory();
		Query  queryReturn = sessionfactory.getCurrentSession().createQuery(query.toString());
		

		return queryReturn;
	}

	/**
	 * Realiza consulta no  banco de dados iniciando  o carregamento a partir do registro passado 
	 * no parametro -> iniciaNoRegistro e obtendo maximo de resultados
	 * @param query
	 * @param iniciaNoRegistro
	 * @param maximoResultado
	 * @return List<T>
	 * @throws Exception
	 */
	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {
     validaSessionFactory();
     List<T> lista = new ArrayList<T>();
     lista =  sessionfactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro).setMaxResults(maximoResultado).list();
    		 
     
		
		return lista;
	}

	private void validaSessionFactory() {
		if (sessionfactory == null) {
			sessionfactory = HibernateUtil.getSessionFactory();
		}

	}

	private void validaTransaction() {
		if (!sessionfactory.getCurrentSession().getTransaction().isActive()) {
		}

		sessionfactory.getCurrentSession().beginTransaction();

	}

	private void commitProcessoAjax() {
		sessionfactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollBackProcessoAjax() {
		sessionfactory.getCurrentSession().beginTransaction().toString();
	}

	// roda instantaneamente no banco de dados
	private void executeFlushSession() {
		sessionfactory.getCurrentSession().flush();

	}

	public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
		validaSessionFactory();
		List<Object[]> lista = (List<Object[]>) sessionfactory.getCurrentSession().createSQLQuery(sql).list();

		return lista;
	}

}
