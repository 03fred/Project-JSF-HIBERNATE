package br.com.srv.implementacao;

import java.util.Date;

import br.com.srv.interfaces.SrvEntidade;

public class SrvEntidadeImpl implements SrvEntidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Date getUltimoAcessoEntidadeLogada(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUltimoAcessoUser(String login) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existeUsuario(String ent_login) {
		// TODO Auto-generated method stub
		return false;
	}

}
