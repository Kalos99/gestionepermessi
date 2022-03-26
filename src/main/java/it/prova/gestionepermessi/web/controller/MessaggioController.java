package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.service.MessaggioService;

@SessionAttributes("unread_messages")
@Controller
@RequestMapping(value = "/messaggio")
public class MessaggioController {
	
	@Autowired
	private MessaggioService messaggioService;
	
	@GetMapping
	public ModelAndView listAllMessaggi(Model model) {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> messaggi = messaggioService.listAllMessaggi();
		mv.addObject("messaggio_list_attribute", MessaggioDTO.createMessaggioDTOListFromModelList(messaggi));
		checkMessageIfBO(model);
		mv.setViewName("messaggio/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String searchMessaggi(Model model) {
		checkMessageIfBO(model);
		return "messaggio/search";
	}
	
	@PostMapping("/list")
	public String listMessaggi(MessaggioDTO messaggioExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			Model model) {

		List<Messaggio> messaggi = messaggioService.findByExample(messaggioExample.buildMessaggioModel(false), pageNo,
				pageSize, sortBy).getContent();

		model.addAttribute("messaggio_list_attribute", MessaggioDTO.createMessaggioDTOListFromModelList(messaggi));
		checkMessageIfBO(model);
		return "messaggio/list";
	}
	
	@GetMapping("/show/{idMessaggio}")
	public String showRichiesta(@PathVariable(required = true) Long idMessaggio, Model model) {
		messaggioService.leggiMessaggio(idMessaggio);
		model.addAttribute("show_messaggio_attr", messaggioService.caricaSingoloMessaggioEager(idMessaggio));
		checkMessageIfBO(model);
		return "messaggio/show";
	}
	
	private void checkMessageIfBO(Model modelInput) {
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
		if(roles.contains("ROLE_BO_USER")) {
			modelInput.addAttribute("unread_messages", messaggioService.numeroMessaggiNonLetti());
		}
	}

}
