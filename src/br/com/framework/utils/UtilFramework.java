package br.com.framework.utils;

import java.io.Serializable;

public class UtilFramework implements Serializable{

	
	private static final long serialVersionUID = 1L;

	// carregar codigo dos usuarios
	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
	
	// para não haver concorrencia ...sicronizado
	
	public synchronized static ThreadLocal<Long> getThreadLocal(){
		
		return threadLocal;
	}
}
