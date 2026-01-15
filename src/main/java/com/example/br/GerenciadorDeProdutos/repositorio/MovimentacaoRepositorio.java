package com.example.br.GerenciadorDeProdutos.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.br.GerenciadorDeProdutos.modelo.MovimentacaoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.ProdutoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;

import java.util.List;

public interface MovimentacaoRepositorio extends CrudRepository<MovimentacaoModelo, Long> {

    List<MovimentacaoModelo> findByProduto(ProdutoModelo produto);

    List<MovimentacaoModelo> findByUsuario(UsuarioModelo usuario);
}
