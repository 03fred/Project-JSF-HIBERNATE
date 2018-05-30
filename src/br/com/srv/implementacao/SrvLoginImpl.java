package br.com.srv.implementacao;

import javax.annotation.Resource;

import br.com.repository.interfaces.RepositoryLogin;
import br.com.srv.interfaces.SrvLogin;

public class SrvLoginImpl implements SrvLogin {

	private static final long serialVersionUID = 1L;
	
@Resource
	private RepositoryLogin repositoryLogin;

	@Override
	public boolean autentico(String login, String senha) throws Exception {

		return repositoryLogin.autentico(login, senha);
	}

}
