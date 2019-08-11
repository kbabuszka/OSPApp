package net.babuszka.osp.utils;

import java.util.Comparator;

import net.babuszka.osp.model.Firefighter;

public class FirefighterUtils {

	public Comparator<Firefighter> compareByLastName = (Firefighter f1, Firefighter f2) -> f1.getLastName().compareTo(f2.getLastName());
}
