package com.cursojava.sistemaacademico.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private int idade;
    private List<Nota> notas = new ArrayList<>();

    public Aluno () {

    }
    
    

    public Aluno(Long id, String nome, String email,
            String matricula, int idade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.idade = idade;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void SetNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
}

public void setMatricula(String matricula) {
    this.matricula = matricula;
}

public int getIdade() {
    return idade;
}
    
public void setIdade(int idade) {
    this.idade = idade;
}


    public List<Nota> getNotas() {
        return notas;
    }

    public void adicionarNota(Nota nota) {
        notas.add(nota);
    }

    public double getMedia() {
        if (notas.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;

        for (Nota nota : notas) {
            soma += nota.getValor();
        }

        return soma / notas.size();
    }

    public String getSituacao() {
        if(notas.isEmpty()) {
            return "Sem notas";
        }

        if (getMedia() >= 7.0) {
            return "Aprovado";
        }

        return "Reprovado";
    }
}
