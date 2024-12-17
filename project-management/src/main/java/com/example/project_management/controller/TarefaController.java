package com.example.project_management.controller;

import com.example.project_management.model.Tarefa;
import com.example.project_management.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return tarefaService.salvar(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return tarefaService.buscarPorId(id)
                .map(tarefa -> {
                    tarefa.setNome(tarefaAtualizada.getNome());
                    tarefa.setDescricao(tarefaAtualizada.getDescricao());
                    tarefa.setPrazo(tarefaAtualizada.getPrazo());
                    tarefa.setStatus(tarefaAtualizada.getStatus());
                    return ResponseEntity.ok(tarefaService.salvar(tarefa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (tarefaService.buscarPorId(id).isPresent()) {
            tarefaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
