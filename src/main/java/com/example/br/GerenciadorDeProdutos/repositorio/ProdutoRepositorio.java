package com.example.br.GerenciadorDeProdutos.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.br.GerenciadorDeProdutos.modelo.ProdutoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;

import java.util.List;

public interface ProdutoRepositorio extends CrudRepository<ProdutoModelo, Long> {
    List<ProdutoModelo> findByUsuario(UsuarioModelo usuario);
    List<ProdutoModelo> findByNomeContainingIgnoreCase(String nome);
}