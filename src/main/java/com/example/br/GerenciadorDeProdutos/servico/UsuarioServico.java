package com.example.br.GerenciadorDeProdutos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.br.GerenciadorDeProdutos.modelo.RespostaModelo;
import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private RespostaModelo respostaModelo;

    public ResponseEntity<?> cadastrar(UsuarioModelo usuario, String acao) {

        if (usuario.getLogin() == null || usuario.getLogin().isEmpty() || usuario.getLogin().length() < 5) {
            respostaModelo.setMensagem("O login deve conter mais de 5 dígitos");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        if (usuario.getSenha() == null || usuario.getSenha().isEmpty() || usuario.getSenha().length() <= 4) {
            respostaModelo.setMensagem("A senha deve conter mais de 4 dígitos");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        if (acao.equals("cadastrar")) {
            if (usuarioRepo.findByLogin(usuario.getLogin()).isPresent()) {
                respostaModelo.setMensagem("Este login já está sendo utilizado");
                return ResponseEntity.badRequest().body(respostaModelo);
            }
        }

        UsuarioModelo salvo = usuarioRepo.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    public ResponseEntity<?> logar(UsuarioModelo usuario) {
        UsuarioModelo u = usuarioRepo.findByLogin(usuario.getLogin()).orElse(null);

        if (u == null) {
            respostaModelo.setMensagem("Usuário não encontrado");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        if (!u.getSenha().equals(usuario.getSenha())) {
            respostaModelo.setMensagem("Senha incorreta!");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        return ResponseEntity.ok(u);
    }

    public ResponseEntity<?> alterarSenha(String login, String novaSenha) {
        UsuarioModelo u = usuarioRepo.findByLogin(login).orElse(null);

        if (u == null) {
            respostaModelo.setMensagem("Login não encontrado");
            return ResponseEntity.badRequest().body(respostaModelo);
        }

        if (novaSenha == null || novaSenha.isEmpty() || novaSenha.length() <= 4) {
            respostaModelo.setMensagem("A nova senha deve conter mais de 4 dígitos");
            return ResponseEntity.badRequest().body(respostaModelo);
        }


        u.setSenha(novaSenha);
        usuarioRepo.save(u);

        respostaModelo.setMensagem("Senha alterada com sucesso!");
        return ResponseEntity.ok(respostaModelo);
    }

}
