package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L; //controle de versão para essa classe
	
	private String userName;//email
	private String password;//senha
	private List<GrantedAuthority> authorities;// autorização que o usuário tem
	
	//metodo construtor p/  usuário
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}
	
    // segundo metodo construtor para autorização
	public UserDetailsImpl() {	}

	@Override // a ? é uma variavel generica, pq não tem uma lista especifica de autorizações
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override // retorna a senha 
	public String getPassword() {

		return password;
	}

	@Override //email ou usuario
	public String getUsername() {

		return userName;
	}

	@Override // ajudar a verificar se o acesso já expirou
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override // auxilia a validação de o usuário esta bloqueado
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override //auxilia a validar se a credencial expira
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override // validar se o user esta ativo
	public boolean isEnabled() {
		return true;
	}

}
