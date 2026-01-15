package com.example.br.GerenciadorDeProdutos.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movimentacoes")
public class MovimentacaoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; 

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao;

    @ManyToOne
    @JoinColumn(name = "produtos_id", nullable = false)
    private ProdutoModelo produto;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private UsuarioModelo usuario;
}
