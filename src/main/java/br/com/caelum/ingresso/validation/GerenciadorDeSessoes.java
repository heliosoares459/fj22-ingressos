package br.com.caelum.ingresso.validation;

import java.util.List;
import java.util.stream.Stream;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessoes {

	public static boolean sessaoEstaDisponivel(List<Sessao> sessoes, Sessao sessaoNova) {
		Stream<Sessao> streamSessao = sessoes.stream();
		return streamSessao.noneMatch(sessaoExistente -> isConflitante(sessaoExistente, sessaoNova));
		/*for (Sessao sessaoExistente : sessoes) {
			if (isConflitante(sessaoExistente, sessaoNova)) {
				return false;
			}
		}
		return true;*/
	}

	private static boolean isConflitante(Sessao sessaoExistente, Sessao sessaoNova) {
		boolean sessaoNovaTerminaAntesDaExistente = sessaoNova.getHorarioTermino().isBefore(sessaoExistente.getHorario());
		boolean sessaoNovaComecaDepoisDaExistente = sessaoNova.getHorario().isAfter(sessaoExistente.getHorarioTermino());
		if(sessaoNovaTerminaAntesDaExistente || sessaoNovaComecaDepoisDaExistente){
			return false;
		}
		return true;
	}

}
