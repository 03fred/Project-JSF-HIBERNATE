
package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

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

	private List<Cidade> list = new ArrayList<Cidade>();
	private static final long serialVersionUID = 1L;
    private String url = "/cadastro/cad_cidade.jsf?faces-redirect=true";
	private Cidade objetoSelecionado = new  Cidade();
	
	@Override
	public String save() throws Exception {
   objetoSelecionado = cidadeController.merge(objetoSelecionado);
		return "";
	}
	
	@Override
	public String novo() throws Exception {
		objetoSelecionado = new Cidade();
		return url;
	}
	
	public String getUrl() {
		return url;
	}

	@Autowired
	private CidadeController cidadeController;

	@Override
	public String editar() throws Exception {

		return url;
	}
	public Cidade getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Cidade objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}
	
	@Override
	public void excluir() throws Exception {
		cidadeController.delete(objetoSelecionado);
list =  cidadeController.findList(Cidade.class);
		novo();
	}
 
	
	public List<Cidade> getList() {
		try {
			list =  cidadeController.findList(Cidade.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
}
