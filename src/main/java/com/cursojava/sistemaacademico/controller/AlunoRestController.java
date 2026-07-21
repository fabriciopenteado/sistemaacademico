package com.cursojava.sistemaacademico.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.sistemaacademico.model.Aluno;
import com.cursojava.sistemaacademico.model.Nota;
import com.cursojava.sistemaacademico.service.AlunoService;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @GetMapping("/busca")
    public ResponseEntity<List<Aluno>> buscarPorNome(
            @RequestParam String nome) {
        if (nome.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
    
        List<Aluno> encontrados = alunoService.buscarPorNome(nome);
    
        return ResponseEntity.ok(encontrados);
        }
    
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        
        if(aluno == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(aluno);
        
    }
    
    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> listarNotas(
        @PathVariable Long id) {
            
            Aluno aluno = alunoService.buscarPorId(id);
            
            if (aluno == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(aluno.getNotas());
            
        }
        
        @GetMapping("/{id}/media")
        public ResponseEntity<Map<String, Object>> conultaMedia(
            @PathVariable Long id) {
                
                Aluno aluno = alunoService.buscarPorId(id);
                
                if (aluno == null) {
                    return ResponseEntity.notFound().build();
                }
                Map<String, Object> resposta = Map.of(
                    "aluno", aluno.getNome(),
                    "media", aluno.getMedia(),
                    "situcao", aluno.getSituacao()
                );
                
                return ResponseEntity.ok(resposta);
            }

        @PostMapping
         public ResponseEntity<Aluno> cadastrar(
            @RequestBody Aluno aluno) {
            
                    if (alunoService.dadosInvalidos(aluno)) {
                        return ResponseEntity.badRequest().build();
                    }
                    
                    Aluno alunoCadastrado = alunoService.cadastrarAluno(
                        aluno.getNome(),
                        aluno.getEmail(),
                        aluno.getMatricula(),
                        aluno.getIdade()
                    );
                    
                    return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(alunoCadastrado);
                }
                
    //Exercicio 1 - Rota /alunos @Controller retorna pagina HTML, Rota /api/alunos @RestController retorna JSON
                
        @PutMapping("/{id}")
        public ResponseEntity<Aluno> atualizar(
            @PathVariable Long id,
            @RequestBody Aluno dados) {
            
            Aluno alunoExistente = alunoService.buscarPorId(id);
            
            if (alunoExistente == null) {
                return ResponseEntity.notFound().build(); 
            }
            
            if (alunoService.dadosInvalidos(dados)) {
                return ResponseEntity.badRequest().build();
            }
            
            Aluno alunoAtualizado = alunoService.atualizarAluno(id, dados);
            return ResponseEntity.ok(alunoAtualizado);
                }
            
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluir(
            @PathVariable Long id) {
        
                boolean excluiu = alunoService.excluirAluno(id);
        
                if(!excluiu) {
                    return ResponseEntity.notFound().build();
                }
        
                return ResponseEntity.noContent().build();
            }
        
        @PostMapping("/{id}/notas")
        public ResponseEntity<Nota> cadastrarNota(
            @PathVariable Long id,
            @RequestBody Nota nota) {
        
                Aluno aluno = alunoService.buscarPorId(id);
        
            if (aluno == null) {
                return ResponseEntity.notFound().build();
            }
        
            if (alunoService.notaInvalida(nota)) {
                return ResponseEntity.badRequest().build();
            }
        
            Nota notaCadastrada = alunoService.cadastrarNota(id,
                nota.getDescricao(),
                nota.getValor()
                );
        
            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(notaCadastrada);
        
            }
                
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
