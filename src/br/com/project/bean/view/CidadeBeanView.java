
package br.com.project.bean.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.geral.BeanManagedViewAbstract;
import br.com.project.geral.controller.CidadeController;
import br.com.project.model.classes.Cidade;

@Controller
@Scope(value = "session")
@ManagedBean(name = "cidadeBeanView")
public class CidadeBeanView extends BeanManagedViewAbstract {

	private List<Cidade> list = new ArrayList<Cidade>();
	private static final long serialVersionUID = 1L;
	private String url = "/cadastro/cad_cidade.jsf?faces-redirect=true";
	private Cidade objetoSelecionado = new Cidade();
	private String urlFind = "/cadastro/find_cidade.jsf?faces= true";

	@Override
	public String save() throws Exception {
		objetoSelecionado = cidadeController.merge(objetoSelecionado);
		return "";
	}

	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		return url;
	}
	
	

	@Override
	public void setarVariaveisNulas() throws Exception {
		list.clear();
		objetoSelecionado = new Cidade();
	}

	public String getUrl() {
		return url;
	}

	@Autowired
	private CidadeController cidadeController;

	@Override
	public String editar() throws Exception {
		list.clear();
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
		objetoSelecionado = (Cidade) cidadeController.getSession().get(getClassImplement(),
				objetoSelecionado.getCid_codigo());
		cidadeController.delete(objetoSelecionado);
		list.remove(objetoSelecionado);
		novo();
		sucesso();
	}

	
	@Override
	public void saveEdit() throws Exception {

		saveNoReturn();
	}

	@Override
	public void saveNoReturn() throws Exception {
		list.clear();
		objetoSelecionado = cidadeController.merge(objetoSelecionado);
		list.add(objetoSelecionado);
		objetoSelecionado = new Cidade();
		sucesso();

	}
@Override
public StreamedContent getArquivoReport() throws Exception {
super.setNomeRelatorioJasper("report_cidade");
super.setNomeRelatorioSaida("report_cidade");
super.setListDataBeanCollectionReport(cidadeController.findList(getClassImplement()));
	return super.getArquivoReport();
}
	
	public List<Cidade> getList() {
		try {
			list = cidadeController.findList(getClassImplement());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	protected Class<Cidade> getClassImplement() {

		return Cidade.class;
	}
	
	@Override
	public String redirecionarFindEntidade() throws Exception {

setarVariaveisNulas();
return urlFind;
	}

	@Override
	protected InterfaceCrud<Cidade> getController() {

		return cidadeController;
	}
	
	
}
