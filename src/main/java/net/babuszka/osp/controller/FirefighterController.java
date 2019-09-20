package net.babuszka.osp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.DeletedFirefighter;
import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.FirefighterTraining;
import net.babuszka.osp.model.FirefighterTrainingWrapper;
import net.babuszka.osp.model.User;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.FirefighterTrainingService;
import net.babuszka.osp.service.FirefighterTypeService;
import net.babuszka.osp.service.TrainingService;
import net.babuszka.osp.service.UserService;
import net.babuszka.osp.utils.FirefighterUtils;

@Controller
public class FirefighterController {
	
	@Value("${firefighter.message.add.success}")
	private String messageFirefighterAdded;
	
	@Value("${firefighter.message.delete.success}")
	private String messageFirefighterDeleted;
	
	@Value("${firefighter.message.delete.error}")
	private String messageFirefighterNotDeleted;
	
	@Value("${firefighter.message.edit.success}")
	private String messageFirefighterEdited;
	
	@Value("${firefighter.message.deletetraining.success}")
	private String messageFirefighterTrainingDeleted;
	
	@Value("${firefighter.message.deletetraining.error}")
	private String messageFirefighterTrainingNotDeleted;
	
	@Value("${firefighter.message.deletetraining.notexists}")
	private String messageFirefighterTrainingNotExists;
	
	@Value("${firefighter.message.firefighter.notexist}")
	private String messageFirefighterNotExist;
	
	private FirefighterService firefighterService;
	private FirefighterTypeService firefighterTypeService;
	private FirefighterTrainingService firefighterTrainingService;
	private TrainingService trainingService;
	private UserService userService;

	// Allow Spring to set empty values as null instead of empty string.
	@InitBinder
	public void allowEmptyDateBinding( WebDataBinder binder )
	{
	    binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
	}
	
	@Autowired
	public void setFirefighterService(FirefighterService firefighterService) {
		this.firefighterService = firefighterService;
	}

	@Autowired
	public void setFirefighterTypeService(FirefighterTypeService firefighterTypeService) {
		this.firefighterTypeService = firefighterTypeService;
	}
	
	@Autowired
	public void setFirefighterTrainingService(FirefighterTrainingService firefighterTrainingService) {
		this.firefighterTrainingService = firefighterTrainingService;
	}

