package br.com.project.bean.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.Entidade;

@Scope(value = "session")
@Component(value = "contextoBean")
public class ContextoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String USER_LOGADO_SESSAO = "userLogadoSessao";

	
	@Autowired
   private EntidadeController entidadeController;
	
	
	/*RETORNA TODAS AS INFORMAÇÕES DO USUÁRIO LOGADO
	 * @RETURN AUTHENTICATION*/
	
	public Authentication getAuthentication() {

		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public String getUserPrincipal() {
		
		return  getExternalContext().getUserPrincipal().getName();
	}
	public Entidade  getEntidadeLogada (){
		Entidade entidade = (Entidade) getExternalContext().getSessionMap().get(USER_LOGADO_SESSAO);
		
		if(entidade == null || (entidade != null && !entidade.getEnt_login().equals(getUserPrincipal()))){
			
			if(getAuthentication().isAuthenticated()) {
				entidadeController.updateUltimoAcessoUser(getAuthentication().getName());
				
			}
		}
		
		
		
		return entidade;
	}


	public ExternalContext  getExternalContext()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;
		
	}
}
