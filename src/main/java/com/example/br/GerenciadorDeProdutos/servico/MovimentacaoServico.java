package com.example.br.GerenciadorDeProdutos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.br.GerenciadorDeProdutos.modelo.MovimentacaoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.ProdutoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.RespostaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.MovimentacaoRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.ProdutoRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.UsuarioRepositorio;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class MovimentacaoServico {

    @Autowired
    private MovimentacaoRepositorio movimentacaoRepo;

    @Autowired
    private ProdutoRepositorio produtoRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private RespostaModelo respostaModelo;

    public ResponseEntity<?> registrar(MovimentacaoModelo mov) {

        Optional<ProdutoModelo> produtoOpt = produtoRepo.findById(mov.getProduto().getId());
        if (!produtoOpt.isPresent()) {
            respostaModelo.setMensagem("Produto não encontrado");
            return ResponseEntity.badRequest().body(respostaModelo);
        }
        ProdutoModelo produto = produtoOpt.get();


        Optional<UsuarioModelo> usuarioOpt = usuarioRepo.findById(mov.getUsuario().getId());
        UsuarioModelo usuario = usuarioOpt.get();

        if (mov.getTipo().equalsIgnoreCase("entrada")) {
            produto.setQuantidade(produto.getQuantidade() + mov.getQuantidade());
        } else if (mov.getTipo().equalsIgnoreCase("saida")) {

            if (produto.getQuantidade() < mov.getQuantidade()) {
                respostaModelo.setMensagem("Quantidade insuficiente no estoque");
                return ResponseEntity.badRequest().body(respostaModelo);
            }
            produto.setQuantidade(produto.getQuantidade() - mov.getQuantidade());
        } else {
            respostaModelo.setMensagem("Tipo inválido! Use 'entrada' ou 'saida'.");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        produtoRepo.save(produto);

        mov.setProduto(produto);
        mov.setUsuario(usuario);
        mov.setDataMovimentacao(LocalDateTime.now());

        movimentacaoRepo.save(mov);

        respostaModelo.setMensagem("Movimentação registrada com sucesso");
        return ResponseEntity.ok(respostaModelo);
    }

   
    public ResponseEntity<?> listarProduto(Long idProduto) {
        Optional<ProdutoModelo> produto = produtoRepo.findById(idProduto);

        if (!produto.isPresent()) {
            respostaModelo.setMensagem("Produto não encontrado!");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        List<MovimentacaoModelo> lista = movimentacaoRepo.findByProduto(produto.get());
        return ResponseEntity.ok(lista);
    }

    
    public ResponseEntity<?> listarUsuario(Long idUsuario) {
        Optional<UsuarioModelo> usuario = usuarioRepo.findById(idUsuario);

        List<MovimentacaoModelo> lista = movimentacaoRepo.findByUsuario(usuario.get());
        return ResponseEntity.ok(lista);
    }
    
public ResponseEntity<?> listar() {
    return ResponseEntity.ok(movimentacaoRepo.findAll());
}


}
