package it.prova.gestionepermessi.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.exceptions.DataInizioPassataException;
import it.prova.gestionepermessi.exceptions.ElementNotFoundException;
import it.prova.gestionepermessi.exceptions.RichiestaApprovataException;
import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.AttachmentService;
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
	
	@Autowired
	private AttachmentService attachmentService;
	
	@GetMapping
	public ModelAndView listAllRichiestePermesso(Model model) {
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
			for (RichiestaPermesso r : richiestePermesso) {
				if (r.getAttachment() == null) {
					System.out.println("ATTACHMENT NULL");
				} else {
					System.out.println(r);
				}
			}
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
		checkMessageIfBO(model);
		return "richiesta_permesso/search";
	}
	
	@PostMapping("/list")
	public String listRichieste(RichiestaPermessoDTO richiestaExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			Model model) {
		
		Set<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (roles.contains("ROLE_DIPENDENTE_USER")) {
			Utente utenteInPagina = utenteService.findByUsername(username);
			richiestaExample.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));
		}
		List<RichiestaPermesso> richieste = richiestaPermessoService.findByExample(richiestaExample.buildRichiestaPermessoModel(true), pageNo,
				pageSize, sortBy).getContent();
		model.addAttribute("richiesta_list_attribute", RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richieste));
		checkMessageIfBO(model);
		return "richiesta_permesso/list";
	}
	
	@GetMapping("/insert")
	public String createRichiestaPermesso(Model model) {
		model.addAttribute("insert_richiesta_attr", new RichiestaPermessoDTO());
		return "richiesta_permesso/insert";
	}
	
	@PostMapping("/save")
	public String saveRichiestaPermesso(@Validated @ModelAttribute("insert_richiesta_attr") RichiestaPermessoDTO richiestaDTO, BindingResult result, @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttrs) {
		
		Utente utenteInPagina = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		richiestaDTO.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));
		
		if (result.hasErrors()) {
			return "richiesta_permesso/insert";
		}
		if (file == null || file.isEmpty()) {
			model.addAttribute("errorMessage", "Inserire dei valori");
			return "richiestapermesso/insert";
		}
		
		richiestaDTO.setAttachment(new Attachment());
		try {
			richiestaDTO.getAttachment().setContentType(file.getContentType());
			richiestaDTO.getAttachment().setNomeFile(file.getOriginalFilename());
			richiestaDTO.getAttachment().setPayload(file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Problema nell'upload file", e);
		}
		attachmentService.inserisciNuovo(richiestaDTO.getAttachment());
		richiestaPermessoService.inserisciNuovaECreaMessaggio(richiestaDTO.buildRichiestaPermessoModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiesta_permesso";
	}
	
	@GetMapping("/show/{idRichiesta}")
	public String showRichiesta(@PathVariable(required = true) Long idRichiesta, Model model) {
		model.addAttribute("show_richiesta_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoService.caricaSingolaRichiestaEager(idRichiesta)));
		checkMessageIfBO(model);
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
			Model model) {

		Utente utenteInPagina = utenteService.findByUsername(username);
		richiestaExample.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));
		List<RichiestaPermesso> richieste = richiestaPermessoService.findByExample(richiestaExample.buildRichiestaPermessoModel(true), pageNo,
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
	
	@GetMapping("/delete/{idRichiesta}")
	public String delete(@PathVariable(required = true) Long idRichiesta, Model model) {
		model.addAttribute("delete_richiesta_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoService.caricaSingolaRichiestaEager(idRichiesta)));
		return "richiesta_permesso/delete";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam(required = true) Long idRichiesta, RedirectAttributes redirectAttrs) {
	    try {
	        richiestaPermessoService.rimuovi(idRichiesta);
	      } catch (ElementNotFoundException e) {
	        redirectAttrs.addFlashAttribute("errorMessage", "Richiesta non trovata.");
	        return "redirect:/richiesta_permesso";
	      } catch (RichiestaApprovataException e) {
		    redirectAttrs.addFlashAttribute("errorMessage", "Impossibile rimuovere: la richiesta è già stata approvata");
		    return "redirect:/richiesta_permesso";
		  }catch (DataInizioPassataException e) {
			    redirectAttrs.addFlashAttribute("errorMessage", "Impossibile rimuovere: la data inizio della richiesta è già passata");
			    return "redirect:/richiesta_permesso";
			  }
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiesta_permesso";
	}
	
	@GetMapping("/edit/{idRichiesta}")
	public String edit(@PathVariable(required = true) Long idRichiesta, Model model) {
		RichiestaPermesso richiestaModel = richiestaPermessoService.caricaSingolaRichiesta(idRichiesta);
		model.addAttribute("edit_richiesta_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaModel));
		return "richiesta_permesso/edit";
	}
	
	@PostMapping("/update")
	public String update(@Validated @ModelAttribute("edit_richiesta_attr") RichiestaPermessoDTO richiestaDTO, BindingResult result, @RequestParam(name = "usernameUtente", required = true) String username, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
		
		Utente utenteInPagina = utenteService.findByUsername(username);
		richiestaDTO.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendenteConUtente(utenteInPagina.getId())));

		if (result.hasErrors()) {
			return "richiesta_permesso/edit";
		}
		try {
	        richiestaPermessoService.aggiornaRichiestaECreaNuovoMessaggio(richiestaDTO.buildRichiestaPermessoModel(true));
	      } catch (ElementNotFoundException e) {
	        redirectAttrs.addFlashAttribute("errorMessage", "Richiesta non trovata.");
	        return "redirect:/richiesta_permesso";
		  }catch (DataInizioPassataException e) {
			    redirectAttrs.addFlashAttribute("errorMessage", "Impossibile rimuovere: la data inizio della richiesta è già passata");
			    return "redirect:/richiesta_permesso";
			  }

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiesta_permesso";
	}
	
	@GetMapping("/showAttachment/{idAttachment}")
	public ResponseEntity<byte[]> showAttachment(@PathVariable(required = true) Long idAttachment) {

		Attachment file = attachmentService.caricaSingolo(idAttachment);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getNomeFile() + "\"")
				.body(file.getPayload());

	}
}