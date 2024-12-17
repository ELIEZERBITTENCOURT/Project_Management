package com.example.project_management.controller;

import com.example.project_management.model.Projeto;
import com.example.project_management.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public List<Projeto> listarTodos() {
        return projetoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return projetoService.salvar(projeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizar(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        return projetoService.buscarPorId(id)
                .map(projeto -> {
                    projeto.setNome(projetoAtualizado.getNome());
                    projeto.setDescricao(projetoAtualizado.getDescricao());
                    projeto.setPrazo(projetoAtualizado.getPrazo());
                    projeto.setStatus(projetoAtualizado.getStatus());
                    return ResponseEntity.ok(projetoService.salvar(projeto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (projetoService.buscarPorId(id).isPresent()) {
            projetoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}