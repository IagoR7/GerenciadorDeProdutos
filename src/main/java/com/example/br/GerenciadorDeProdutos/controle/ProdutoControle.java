package com.example.br.GerenciadorDeProdutos.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.br.GerenciadorDeProdutos.modelo.ProdutoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.RespostaModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.ProdutoRepositorio;
import com.example.br.GerenciadorDeProdutos.servico.ProdutoServico;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoControle {

    @Autowired
    private ProdutoServico ps;

    @Autowired
    private ProdutoRepositorio pr;

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<?> listar(@PathVariable Long idUsuario) {
        return ps.listar(idUsuario);
    }

    
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoModelo pm) {
        return ps.cadastrar(pm, "cadastrar");
    }

    
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody ProdutoModelo pm) {
        return ps.cadastrar(pm, "alterar");
    }

    
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
        return ps.remover(id);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<ProdutoModelo> produto = pr.findById(id);
        return ResponseEntity.ok(produto.get());
    }
}