	@Autowired
	public void setTrainingService(TrainingService trainingService) {
		this.trainingService = trainingService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// Display single firefighter's details
	@RequestMapping(path = "/firefighters/{id:\\d+}", method = RequestMethod.GET)
	public String getFirefighter(Model model, @PathVariable(value = "id") Integer id) {
		model.addAttribute("page_title", "Szczegóły strażaka");
		if(firefighterService.getFirefighter(id) != null) {
			model.addAttribute("firefighter", firefighterService.getFirefighter(id));
			model.addAttribute("firefighter_types", firefighterTypeService.getAllFirefighterTypes());
			FirefighterTrainingWrapper trainingWrapper = new FirefighterTrainingWrapper();
			trainingWrapper.setFirefighterTrainings(firefighterService.getFirefighter(id).getTrainings());
			model.addAttribute("trainings", trainingWrapper);
			User user = userService.findUserByFirefighterId(id);
			model.addAttribute("user", user);
			return "firefighter_details";
		} else {
			return "redirect:/firefighters";
		}
	}

	// Display all firefighters
	@RequestMapping(path = "/firefighters", method = RequestMethod.GET)
	public String getAllFirefighters(Model model) {
		model.addAttribute("page_title", "Lista strażaków");
		model.addAttribute("firefighters", firefighterService.getAllFirefighters());
		return "firefighters_list";
	}

	// Display new firefighter form
	@RequestMapping(path = "/firefighters/add", method = RequestMethod.GET)
	public String addFirefighter(Model model) {
		model.addAttribute("page_title", "Dodaj strażaka");
		model.addAttribute("firefighter", new Firefighter());
		model.addAttribute("firefighter_types", firefighterTypeService.getAllFirefighterTypes());
		return "firefighter_add";
	}
	
	// Submit new firefighter form
	@RequestMapping(path = "/firefighters/add", method = RequestMethod.POST)
	public String saveFirefighter(@Valid Firefighter firefighter, BindingResult bindingResult, Model model,
								RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Dodaj strażaka");
			model.addAttribute("firefighter_types", firefighterTypeService.getAllFirefighterTypes());
			return "firefighter_add";
		} else {
			firefighterService.saveFirefighter(firefighter);
			redirectAttributes.addFlashAttribute("message", messageFirefighterAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/firefighters";
		}
	}

	// Display edit firefighter form
	@RequestMapping(path = "/firefighters/edit/{id}", method = RequestMethod.GET)
	public String editFirefighter(Model model, @PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes) {	
		model.addAttribute("page_title", "Edytuj strażaka");
		if(firefighterService.getFirefighter(id) != null) {
			model.addAttribute("firefighter", firefighterService.getFirefighter(id));
			model.addAttribute("firefighter_types", firefighterTypeService.getAllFirefighterTypes());	
			model.addAttribute("training_types", trainingService.getAllTrainings());
			return "firefighter_edit";
		} else {
			redirectAttributes.addFlashAttribute("message", messageFirefighterNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/firefighters";
		}
	}
	
	// Submit edit firefighter form
	@RequestMapping(path = "/firefighters/edit/{id}", method = RequestMethod.POST)
	public String updateFirefighter(@ModelAttribute("firefighter") @Valid Firefighter firefighter, BindingResult bindingResult, 
									@PathVariable(value = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		
		if(firefighter.getTrainings() != null) {
			for(int i=0; i < firefighter.getTrainings().size(); i++) {
				if(firefighter.getTrainings().get(i).getTraining() == null) 
						bindingResult.rejectValue("trainings["+ i +"].training", "firefighter.training.type.empty");
			}
		}
		
		if (firefighterService.getFirefighter(firefighter.getPesel(), id)) {
			bindingResult.rejectValue("pesel", "firefighter.pesel.duplicate");
		}
		
		if(bindingResult.hasErrors()) {
			if(firefighter.getTrainings() == null)
				firefighter.setTrainings(new ArrayList<FirefighterTraining>());
			model.addAttribute("page_title", "Edytuj strażaka");
			model.addAttribute("firefighter", firefighter);
			model.addAttribute("firefighter_types", firefighterTypeService.getAllFirefighterTypes());
			model.addAttribute("training_types", trainingService.getAllTrainings());
			return "firefighter_edit";
			
		} else {
			firefighterService.saveFirefighter(firefighter);
			if(firefighter.getTrainings() != null) {
				firefighterTrainingService.saveAllFirefighterTrainings(firefighter.getTrainings());
			}
			redirectAttributes.addFlashAttribute("message", messageFirefighterEdited);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/firefighters/edit/" + id;
		}
		
	}

	// Delete a firefighter
	@RequestMapping(path = "/firefighters/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes) {
		Firefighter firefighter = firefighterService.getFirefighter(id);
		if(firefighter != null) {
			if(firefighter.getTrainings() != null) {
				for(FirefighterTraining training : firefighter.getTrainings()) {
					firefighterTrainingService.deleteFirefighterTraining(training.getId());
				}
			}
			firefighterService.deleteFirefighter(id);
			if(firefighterService.getFirefighter(id) == null && firefighterService.getDeletedFirefighter(id) != null) {
				redirectAttributes.addFlashAttribute("message", messageFirefighterDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
				return "redirect:/firefighters";
			} else {
				redirectAttributes.addFlashAttribute("message", messageFirefighterNotDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				return "redirect:/firefighters";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", messageFirefighterNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/firefighters";
		}
	}
	
	// Delete firefighter's training
	@RequestMapping(path = "/firefighters/edit/{firefighterId}/deletetraining/{id}", method = RequestMethod.GET)
	public String deleteTraining(@PathVariable(value = "id") Integer id, @PathVariable(value = "firefighterId") Integer firefighterId,
								RedirectAttributes redirectAttributes) {
		FirefighterTraining training = firefighterTrainingService.getFirefighterTraining(id);
		if(training != null) {
			if(training.getFirefighterId().getId().equals(firefighterId)) {
				redirectAttributes.addFlashAttribute("message", messageFirefighterTrainingDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
				firefighterTrainingService.deleteFirefighterTraining(id);
				return "redirect:/firefighters/edit/" + firefighterId;
			} else {
				redirectAttributes.addFlashAttribute("message", messageFirefighterTrainingNotExists);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				return "redirect:/firefighters/edit/" + firefighterId;
			}
		} else {
			redirectAttributes.addFlashAttribute("message", messageFirefighterTrainingNotDeleted);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/firefighters/edit/" + firefighterId;
		}
	}
	
	// Display JOT Matrix
	@RequestMapping(path = "/firefighters/jot", method = RequestMethod.GET)
	public String getJotFirefighters(Model model) {
		model.addAttribute("page_title", "Tabela JOT");
		List<Firefighter> firefighters = firefighterService.getJotFirefighters();
		FirefighterUtils utils = new FirefighterUtils();
		Collections.sort(firefighters, utils.compareByLastName);
		model.addAttribute("firefighters", firefighters);
		model.addAttribute("training_types", trainingService.getAllTrainings());
		return "firefighters_jot";
	}
	
	// Display JOT Matrix
	@RequestMapping(path = "/firefighters/deleted", method = RequestMethod.GET)
	public String getDeletedFirefighters(Model model) {
		model.addAttribute("page_title", "Lista usuniętych strażaków");
		List<DeletedFirefighter> firefighters = firefighterService.getAllDeletedFirefighters();
//		FirefighterUtils utils = new FirefighterUtils();
//		Collections.sort(firefighters, utils.compareByLastName);
		model.addAttribute("firefighters", firefighters);
		return "firefighters_deleted";
	}

}
