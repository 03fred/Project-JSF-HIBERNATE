package br.com.project.util.all;

public class UtilitariaRegex {

	
	
	public String retiraAcentos(String string) {
		
		String aux= new String(string);
		aux = aux.replace("[����]","e");
		aux = aux.replace("[�������]","a");
		aux = aux.replace("[����]","i");
		aux = aux.replace("[����]","u");
		aux = aux.replace("[������]","o");

		return aux;
	}
}
