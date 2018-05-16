package br.com.project.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;
@SuppressWarnings("unchecked")

public class ViewScope implements Scope, Serializable {

	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

	private static final long serialVersionUID = 1L;

	// vai retornar a estancia do escopo
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if (instance == null) {

			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}

		return instance;
	}

	// método que carrega o ID
	@Override
	public String getConversationId() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);

		return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	// destrói o view do escopo
	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if (callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);

		}

	}

	@Override
	public Object remove(String name) {
		Object instancia = getViewMap().remove(name);

		if (instancia != null) {

			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if (callBacks != null) {
				callBacks.remove(name);

			}

		}
		return instancia;
	}

	// objeto que resolve a referência e retorna o objeto correto
	
	@Override
	public Object resolveContextualObject(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		
		
		
		return  facesRequestAttributes.resolveReference(name);
	}

	// getViewRoot
	// Retorna o componente raiz que está associado a esta solicitação(request)
	// getViewMap Retorna um map que atua como a interface para o armazenamento de
	// dados

	private Map<String, Object> getViewMap() {
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				: new HashMap<String, Object>();
	}

}
