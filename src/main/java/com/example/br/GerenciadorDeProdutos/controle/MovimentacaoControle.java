package com.example.br.GerenciadorDeProdutos.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.br.GerenciadorDeProdutos.modelo.MovimentacaoModelo;
import com.example.br.GerenciadorDeProdutos.servico.MovimentacaoServico;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoControle {

    @Autowired
    private MovimentacaoServico servico;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody MovimentacaoModelo mov) {
        return servico.registrar(mov);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return servico.listar();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarUsuario(@PathVariable Long id) {
        return servico.listarUsuario(id);
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<?> listarProduto(@PathVariable Long id) {
        return servico.listarProduto(id);
    }

}
