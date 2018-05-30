package br.com.project.bean.view;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.geral.BeanManagedViewAbstract;

@Controller
@Scope(value="request")
@ManagedBean(name = "loginBeanView")
public class LoginBeanView extends BeanManagedViewAbstract{

	private String username;
	private String password;
	
	private static final long serialVersionUID = 1L;

	
	public void invalidar(ActionEvent actionEvent) {
		System.out.println("cheguei no invalidar");
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
