package br.com.caelum.ingresso.validation;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessoesTests {
	List<Sessao> sessoes;
	LocalTime horario;
	Sala sala;
	Filme filme;
	Sessao sessaoNova;
	
	@org.junit.Before
	public void preparadorCenario(){
		// simulacao do cenario
		sessoes = Collections.emptyList();
		horario = LocalTime.of(10,0);
		sala = new Sala("Teste JUnit");
		filme = new Filme("Prince of Persia The Sands of Time",Duration.ofMinutes(120), "Ação/Aventura");
		sessaoNova = new Sessao(filme, sala, horario);
	}
	
	@Test
	public void validacaoDeListVazia(){
		// Executa a logica 
		boolean sessaoEstaDisponivel = GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes, sessaoNova);
		// verifica o resultado
		Assert.assertTrue(sessaoEstaDisponivel);
	}
	
	@Test
	public void naoPermiteSessaoNoMesmoHorario(){
		List<Sessao> sessoes2 = new ArrayList<>();
		sessoes2.add(sessaoNova);
		Assert.assertFalse(GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes2, sessaoNova));
	}
	
	@Test
	public void naoPermiteFilmesComSessaoTerminandoDentroDoHorarioDeOutra(){
		List<Sessao> sessoes2 = new ArrayList<>();
		sessoes2.add(sessaoNova);
		filme.setDuracao(90);
		Sessao sessaoTeste = new Sessao(filme,sala,horario);
		Assert.assertFalse(GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes2, sessaoTeste));
	}
	
	@Test
	public void sessaoIniciandoDentroDoHorarioDeOutra(){
		List<Sessao> sessoes2 = new ArrayList<>();
		sessoes2.add(sessaoNova);
		Sessao sessaoTeste = new Sessao(filme,sala,horario.plusHours(1));
		Assert.assertFalse(GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes2, sessaoTeste));
	}
	
	@Test
	public void sessaoEntreDoisFilmes(){
		List<Sessao> sessoes2 = new ArrayList<>();
		
		Sessao sessaoTeste = new Sessao(filme,sala,horario.plusHours(3));
		Sessao sessaoIntermediaria = new Sessao(filme,sala,horario.minusHours(1));
		
		sessoes2.add(sessaoNova);
		sessoes2.add(sessaoTeste);
		Assert.assertFalse(GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes2, sessaoIntermediaria));
	}
	
}
