package com.example.usuario.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "endereco")
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", length = 100)
    private String rua;

    @Column(name = "numero", length = 5)
    private String numero;

    @Column(name = "complemento", length = 10)
    private String complemento;

    @Column(name = "cidade", length = 150)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;
}
