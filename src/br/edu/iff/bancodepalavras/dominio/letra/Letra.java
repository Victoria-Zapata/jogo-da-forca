package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class Letra {
	private char codigo;
	
	protected Letra(char codigo) {
		this.codigo = codigo;
	}
	
	public char getCodigo() {
		return this.codigo;
	}
	
	public abstract void exibir(Object contexto);
	
	public boolean equals(Object o) {
		if (!(o instanceof Letra)) {
			return false;
		}
	    Letra outra = (Letra) o; //typecast
	    return this.codigo == outra.codigo && this.getClass().equals(outra.getClass());
	     
	}
	
	public int hashCode() {
		return this.codigo+this.getClass().hashCode();
		
	}
	
	public final String toString() {
		return "Código: " +codigo;
		
	}
}
