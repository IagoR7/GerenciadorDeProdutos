package com.example.br.GerenciadorDeProdutos.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;

import java.util.Optional;

public interface UsuarioRepositorio extends CrudRepository<UsuarioModelo, Long> {

    Optional<UsuarioModelo> findByLogin(String login);

}
