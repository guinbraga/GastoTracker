package com.example.gastotracker.model;

import com.example.gastotracker.enums.Categorias;
import com.example.gastotracker.enums.FormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Insira um valor positivo!")
    private BigDecimal valor;

    @Size(max = 200, message = "Máximo 200 caracteres")
    private String descricao;

//    @NotBlank(message = "Insira uma data válida!")
    @DateTimeFormat
    private LocalDateTime dataGasto;

    @NotBlank(message = "Selecione uma categoria válida!")
    private String categoria;

    @NotBlank(message = "Selecione uma forma de pagamento!")
    private String formaPagamento;

    // getters e setters

    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}

    public BigDecimal getValor() {return this.valor;}
    public void setValor(BigDecimal valor) {this.valor = valor;}

    public String getDescricao() {return this.descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public LocalDateTime getDataGasto() {return this.dataGasto;}
    public void setDataGasto(LocalDateTime dataGasto) {this.dataGasto = dataGasto;}

    public String getCategoria() {return this.categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}

    public String getFormaPagamento() {return this.formaPagamento;}
    public void setFormaPagamento(String formaPagamento) {this.formaPagamento = formaPagamento;}


}
