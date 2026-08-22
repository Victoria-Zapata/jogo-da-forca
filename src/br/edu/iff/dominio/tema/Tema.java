package br.edu.iff.dominio.tema;

public class Tema {
    private String nome;

    public criar (long id, String nome){
        this.setNome(nome);
    }


    public reconstituir(long id, String nome){
        return null;

    
    }

    public Tema(long id, String nome) {
        this.nome = nome;
        this.id= id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do nosso tema não pode ser nulo ou vazio");
        } else {
            this.nome = nome;
        }
    }
}