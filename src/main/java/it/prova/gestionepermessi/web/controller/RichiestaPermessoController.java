package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;

import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/richiesta_permesso")
public class RichiestaPermessoController {
	
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private MessaggioService messaggioService;
	
	@GetMapping
	public ModelAndView listAllRichiestePermesso(Model model) {
		System.out.println("SEI NEL PATH BASE");
		ModelAndView mv = new ModelAndView();
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());
		if (roles.contains("ROLE_BO_USER")) {
			List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllRichieste();
			mv.addObject("richiesta_list_attribute",
					RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
			mv.addObject("path", "ricercaPermessi");
			checkMessageIfBO(model);
			mv.setViewName("richiesta_permesso/list");
		} else if (roles.contains("ROLE_DIPENDENTE_USER")) {
			List<RichiestaPermesso> richiestePermesso = richiestaPermessoService
					.listAllElementsByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			mv.addObject("richiesta_list_attribute",
					RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
			mv.addObject("path", "gestioneRichiestePermesso");
			mv.setViewName("richiesta_permesso/list");
		} else {
			mv.addObject("path", "home");
			mv.setViewName("home");
		}

		return mv;
	}
	
	@GetMapping("/search")
	public String searchRichieste(Model model) {
		model.addAttribute("dipendenti_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendenti()));
		return "richiesta_permesso/search";
	}
	
	@PostMapping("/list")
	public String listRichieste(RichiestaPermessoDTO richiestaExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		System.out.println("Sono dentro");
		List<RichiestaPermesso> richieste = richiestaPermessoService.findByExample(richiestaExample.buildRichiestaPermessoModel(false, true), pageNo,
				pageSize, sortBy).getContent();

		model.addAttribute("richiesta_list_attribute", RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richieste));
		return "richiesta_permesso/list";
	}
	
	@GetMapping("/insert")
	public String createRichiestaPermesso(Model model) {
		model.addAttribute("insert_richiesta_attr", new RichiestaPermessoDTO());
		return "richiesta_permesso/insert";
	}
	
	@PostMapping("/save")
	public String saveRichiestaPermesso(@Valid @ModelAttribute("insert_richiesta_attr") RichiestaPermessoDTO richiestaDTO, BindingResult result, @RequestParam(name = "usernameUtente", required = true) String username,
			Model model, RedirectAttributes redirectAttrs) {
		
		Utente utenteInPagina = utenteService.findByUsername(username);
		richiestaDTO.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));
		
		if (result.hasErrors()) {
			System.out.println(result.getFieldError());
			return "richiesta_permesso/insert";
		}

		richiestaPermessoService.inserisciNuovaECreaMessaggio(richiestaDTO.buildRichiestaPermessoModel(false, true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiesta_permesso";
	}
	
	@GetMapping("/show/{idRichiesta}")
	public String showRichiesta(@PathVariable(required = true) Long idRichiesta, Model model) {
		model.addAttribute("show_richiesta_attr", richiestaPermessoService.caricaSingolaRichiestaEager(idRichiesta));
		return "richiesta_permesso/show";
	}
	
	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idRichiestaForChangingStato", required = true) Long idRichiesta, RedirectAttributes redirectAttrs) {
		richiestaPermessoService.changeState(idRichiesta);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiesta_permesso";
	}
	
	@GetMapping("/search_personal")
	public String searchRichiestePersonali() {
		return "richiesta_permesso/search_personal";
	}
	
	@PostMapping("/list_personal")
	public String listRichiestePersonali(RichiestaPermessoDTO richiestaExample,@RequestParam(name = "usernameUtente", required = true) String username, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		Utente utenteInPagina = utenteService.findByUsername(username);
		richiestaExample.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));
		List<RichiestaPermesso> richieste = richiestaPermessoService.findByExample(richiestaExample.buildRichiestaPermessoModel(false, true), pageNo,
				pageSize, sortBy).getContent();

		model.addAttribute("richiesta_list_attribute", RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richieste));
		return "richiesta_permesso/list";
	}
	
	private void checkMessageIfBO(Model modelInput) {
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
		if(roles.contains("ROLE_BO_USER")) {
			modelInput.addAttribute("unread_messages", messaggioService.numeroMessaggiNonLetti());
		}
	}
}