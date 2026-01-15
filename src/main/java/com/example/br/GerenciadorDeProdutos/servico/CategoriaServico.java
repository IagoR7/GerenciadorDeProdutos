package com.example.br.GerenciadorDeProdutos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.br.GerenciadorDeProdutos.modelo.CategoriaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.RespostaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.CategoriaRepositorio;

import java.util.List;

@Service
public class CategoriaServico {

    @Autowired
    private CategoriaRepositorio categoriaRepo;

    @Autowired
    private RespostaModelo respostaModelo;

    public ResponseEntity<?> cadastrarCategoria(CategoriaModelo categoria) {

        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            respostaModelo.setMensagem("O nome da categoria é obrigatório!");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        if (categoria.getUsuario() == null || categoria.getUsuario().getId() == null) {
            respostaModelo.setMensagem("O usuário da categoria é obrigatório!");
            return ResponseEntity.badRequest().body(respostaModelo);
        }
        boolean existe = categoriaRepo.existsByNomeAndUsuario(
                categoria.getNome(),
                categoria.getUsuario()
        );

        if (existe) {
            respostaModelo.setMensagem("Essa categoria já existe para este usuário!");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        return ResponseEntity.ok(categoriaRepo.save(categoria));
    }

    public ResponseEntity<?> Listar(UsuarioModelo usuario) {


        List<CategoriaModelo> lista = categoriaRepo.findByUsuario(usuario);

        return ResponseEntity.ok(lista);
    }
}
