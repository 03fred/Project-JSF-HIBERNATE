package br.com.project.util.all;

public class UtilitariaRegex {

	
	
	public String retiraAcentos(String string) {
		
		String aux= new String(string);
		aux = aux.replace("[éÊêÉ]","e");
		aux = aux.replace("[áâÁÂãÃÂ]","a");
		aux = aux.replace("[Íîîí]","i");
		aux = aux.replace("[úÚÛû]","u");
		aux = aux.replace("[õÕÓóôÔ]","o");

		return aux;
	}
}
