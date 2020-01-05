package net.babuszka.osp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.FirefighterType;
import net.babuszka.osp.model.FirefighterTypeWrapper;
import net.babuszka.osp.service.FirefighterTypeService;

@Controller
public class FirefighterTypeController {
	
	@Value("${firefightertype.message.add.success}")
	private String messageFirefighterTypeAdded;
	@Value("${firefightertype.message.edit.success}")
	private String messageFirefighterTypeEdited;
	@Value("${firefightertype.message.delete.success}")
	private String messageFirefighterTypeDeleted;
	@Value("${firefightertype.message.delete.cannotdelete}")
	private String messageFirefighterTypeNotDeleted;
	@Value("${firefightertype.message.delete.notexist}")
	private String messageFirefighterTypeNotExist;

	@Autowired
	private FirefighterTypeService firefighterTypeService;

	public void setFirefighterTypeRepository(FirefighterTypeService firefighterTypeService) {
		this.firefighterTypeService = firefighterTypeService;
	}
	
	// Display page with firefighter types
	@GetMapping(path = "/manage/firefighter-types")
	public String getAllFirefighterTypes(Model model) {
		model.addAttribute("page_title", "Zarządzaj rodzajami strażaków");
		FirefighterTypeWrapper wrapper = new FirefighterTypeWrapper();
		wrapper.setTypes(firefighterTypeService.getAllFirefighterTypes());
		model.addAttribute("wrapper", wrapper);
		model.addAttribute("firefighter_type", new FirefighterType());
		return "manage_firefighter_types";
	}
	
	// Submit new firefighter type form
	@PostMapping(path = "/manage/firefighter-types")
	public String addFirefighterType(@ModelAttribute("firefighter_type") @Valid FirefighterType firefighterType, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Zarządzaj rodzajami strażaków");
			FirefighterTypeWrapper wrapper = new FirefighterTypeWrapper();
			wrapper.setTypes(firefighterTypeService.getAllFirefighterTypes());
			model.addAttribute("wrapper", wrapper);
			return "manage_firefighter_types";
		} else {
			firefighterTypeService.saveFirefighterType(firefighterType);
			redirectAttributes.addFlashAttribute("message", messageFirefighterTypeAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/firefighter-types";
		}
	}
	
	//Submit edit form of firefighter types
	@PostMapping(path = "/manage/firefighter-types/edit")
	public String updateFirefighterTypes(@ModelAttribute("wrapper") @Valid FirefighterTypeWrapper wrapper, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		for(int i=0; i < wrapper.getTypes().size(); i++) {
			if(wrapper.getTypes().get(i).getName().length() < 3) {
				bindingResult.rejectValue("types[" + i + "].name", "firefightertype.name.size");
			}
			if(wrapper.getTypes().get(i).getName().length() == 0) {
				bindingResult.rejectValue("types[" + i + "].name", "firefightertype.name.empty");
			}
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Zarządzaj rodzajami strażaków");
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("firefighter_type", new FirefighterType());
			return "manage_firefighter_types";
		} else {	
			List<FirefighterType> types = wrapper.getTypes();
			firefighterTypeService.updateFirefighterTypes(types);
			redirectAttributes.addFlashAttribute("message", messageFirefighterTypeEdited);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/firefighter-types";
		}
	}
	
	//Delete firefighter type
	@GetMapping(path = "/manage/firefighter-types/delete/{id:\\d+}")
	public String deleteFirefighterType(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes) {
		if(firefighterTypeService.getFirefighterType(id) != null) {
			firefighterTypeService.deleteFirefighterType(id);
			if(firefighterTypeService.getFirefighterType(id) == null) {
				redirectAttributes.addFlashAttribute("message", messageFirefighterTypeDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", messageFirefighterTypeNotDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
			return "redirect:/manage/firefighter-types";
		} else {
			redirectAttributes.addFlashAttribute("message", messageFirefighterTypeNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/firefighter-types";
		}

	}
}
