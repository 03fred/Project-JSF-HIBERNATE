package br.com.project.bean.view;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.geral.BeanManagedViewAbstract;
import br.com.project.geral.controller.CidadeController;
import br.com.project.model.Cidade;

@Controller
@Scope(value = "session")
@ManagedBean(name = "cidadeBeanView")
public class CidadeBeanView extends BeanManagedViewAbstract{

	private static final long serialVersionUID = 1L;
    
	private Cidade objetoSelecionado = new  Cidade();
	
	@Override
	public String save() throws Exception {

		System.out.println(objetoSelecionado.getCid_descricao());
		return "";
	}
	
	@Autowired
	private CidadeController cidadeController;

	public Cidade getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Cidade objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}
	
 
	
}
