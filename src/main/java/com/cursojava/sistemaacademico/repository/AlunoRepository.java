package com.cursojava.sistemaacademico.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cursojava.sistemaacademico.model.Aluno;

@Repository
public class AlunoRepository {
    private List<Aluno> alunos = new ArrayList<>();
    private Long proximoId = 1L;

    public List<Aluno> listarTodos() {
        return alunos;
    }

    public Aluno buscarPorId(Long id) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == id) {
                return aluno;
            }
        }

        return null;
    }

    public Aluno salvar(String nome, String email, String matricula, int idade) {

        Aluno aluno = new Aluno(proximoId, nome, email, matricula, idade);

        alunos.add(aluno);
        proximoId++;

        return aluno;

    }

}
