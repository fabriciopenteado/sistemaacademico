package com.cursojava.sistemaacademico.controller;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.sistemaacademico.model.Aluno;
import com.cursojava.sistemaacademico.model.Nota;
import com.cursojava.sistemaacademico.service.AlunoService;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

@RestController
@RequestMapping("/api/alunos")

public class AlunoRestController {
    private final AlunoService alunoService;

    public AlunoRestController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/{id}/notas")
    public List<Nota> listarNotas(@PathVariable long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return List.of();
        }

        return aluno.getNotas();

    }

    @GetMapping("/{id}/media")
    public Map<String, Object> consultaMedia(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return Map.of("erro", "Aluno nao encontrado");
        }
        return Map.of(
            "aluno", aluno.getNome(),
            "media", aluno.getMedia(),
            "situcao", aluno.getSituacao()
        );
    }


    //Exercicio 1 - Rota /alunos @Controller retorna pagina HTML, Rota /api/alunos @RestController retorna JSON


    @GetMapping("/quantidade")
    public Map<String, Object> quantidadeAlunos() {
        return Map.of("Quantidade",alunoService.listarTodos().size());
       
    }
            
    @GetMapping("/nomes")
    public List<String> nomeAlunos() {
        List<String> nomes = new ArrayList<>();
                
            for (Aluno aluno : alunoService.listarTodos()) {
                nomes.add(aluno.getNome());
            }
                  
            return nomes;
    } 
        
    @GetMapping("/aprovados")
    public List<Aluno> alunoAprovado() {
        List<Aluno> aprovados = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if(aluno.getSituacao() == "Aprovado") {
                aprovados.add(aluno);
            }
        }

        return aprovados;

    }

    @GetMapping("/reprovados")
    public List<Aluno> alunoReprovado() {
        List<Aluno> reprovados = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if(aluno.getSituacao() == "Reprovado") {
                reprovados.add(aluno);
            }
        }

        return reprovados;

    }

    @GetMapping("/sem-nota")
    public List<Aluno> alunoSemNota() {
        List<Aluno> semNota = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if(aluno.getNotas().isEmpty()) {
                semNota.add(aluno);
            }
        }

        return semNota;

    }

    @GetMapping("/{id}/boletim")
    public Map<String, Object> boletimAluno(@PathVariable Long id) {

        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return Map.of("erro", "Aluno nao encontrado");
        }
        return Map.of(
            "aluno", aluno.getNome(),
            "matricula", aluno.getMatricula(), 
            "media", aluno.getMedia(),
            "situcao", aluno.getSituacao()
        );
        
    }

    @GetMapping("/{id}/email")
    public Map<String, Object> emailAluno(@PathVariable Long id) {

        
        Aluno aluno = alunoService.buscarPorId(id);
            
            if (aluno == null) {
                return Map.of ("erro", "Aluno não encontrado");
            }
            
            return Map.of(
                "aluno", aluno.getNome(),
                "email", aluno.getEmail()
            );
            
        }
    

//Exercicio 9 - O Tratamento de erro quando não encontra o aluno foi aplicado na Rota GET /api/alunos/{id}/media - 
// if (aluno == null) {return Map.of("erro", "Aluno nao encontrado"); 

//Exercicio 10 - AlunoController - constrola o GET e Post retornando paginas HTML, AlunoRestController - Controla os GET para retorno de dados, normalmente no formato JASON 

}
