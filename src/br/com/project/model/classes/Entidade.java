package br.com.project.model.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.primefaces.json.JSONObject;

@Audited
@Entity
public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long ent_codigo;

	private String ent_nomeFantasia;
	private String ent_login;
	private String ent_senha;
    private boolean ent_inativo = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ent_ultimoacesso;
    
    public void setEnt_inativo(boolean ent_inativo) {
		this.ent_inativo = ent_inativo;
	}
    
   

	public Date getEnt_ultimoacesso() {
		return ent_ultimoacesso;
	}



	public void setEnt_ultimoacesso(Date ent_ultimoacesso) {
		this.ent_ultimoacesso = ent_ultimoacesso;
	}



	public boolean isEnt_inativo() {
		return ent_inativo;
	}

	public void getEnt_inativo(boolean ent_inativo) {
  		this.ent_inativo = ent_inativo;
    }
   
	public String getEnt_login() {

		return ent_login;
	}

	public void setEnt_login(String ent_login) {
		this.ent_login = ent_login;
	}

	public String getEnt_senha() {
		return ent_senha;
	}

	public void setEnt_senha(String ent_senha) {
		this.ent_senha = ent_senha;
	}

	public Long getEnt_codigo() {
		return ent_codigo;
	}

	public void setEnt_codigo(Long ent_codigo) {
		this.ent_codigo = ent_codigo;
	}



	public String getEnt_nomeFantasia() {
		return ent_nomeFantasia;
	}



	public void setEnt_nomeFantasia(String ent_nomeFantasia) {
		this.ent_nomeFantasia = ent_nomeFantasia;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ent_codigo == null) ? 0 : ent_codigo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidade other = (Entidade) obj;
		if (ent_codigo == null) {
			if (other.ent_codigo != null)
				return false;
		} else if (!ent_codigo.equals(other.ent_codigo))
			return false;
		return true;
	}
	
	
	public JSONObject getJson() {
		Map<Object, Object> map = new HashMap<Object,Object>();
		map.put("ent_codigo","ent_codigo");
		map.put("ent_login","ent_login");
		map.put("ent_nomeFantasia","ent_nomeFantasia");
		return new JSONObject(map);
	}

}
