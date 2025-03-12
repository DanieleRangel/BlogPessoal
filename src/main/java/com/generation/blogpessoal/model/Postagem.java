package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// essa classe vai ser usada para criar uma tabela -> entidade
@Entity
@Table(name="tb_postagens")
public class Postagem {
	//chave primaria
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// não pode texto em branco = " "
	@NotBlank(message = " O atributo titúlo é obrigatório")
	// tamanho
	@Size(min = 5, max = 100, message = "O titúlo tem que ser maior que 5 e menor que 100")
	private String titulo;
	
	@NotBlank(message = " O atributo texto é obrigatório")
	@Size(min = 5, max = 1000, message = "O texto tem que ser maior que 5 e menor que 1000")
	private String texto;
	
	//pega automáticamente a data que a postagem foi feita
	@UpdateTimestamp
	private LocalDateTime data;
	
	@ManyToOne// Classe postagem muitas, tema é uma só
	@JsonIgnoreProperties("postagem")
	private Tema tema; //adicionando o objeto tema (id,descrição)
	
	@ManyToOne// Classe postagem muitas, tema é uma só
	@JsonIgnoreProperties("postagem")
	private Usuario usuario; 
	
	// gerar getters e setters (source -> Generate Getters and Setters)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
