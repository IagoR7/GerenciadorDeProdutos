package com.example.br.GerenciadorDeProdutos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "produtos")
public class ProdutoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // novo campo codigo (ajuste o tipo/length conforme seu schema)
    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    private Integer quantidade;

    private Double preco;

    @ManyToOne
    @JoinColumn(name = "categorias_id", nullable = false)
    private CategoriaModelo categoria;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private UsuarioModelo usuario;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
}
