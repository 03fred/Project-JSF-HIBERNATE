package br.com.dao.implementacao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.repository.interfaces.RepositoryLogin;

public class DaoLogin extends ImplementacaoCrud<Object> implements RepositoryLogin {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean autentico(String login, String senha) throws Exception {

		String sql = "select count(1) as  autentica from entidade where ent_login = ? and ent_senha = ?";
		SqlRowSet sqlRowset = super.getJdbcTemplate().queryForRowSet(sql,new Object[] {login,senha});
		return sqlRowset.next() ? sqlRowset.getInt("autentica") > 0 :false;
	}

}
