package br.edu.iff.bancodepalavras.dominio.tema;

public class Tema {
    private String nome;
   


    public Tema(long id, String nome) {
          super();
          this.setNome(nome);
      
    }

    public Tema criar (long id, String nome){
        return new Tema(id, nome);
    }


    public Tema reconstituir(long id, String nome){
      return new Tema(id, nome);

    
    }

   public String getNome() {
       return nome;
   }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do nosso tema não pode ser nulo ou vazio");
        } else {
            this.nome = nome;
        }
    }
}