package com.example.br.GerenciadorDeProdutos.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.br.GerenciadorDeProdutos.modelo.CategoriaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;

import java.util.List;

public interface CategoriaRepositorio extends CrudRepository<CategoriaModelo, Long> {

    List<CategoriaModelo> findByUsuario(UsuarioModelo usuario);

    boolean existsByNomeAndUsuario(String nome, UsuarioModelo usuario);

}
