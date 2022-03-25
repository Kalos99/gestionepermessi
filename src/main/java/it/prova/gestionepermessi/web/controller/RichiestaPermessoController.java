package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiesta_permesso")
public class RichiestaPermessoController {
	
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@GetMapping
	public ModelAndView listAllRichieste() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richieste = richiestaPermessoService.listAllRichieste();
		mv.addObject("richiesta_list_attribute", RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richieste));
		mv.setViewName("richiesta_permesso/list");
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
}
