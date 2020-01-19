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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.Setting;
import net.babuszka.osp.model.SettingWrapper;
import net.babuszka.osp.service.SettingsService;

@Controller
public class SettingsController {
	
	@Value("${settings.message.save.success}")
	private String messageSettingsSaved;
	@Value("${settings.message.save.error}")
	private String messageSettingsNotSaved;

	private SettingsService settingsService;
	
	@Autowired
	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	@GetMapping(path = "/settings")
	public String initSettingsForm(Model model) {
		model.addAttribute("page_title", "Ustawienia");	
		List<Setting> settings = settingsService.getAllSettings();
		SettingWrapper settingWraper = new SettingWrapper();
		settingWraper.setSettings(settings);
		model.addAttribute("settings", settingWraper);
		return "settings";
	}
	
	@PostMapping(path = "/settings")
	public String processSettingsForm(@ModelAttribute("settings") @Valid SettingWrapper wrapper, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		for(int i=0; i < wrapper.getSettings().size(); i++) {
			if(wrapper.getSettings().get(i).getValue().length() == 0) {
				bindingResult.rejectValue("settings[" + i + "].value", "settings.value.empty");
			}
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("page_title", "Ustawienia");
			model.addAttribute("message", messageSettingsNotSaved);
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("alertClass", "alert-danger");
			return "settings";
		} else {
			List<Setting> settings = wrapper.getSettings();
			for (Setting setting : settings) {
				Integer id = setting.getId();
				String value = setting.getValue();
				Setting settingFromDb = settingsService.getSetting(id);
				settingFromDb.setValue(value);
				settingsService.saveSetting(settingFromDb);
			}
			redirectAttributes.addFlashAttribute("message", messageSettingsSaved);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/settings";
		}
	}
}
