
package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerCaixakiUtils;
import br.com.project.model.Entidade;

@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SessionFactory sf;

	// EXECUTADO APENAS UMA VEZ
	// EXECUTADO QUANDO A APLICA��O EST� SENDO INICIADA
	@Override
	protected void initFilterBean() throws ServletException {

		sf = HibernateUtil.getSessionFactory();

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// JDBC SPRING

		BasicDataSource springBasicDataSource = (BasicDataSource) ContextLoaderListenerCaixakiUtils
				.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springBasicDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {

			request.setCharacterEncoding("UTF-8"); // DEFINE CODIFICA��O

			// CAPTURA USU�RIO QUE DAZ A OPERA��O
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpSession sessao = request2.getSession();
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");

			if (userLogadoSessao != null) {
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
			}

			sf.getCurrentSession().beginTransaction();

			// ANTES DE EXECUTAR A��O(REQUEST)
			chain.doFilter(request, response); // EXECUTA A A��O NO SERVIDOR

			// AP�S EXECUTAR A��O(RESPOSTA)
			transactionManager.commit(status);

			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}

			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().getTransaction().rollback();

			}

			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}

		} finally {
			if (sf.getCurrentSession().isOpen()) {

				sf.getCurrentSession().beginTransaction().isActive();
				sf.getCurrentSession().flush();
				sf.getCurrentSession().clear();

				if (sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();

				}
			}
		}
	}

}
