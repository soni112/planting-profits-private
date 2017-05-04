package com.decipher.agriculture.customclasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.decipher.util.PlantingProfitLogger;

public class CustomHashSet extends HashSet<String[]> implements Set<String[]> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813730033074426065L;

	private transient HashMap<String[], Object> map;

	private static final Object PRESENT = new Object();

	public CustomHashSet() {
		map = new HashMap<>();
	}

	@Override
	public boolean add(String[] e) {
		PlantingProfitLogger.info(Arrays.toString(e));
		if (!checkifArrayAlreadyExist(e)) {
			PlantingProfitLogger.info("Adding to map");
			map.put(e, PRESENT);
			return true;
		}
		return false;
	}

	private boolean checkifArrayAlreadyExist(String[] e) {
		boolean innerflag = false;
		boolean outerflag = false;
		for (Entry<String[], Object> entry : map.entrySet()) {
			if (entry.getKey().length == e.length) {
				outerflag = true;
				for (String arr : e) {
					innerflag = false;
					for (String inner : entry.getKey()) {
						if (arr.equals(inner)) {
							innerflag = true;
							break;
						}
					}
					if (!innerflag) {
						outerflag = false;
						break;
					}
				}
				if (outerflag) {
					return true;
				}
			}
		}
		return false;
	}
}
