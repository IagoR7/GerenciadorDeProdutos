package com.example.br.GerenciadorDeProdutos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.br.GerenciadorDeProdutos.modelo.CategoriaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.MovimentacaoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.ProdutoModelo;
import com.example.br.GerenciadorDeProdutos.modelo.RespostaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.CategoriaRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.MovimentacaoRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.ProdutoRepositorio;
import com.example.br.GerenciadorDeProdutos.repositorio.UsuarioRepositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio pr;

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private CategoriaRepositorio cr;

    @Autowired
    private RespostaModelo rm;

    @Autowired
    private MovimentacaoRepositorio movimentacaoRepo;

    public ResponseEntity<?> cadastrar(ProdutoModelo pm, String acao) {

        if (pm.getNome() == null || pm.getNome().trim().isEmpty()) {
            rm.setMensagem("O nome do produto é obrigatório");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }

        Integer qtdRecebida = pm.getQuantidade();
        if (qtdRecebida == null) {
            pm.setQuantidade(0);
        } else if (qtdRecebida < 0) {
            rm.setMensagem("A quantidade não pode ser negativa");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }


        if (pm.getPreco() == null || pm.getPreco() <= 0) {
            rm.setMensagem("O preço deve ser maior do que zero");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }


   
        if (pm.getCategoria() == null || pm.getCategoria().getId() == null) {
            rm.setMensagem("Categoria do produto é obrigatória");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }

        Optional<CategoriaModelo> categoria = cr.findById(pm.getCategoria().getId());
        if (categoria.isEmpty()) {
            rm.setMensagem("Categoria não encontrada");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }
        pm.setCategoria(categoria.get());

        //alterar
        int quantidadeAntes = 0;
        ProdutoModelo pExist = null;
        if ("alterar".equalsIgnoreCase(acao)) {
            if (pm.getId() == null) {
                rm.setMensagem("ID do produto para alteração é obrigatório");
                return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
            }

            Optional<ProdutoModelo> existente = pr.findById(pm.getId());
            if (existente.isEmpty()) {
                rm.setMensagem("Produto não encontrado");
                return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
            }

            pExist = existente.get();
            
            if (pm.getCodigo() == null || pm.getCodigo().isBlank()) {
                pm.setCodigo(pExist.getCodigo());
            }
            if (pm.getDataCadastro() == null) {
                pm.setDataCadastro(pExist.getDataCadastro());
            }

            
            Integer qAntes = pExist.getQuantidade();
            quantidadeAntes = (qAntes == null) ? 0 : qAntes;
        } else { 
            pm.setDataCadastro(LocalDateTime.now());
            if (pm.getCodigo() == null || pm.getCodigo().isBlank()) {
                pm.setCodigo(UUID.randomUUID().toString());
            }
        }
        ProdutoModelo salvo = pr.save(pm);
        try {
            int qtdDepois = (salvo.getQuantidade() == null) ? 0 : salvo.getQuantidade();
            if ("cadastrar".equalsIgnoreCase(acao)) {
                if (qtdDepois > 0) {
                    MovimentacaoModelo mov = new MovimentacaoModelo();
                    mov.setProduto(salvo);
                    mov.setUsuario(salvo.getUsuario());
                    mov.setTipo("entrada");
                    mov.setQuantidade(qtdDepois);
                    mov.setDataMovimentacao(LocalDateTime.now());
                    movimentacaoRepo.save(mov);
                }
            } else if ("alterar".equalsIgnoreCase(acao)) {
                int delta = qtdDepois - quantidadeAntes;
                
                if (delta != 0) {
                    MovimentacaoModelo mov = new MovimentacaoModelo();
                    mov.setProduto(salvo);
                    mov.setUsuario(salvo.getUsuario());
                    mov.setQuantidade(Math.abs(delta));
                    
                    
                    mov.setTipo("Editado"); 
                    
                    mov.setDataMovimentacao(LocalDateTime.now());
                    movimentacaoRepo.save(mov);
                }
            }
        } catch (Exception e) {
            
            System.err.println("Erro ao gravar movimentação : " + e.getMessage());
        }

        rm.setMensagem("cadastrar".equalsIgnoreCase(acao)
                ? "Produto cadastrado com sucesso"
                : "Produto alterado com sucesso");

        return new ResponseEntity<>(rm,
                "cadastrar".equalsIgnoreCase(acao) ? HttpStatus.CREATED : HttpStatus.OK);
    }


    public ResponseEntity<?> listar(Long idUsuario) {
        Optional<UsuarioModelo> usuario = ur.findById(idUsuario);

        List<ProdutoModelo> produtos = pr.findByUsuario(usuario.get());

        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    
    public ResponseEntity<RespostaModelo> remover(long id) {
        Optional<ProdutoModelo> produto = pr.findById(id);

        if (produto.isEmpty()) {
            rm.setMensagem("Produto não encontrado");
            return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
        }
        try {
            ProdutoModelo p = produto.get();

           
            List<MovimentacaoModelo> movimentacoes = movimentacaoRepo.findByProduto(p);
            movimentacaoRepo.deleteAll(movimentacoes);

            
            pr.deleteById(id);

            rm.setMensagem("O Produto foi removido");
            return new ResponseEntity<>(rm, HttpStatus.OK);

        } catch (Exception e) {
            rm.setMensagem("Erro ao remover produto: " + e.getMessage());
            return new ResponseEntity<>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}