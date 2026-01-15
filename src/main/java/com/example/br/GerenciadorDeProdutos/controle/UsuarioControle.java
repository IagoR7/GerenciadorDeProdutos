package com.example.br.GerenciadorDeProdutos.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.br.GerenciadorDeProdutos.modelo.UsuarioModelo;
import com.example.br.GerenciadorDeProdutos.servico.UsuarioServico;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioControle {

    private final UsuarioServico usuarioServico;

    public UsuarioControle(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioModelo um) {
        return usuarioServico.cadastrar(um, "cadastrar");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody UsuarioModelo um) {
        return usuarioServico.logar(um);
    }
    @PutMapping("/alterarSenha")
    public ResponseEntity<?> alterarSenha(@RequestBody UsuarioModelo usuario) {
        return usuarioServico.alterarSenha(usuario.getLogin(), usuario.getSenha());
    }

}
