package br.com.caelum.ingresso.model;

import java.time.LocalTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sessao {
	@Id
    @GeneratedValue
	private Integer id;
	@ManyToOne
	private Filme filme;
	@ManyToOne
	private Sala sala;
	private LocalTime horario;
	
	/**
	 * @deprecated Somente para uso do hibernate!
	 */
	public Sessao() {
	}
	
	public Sessao(Filme filme, Sala sala, LocalTime horario ) {
		this.filme = filme;
		this.sala = sala;
		this.horario = horario;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public LocalTime getHorario() {
		return horario;
	}
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	
	public LocalTime getHorarioTermino(){
		return this.horario.plusMinutes(this.filme.getDuracao().toMinutes());
	}
	
}
