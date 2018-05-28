package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler weapperd;
	final FacesContext facesContext = FacesContext.getCurrentInstance();

	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {

		this.weapperd = exceptionHandler;
	}

	// SOBRESCREVE O MÉTODO EXCEPTIONHANDLER QUE TORNA A "PILHA" DE EXCESSÕES

	@Override
	public ExceptionHandler getWrapped() {
		return weapperd;
	}

	// SOBREESCREVE O MÉTODO HANDLE QUE É RESPONSÁVEL POR MANIPULAR AS EXCESSÕES
	// DO JSF

	@Override
	public void handle() throws FacesException

	{
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {

			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			// RECUPERAR A EXCESSÃO DO CONTEXTO

			Throwable exeption = context.getException();

			// AQUI TRABALHAMOS A EXCESSÃO

			try {

				requestMap.put("exeptionMessage", exeption.getMessage());
				if (exeption != null && exeption.getMessage() != null
						&& exeption.getMessage().indexOf("ConstraintViolationException") != -1) {

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registro não pode ser removido " + "estar associado.", ""));

				} else if (exeption != null && exeption.getMessage() != null
						&& exeption.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registro foi atualizado ou excluido por outro usuario." + "Consulte Novamente", ""));

				} else {
					// AVISA O USUÁRIO DO ERRO
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O sistema se recuperou de um erro inesperado", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Você pode continuar usando o sistema normalmente", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"o erro  foi causado por :\n" + exeption.getMessage(), ""));

					// ESSE ALERT APENAS É EXIBIDO SE A PÁGINA NÃO REDIRECIONAR
					RequestContext.getCurrentInstance()
							.execute("alert('O sistema se recuperou de um erro inesperado')");

					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
							"erro", "o sistemas se recuperou de um erro inesperado"));

					// REDIRECIONAMENTO PARA PAGINA DE ERRO
					navigationHandler.handleNavigation(facesContext, null,
							"/error/error.jsf?faces-redirect=true&expired=true");
				}

				// RENDERIZA A PAGINNA DE ERRO E EXIBE AS MENSAGENS
				facesContext.renderResponse();

			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}

				// IMPRIME O ERRO NO CONSOLE

				exeption.printStackTrace();
				iterator.remove();
			}

		}

		getWrapped().handle();
	}

}
