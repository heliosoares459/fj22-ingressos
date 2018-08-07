package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.validation.GerenciadorDeSessoes;

@Controller
public class SessaoController {

	@Autowired
	private FilmeDao filmeDao;
	@Autowired
	private SalaDao salaDao;
	@Autowired
	private SessaoDao sessaoDao;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm sessaoForm) {

		ModelAndView view = new ModelAndView("sessao/sessao");
		sessaoForm.setSalaId(salaId);
		view.addObject("filmes", filmeDao.findAll());
		view.addObject("sala", salaDao.findOne(salaId));
		view.addObject("form", sessaoForm);

		return view;
	}

	@Transactional
	@PostMapping("/admin/sessao")
	public ModelAndView salvarSessao(@Valid SessaoForm sessaoForm, BindingResult result) {
		if (result.hasErrors()) {
			return form(sessaoForm.getSalaId(), sessaoForm);
		}
		Sessao sessaoNova = sessaoForm.toSessao(salaDao, filmeDao);
		List<Sessao> sessoes = sessaoDao.findAllBySala(salaDao.findOne(sessaoForm.getSalaId()));
		if(!GerenciadorDeSessoes.sessaoEstaDisponivel(sessoes,sessaoNova )){
			return form(sessaoForm.getSalaId(), sessaoForm);
		}

		ModelAndView view = new ModelAndView("redirect:/admin/sala/" + sessaoForm.getSalaId() + "/sessoes");
		sessaoDao.save(sessaoNova);
		return view;
	}

}
