package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private RuoloService ruoloService;
	
	@GetMapping
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllDipendenti();
		mv.addObject("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		mv.setViewName("dipendente/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String searchDipendente(Model model) {
		return "dipendente/search";
	}

	@PostMapping("/list")
	public String listDipendenti(DipendenteDTO dipendenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "0") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Dipendente> dipendenti = dipendenteService.findByExample(dipendenteExample.buildDipendenteModel(true), pageNo,
				pageSize, sortBy).getContent();

		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "dipendente/list";
	}
	
	@GetMapping("/show/{idDipendente}")
	public String show(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr",
				DipendenteDTO.buildDipendenteDTOFromModel(dipendenteService.caricaSingoloDipendente(idDipendente)));
		return "dipendente/show";
	}
	
	@GetMapping("/insert")
	public String createDipendente(Model model) {
		model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllExceptAdmin()));
		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());
		return "dipendente/insert";
	}
	
	@PostMapping("/save")
	public String saveDipendente(@Valid @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO, BindingResult result,  String ruolo,
			Model model, RedirectAttributes redirectAttrs) {

		
		if (result.hasErrors()) {
			model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAllExceptAdmin()));
			return "dipendente/insert";
		}
		Ruolo ruoloDaInserire = ruoloService.cercaPerCodice(ruolo);
		System.out.println(ruoloDaInserire.toString());
		dipendenteService.inserisciNuovoECensisciUtente(dipendenteDTO.buildDipendenteModel(true), ruoloDaInserire);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}
	
	@GetMapping("/edit/{idDipendente}")
	public String edit(@PathVariable(required = true) Long idDipendente, Model model) {
		Dipendente dipendenteModel = dipendenteService.caricaSingoloDipendente(idDipendente);
		model.addAttribute("edit_dipendente_attr", DipendenteDTO.buildDipendenteDTOFromModel(dipendenteModel));
		return "dipendente/edit";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("edit_dipendente_attr") DipendenteDTO dipendenteDTO, BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "dipendente/edit";
		}
		dipendenteService.aggiornaDipendenteEUtente(dipendenteDTO.buildDipendenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente";
	}
	
	@GetMapping(value = "/searchDipendentiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchDipendente(@RequestParam String term) {

		List<Dipendente> listaDipendenteByTerm = dipendenteService.cercaByCognomeENomeILike(term);
		return buildJsonResponse(listaDipendenteByTerm);
	}

	private String buildJsonResponse(List<Dipendente> listaDipendenti) {
		JsonArray ja = new JsonArray();

		for (Dipendente dipendenteItem : listaDipendenti) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", dipendenteItem.getId());
			jo.addProperty("label", dipendenteItem.getNome() + " " + dipendenteItem.getCognome());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}
}