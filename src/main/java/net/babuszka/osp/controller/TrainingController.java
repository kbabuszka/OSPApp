package net.babuszka.osp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.Training;
import net.babuszka.osp.model.TrainingWrapper;
import net.babuszka.osp.service.TrainingService;

@Controller
public class TrainingController {
	
	@Value("${training.message.add.success}")
	private String messageTrainingAdded;
	@Value("${training.message.edit.success}")
	private String messageTrainingEdited;
	@Value("${training.message.delete.success}")
	private String messageTrainingDeleted;
	@Value("${training.message.delete.success.usage}")
	private String messageTrainingDeletedUsage;
	@Value("${training.message.delete.cannotdelete}")
	private String messageTrainingNotDeleted;
	@Value("${training.message.delete.notexist}")
	private String messageTrainingNotExist;

	private TrainingService trainingService;

	@Autowired
	public void setTrainingService(TrainingService trainingService) {
		this.trainingService = trainingService;
	}
	
	// Display the page with trainings
	@RequestMapping(path = "/manage/trainings", method = RequestMethod.GET)
	public String getAllTrainings(Model model) {
		model.addAttribute("page_title", "Zarządzaj rodzajami szkoleń");
		TrainingWrapper trainingWrapper = new TrainingWrapper();
		trainingWrapper.setTrainings(trainingService.getAllTrainings());
		model.addAttribute("wrapper", trainingWrapper);
		model.addAttribute("training", new Training());
		return "manage_trainings";
	}
	
	// Submit new training form
	@RequestMapping(path = "/manage/trainings", method = RequestMethod.POST)
	public String saveTraining(@Valid Training training, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Zarządzaj rodzajami szkoleń");
			TrainingWrapper trainingWrapper = new TrainingWrapper();
			trainingWrapper.setTrainings(trainingService.getAllTrainings());
			model.addAttribute("wrapper", trainingWrapper);
			return "manage_trainings";
		} else {
			trainingService.saveTraining(training);
			redirectAttributes.addFlashAttribute("message", messageTrainingAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/trainings";
		}
	}
	
	// Submit edit trainings form
	@RequestMapping(path = "/manage/trainings/edit", method = RequestMethod.POST)
	public String updateTrainings(@ModelAttribute("wrapper") @Valid TrainingWrapper wrapper, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		for(int i=0; i < wrapper.getTrainings().size(); i++) {
			if(wrapper.getTrainings().get(i).getName().length() < 3) {
				bindingResult.rejectValue("trainings[" + i + "].name", "training.name.size");
			}
			if(wrapper.getTrainings().get(i).getName().length() == 0) {
				bindingResult.rejectValue("trainings[" + i + "].name", "training.name.empty");
			}
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Zarządzaj rodzajami szkoleń");
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("training", new Training());
			return "manage_trainings";
		} else {
			List<Training> trainings = wrapper.getTrainings();
			trainingService.updateTrainings(trainings);
			redirectAttributes.addFlashAttribute("message", messageTrainingEdited);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/trainings";
		}
	}	
	
	//Delete training
	@RequestMapping(path = "/manage/trainings/delete/{id}", method = RequestMethod.GET)
	public String deleteTraining(@PathVariable(value="id") Integer id, RedirectAttributes redirectAttributes) {
		Training training = trainingService.getTraining(id);
		if(training != null) {
			int usage = training.getTrainings().size();
			trainingService.deleteTraining(id);
			if(trainingService.getTraining(id) == null) {
				redirectAttributes.addFlashAttribute("message", messageTrainingDeleted);
				if(usage > 0)
					redirectAttributes.addFlashAttribute("message2", messageTrainingDeletedUsage + usage);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", messageTrainingNotDeleted);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
			return "redirect:/manage/trainings";
		} else {
			redirectAttributes.addFlashAttribute("message", messageTrainingNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/trainings";
		}
	}
	
}
