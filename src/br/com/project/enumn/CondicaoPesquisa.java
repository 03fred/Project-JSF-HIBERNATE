package br.com.project.enumn;

public enum CondicaoPesquisa {

	CONTEM("Contém"), INICIA("Inicia com"),
	TERMINA_COM ("termina com"), IGUAL_A ("Igual");
	
	private String condicao;
	private CondicaoPesquisa(String condicao){
		
		
		this.condicao = condicao;
	}
	public String getCondicao() {
		return condicao;
	}
	@Override
	public String toString() {

		return this.condicao;
	}
	
}
