package net.babuszka.osp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Setting;
import net.babuszka.osp.repository.SettingsRepository;

@Service
public class SettingsService {

	private SettingsRepository settingsRepository;

	@Autowired
	public void setSettingsRepository(SettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}
	
	public Setting getSetting(Integer id) {
		return settingsRepository.getOne(id);
	}
	
	public List<Setting> getAllSettings() {
		return settingsRepository.findAll();
	}
	
	public void saveSetting(Setting setting) {
		settingsRepository.save(setting);
	}
	
	public void saveAllSettings(List<Setting> settings) {
		settingsRepository.saveAll(settings);
	}
	
	public Setting getByName(String name) {
		return settingsRepository.findByName(name);
	}
}
