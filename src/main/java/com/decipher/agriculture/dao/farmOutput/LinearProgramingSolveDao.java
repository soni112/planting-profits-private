/**
 * 
 */
package com.decipher.agriculture.dao.farmOutput;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.javailp.Result;

import com.decipher.agriculture.bean.CropBeanForOutput;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.FieldInfoView;

/**
 * @author Harshit Gupta
 *
 */
public interface LinearProgramingSolveDao {

	Result getLinearProgramingResultForAcerage(
			List<CropBeanForOutput> cropBeanForOutputList, String land,
			List<CropResourceUsageView> resourceUsageViews,
			Set<CropsGroup> cropsGroups);

	List<String[]> generateCombination(
			List<CropBeanForOutput> cropBeanForOutputList,
			Set<CropsGroup> cropsGroups, List<FieldInfoView> fieldInfoViews);

	Map<String[], Result> getLinearProgramingResultForField(
			List<CropBeanForOutput> cropBeanForOutputList,
			List<CropResourceUsageView> resourceUsageViews,
			Set<CropsGroup> cropsGroups, List<String[]> combinationSetList,
			List<FieldInfoView> fieldInfoViews);

	Map<String, Object> getBestResultFromLinearProgramingForField(
			List<CropBeanForOutput> cropBeanForOutputList,
			List<CropResourceUsageView> resourceUsageViews,
			Set<CropsGroup> cropsGroups, List<FieldInfoView> fieldInfoViews,
			List<String[]> array);

}