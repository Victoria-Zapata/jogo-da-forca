package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class LetraFactoryImpl implements LetraFactory {
	
	private Letra[] pool;
	private Letra encoberta;
	
	protected LetraFactoryImpl() {
		this.pool = new Letra[26];
		
	}
	@Override
	public final Letra getLetra(char codigo) {
		codigo = Character.toLowerCase(codigo);
		
		int i = codigo - 'a';
		
		if(pool[i] != null) {
			return pool[i];
		}
		else {
			pool[i] = criarLetra(codigo);
			return pool[i];
		}
		
	}
	@Override
	public final Letra getLetraEncoberta() {
		if(this.encoberta != null) {
			return this.encoberta;
		}
		else {
			this.encoberta = criarLetra('_');
			return this.encoberta;
		}
	}
	
	protected abstract Letra criarLetra(char codigo);
	
	
	
}