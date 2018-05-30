package br.com.project.geral.controller;

import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

@ApplicationScoped
public class SessionControllerImpl implements SessionController {

	private static final long serialVersionUID = 1L;

	private HashMap<String, HttpSession> hashMap = new HashMap<String, HttpSession>();

	@Override
	public void addSession(String KeyLoginUser, HttpSession httpSession) {

		hashMap.put(KeyLoginUser, httpSession);
	}

	@Override
	public void invalidateSession(String KeyLoginUser) {

		HttpSession session = hashMap.get(KeyLoginUser);
		// REMOVE SESSÃO USUÁRIO PASSADO COMO PARAMETRO
		if (session != null) {
			try {
				session.invalidate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			System.out.println("não tem usuário");

		}

		hashMap.remove(KeyLoginUser);
	}

}
