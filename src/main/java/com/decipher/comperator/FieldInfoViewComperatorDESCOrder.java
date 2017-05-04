package com.decipher.comperator;

import java.util.Comparator;

import com.decipher.util.AgricultureStandardUtils;
import com.decipher.view.form.farmDetails.FieldInfoView;

public class FieldInfoViewComperatorDESCOrder implements
		Comparator<FieldInfoView> {

	@Override
	public int compare(FieldInfoView o1, FieldInfoView o2) {
		if (Long.parseLong(AgricultureStandardUtils.removeAllCommas(o1
				.getFieldSize())) == Long.parseLong(AgricultureStandardUtils
				.removeAllCommas(o2.getFieldSize())))
			return 0;
		else if (Long.parseLong(AgricultureStandardUtils.removeAllCommas(o1
				.getFieldSize())) > Long.parseLong(AgricultureStandardUtils
				.removeAllCommas(o2.getFieldSize())))
			return 1;
		else
			return -1;
	}
}
