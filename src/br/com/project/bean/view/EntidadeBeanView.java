package br.com.project.bean.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.geral.EntidadeAtualizaSenha;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.classes.Entidade;
import br.com.project.util.all.BeanViewAbstract;

@Controller
@Scope(value = "session")
@ManagedBean(name = "entidadeBeanView")
public class EntidadeBeanView extends BeanViewAbstract {

	@Autowired
	private ContextoBean contextoBean;
	private static final long serialVersionUID = 1L;
	private EntidadeAtualizaSenha entidadeAtualizaSenha = new EntidadeAtualizaSenha();
	private EntidadeController entidadeController = new EntidadeController();

	public void updateSenha() throws Exception {
    	
    	Entidade entidadeLogada = contextoBean.getEntidadeLogada();
    	
    	if(!entidadeAtualizaSenha.getSenhaAtual().
    			equals(entidadeLogada.getEnt_senha())){
    		addMsg("A senha Atual n�o � v�lida");
    		return ;
    		
    	}else if (entidadeAtualizaSenha.getSenhaAtual().
    			equals(entidadeAtualizaSenha.getNovaSenha())) {
    		addMsg("A senha Atual n�o pode ser igual a nova senha");
    		return ;

    		
    	}else if(!entidadeAtualizaSenha.getNovaSenha().equals(entidadeAtualizaSenha.getConfirmaSenha()))
    	{
    		addMsg("A nova senha e a confirma��o n�o conferem");
    		return ;

    	}
    	else {
    		entidadeLogada.setEnt_senha(entidadeAtualizaSenha.getNovaSenha());
            entidadeController.saveOrpUpdate(entidadeLogada);
            entidadeLogada = entidadeController.findByporId(Entidade.class,entidadeLogada.getEnt_codigo());
               if(entidadeLogada.getEnt_senha().equals(entidadeAtualizaSenha.getNovaSenha())) {
            	  
            		   sucesso();
            	   }else {
            		   addMsg("A nova senha n�o foi atualizada");
            		   error();
            	   }
               
	
    	}
	
    }

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public EntidadeAtualizaSenha getEntidadeAtualizaSenha() {
		return entidadeAtualizaSenha;
	}

	public void setEntidadeAtualizaSenha(EntidadeAtualizaSenha entidadeAtualizaSenha) {
		this.entidadeAtualizaSenha = entidadeAtualizaSenha;
	}

	public String getUsuarioLogadoSecurity() {
		return contextoBean.getAuthentication().getName();

	}

	public Date getUltimoAcesso() throws Exception {

		return contextoBean.getEntidadeLogada().getEnt_ultimoacesso();
	}
}
