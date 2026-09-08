package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.dominio.ObjetoDominioImpl;

public class Tema extends ObjetoDominioImpl {
    private String nome;
    private long id;

    public long getId() {
        return id;
    }

    Tema(long id, String nome) {
          super(id);
          this.nome = nome;
    }

    public static Tema criar (long id, String nome){
        return new Tema(id, nome); //factory que vai chamar
        //static pq tem acesso interno ao construtor e nao precisa ficar instanciando 
    }


    public static  Tema reconstituir(long id, String nome){
      return new Tema(id, nome);// repository que vai chamar
    }

   public String getNome() {
       return nome;
   }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("O tema não pode ser nulo ou vazio");
        } else {
            this.nome = nome;
        }
    }
}