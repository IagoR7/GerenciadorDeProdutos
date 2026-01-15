package com.example.br.GerenciadorDeProdutos.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.br.GerenciadorDeProdutos.modelo.CategoriaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.CategoriaRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.UsuarioRepositorio;
import com.example.br.GerenciadorDeProdutos.servico.CategoriaServico;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
public class CategoriaControle {

    @Autowired
    private CategoriaRepositorio cr;

    @Autowired
    private UsuarioRepositorio ur;

    
    @Autowired
    private CategoriaServico categoriaServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody CategoriaModelo cm) {
        return categoriaServico.cadastrarCategoria(cm);
    }

  
    @GetMapping("/listar/usuario/{idUsuario}")
    public ResponseEntity<?> Listar(@PathVariable Long idUsuario) {
        Optional<UsuarioModelo> usuario = ur.findById(idUsuario);
        List<CategoriaModelo> lista = cr.findByUsuario(usuario.get());
        return ResponseEntity.ok(lista);
    }

}
