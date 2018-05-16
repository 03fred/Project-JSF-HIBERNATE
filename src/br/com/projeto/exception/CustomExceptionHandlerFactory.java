package br.com.projeto.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory{

	
	private CustomExceptionHandlerFactory parent;
	
	
	 public CustomExceptionHandlerFactory(CustomExceptionHandlerFactory parent) {
	
	 this.parent =   parent;
	 }
	 
	 
	@Override
	public ExceptionHandler getExceptionHandler() {

		ExceptionHandler handler =  new CustomExceptionHandler(parent.getExceptionHandler());
		return  handler ;
	}

}
