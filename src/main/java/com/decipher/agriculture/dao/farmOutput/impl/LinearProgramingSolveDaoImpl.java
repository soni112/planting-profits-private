package com.decipher.agriculture.dao.farmOutput.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.decipher.agriculture.bean.LinearProgrammingResultBean;
import com.decipher.agriculture.dao.farmOutput.LinearProgramingSolveDao;
import net.sf.javailp.Linear;
import net.sf.javailp.Operator;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.bean.CropBeanForOutput;
import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FieldInfoView;

import javax.servlet.http.HttpSession;

/**
 * @author Harshit Gupta
 */

@Repository
@Transactional
public class LinearProgramingSolveDaoImpl implements LinearProgramingSolveDao {

    @Autowired
    private HttpSession httpSession;
    private Set<String> minRequiredCrops = null;
    private static final Double zeroDouble = 0.0;
    private Set<String> minRequiredCropsForGroups = null;

//    public static void main(String[] args) {
//        System.out.println(GLPK.glp_version());
//    }

    @Override
    public Result getLinearProgramingResultForAcerage(List<CropBeanForOutput> cropBeanForOutputList, String land, List<CropResourceUsageView> resourceUsageViews, Set<CropsGroup> cropsGroups) {
        /**
         * @Changed - Abhishek
         * @Date - 27-11-2015
         * */
        SolverFactory factory = new SolverFactoryLpSolve();   // use lp_solve
//		SolverFactory factory = new SolverFactoryGLPK();
//		SolverFactorproblemyCPLEX factory = new SolverFactoryCPLEX();

        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, 1000);
        Problem problem = new Problem();

		/* Profit by each crop */
        Linear linear = new Linear();

        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

            /**
             * @author Raja
             * @since 14 Dec, 2015
             * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
             */

            if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                continue;
            }

			/* linear for profit by each crop */

            linear.add(beanForOutput.getProfit(), beanForOutput.getCropType().getCropName());
            if (beanForOutput.getFirmAcres() > zeroDouble) {
                linear.add(beanForOutput.getForwardprofit(), beanForOutput.getCropType().getCropName() + " (Contract)");
                PlantingProfitLogger.info(beanForOutput.getForwardprofit() + "----------" + beanForOutput.getCropType().getCropName() + " (Contract)");
            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                linear.add(beanForOutput.getForwardprofit(), beanForOutput.getCropType().getCropName() + " (Proposed)");
                PlantingProfitLogger.info(beanForOutput.getForwardprofit() + "----------" + beanForOutput.getCropType().getCropName() + " (Proposed)");
            }
            PlantingProfitLogger.info(beanForOutput.getProfit() + "----------" + beanForOutput.getCropType().getCropName());
        }
        problem.setObjective(linear, OptType.MAX);


        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

            /**
             * @author Raja
             * @since 14 Dec, 2015
             * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
             */

            if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                continue;
            }

			/* linear for crop info */

            linear = new Linear();
            linear.add(1, beanForOutput.getCropType().getCropName());
            problem.add(linear, Operator.GE, zeroDouble);
            if (beanForOutput.getFirmAcres() > zeroDouble) {
                linear = new Linear();
                linear.add(1, beanForOutput.getCropType().getCropName() + " (Contract)");
                problem.add(linear, Operator.GE, zeroDouble);
            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                linear = new Linear();
                linear.add(1, beanForOutput.getCropType().getCropName() + " (Proposed)");
                problem.add(linear, Operator.GE, zeroDouble);
            }
        }

        for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
            linear = new Linear();
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                /**
                 * @author Raja
                 * @since 14 Dec, 2015
                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                 */

                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                    continue;
                }


                for (Entry<String, Double> entry : beanForOutput.getResourceList().entrySet()) {
                    if (cropResourceUsageView.isActive()) {
                        if (entry.getKey().equals(cropResourceUsageView.getCropResourceUse())) {

                            /* linear for resource usage */

                            linear.add(entry.getValue(), beanForOutput.getCropType().getCropName());
                            if (beanForOutput.getFirmAcres() > zeroDouble) {
                                linear.add(entry.getValue(), beanForOutput.getCropType().getCropName() + " (Contract)");
                                PlantingProfitLogger.info(entry.getValue() + "----------" + beanForOutput.getCropType().getCropName() + " (Contract)");
                            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                linear.add(entry.getValue(), beanForOutput.getCropType().getCropName() + " (Proposed)");
                                PlantingProfitLogger.info(entry.getValue() + "----------" + beanForOutput.getCropType().getCropName() + " (Proposed)");
                            }
                            PlantingProfitLogger.info(entry.getValue() + "----------" + beanForOutput.getCropType().getCropName());
                        }
                    }
                }

            }
            problem.add(cropResourceUsageView.getCropResourceUse(), linear, Operator.LE, Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()));
            PlantingProfitLogger.info(Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()));
        }

		/* Minimum required crop */
        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

            /**
             * @author Raja
             * @since 14 Dec, 2015
             * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
             */

            if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                continue;
            }

            if (beanForOutput.getMinAcre() > zeroDouble) {
                linear = new Linear();
                linear.add(beanForOutput.getAcres(), beanForOutput.getCropType().getCropName());
                problem.add("Minimum " + beanForOutput.getCropType().getCropName(), linear, Operator.GE, beanForOutput.getMinAcre());
                PlantingProfitLogger.info(beanForOutput.getMinAcre());
            }
            if (beanForOutput.getMaxAcre() > zeroDouble) {
                linear = new Linear();
                linear.add(beanForOutput.getAcres(), beanForOutput.getCropType().getCropName());
                problem.add("Maximum " + beanForOutput.getCropType().getCropName(), linear, Operator.LE, beanForOutput.getMaxAcre());
                PlantingProfitLogger.info(beanForOutput.getMaxAcre());
            }
            /*else if (beanForOutput.getMinAcre()>0 || beanForOutput.getMinAcre()==0){
                if (beanForOutput.getMaxAcre()==0){
                    linear = new Linear();
                    linear.add(beanForOutput.getAcres(), beanForOutput.getCropType().getCropName());
                    problem.add("Maximum " + beanForOutput.getCropType().getCropName(), linear, Operator.LE, beanForOutput.getMaxAcre());
                    PlantingProfitLogger.info(beanForOutput.getMaxAcre());
                }
            }*/
            if (beanForOutput.getFirmAcres() > zeroDouble) {
                linear = new Linear();
                linear.add(beanForOutput.getAcres(), beanForOutput.getCropType().getCropName() + " (Contract)");
                problem.add("Minimum " + beanForOutput.getCropType().getCropName() + " (Contract)", linear, Operator.EQ, beanForOutput.getFirmAcres());
                PlantingProfitLogger.info(beanForOutput.getFirmAcres());
            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                linear = new Linear();
                linear.add(beanForOutput.getAcres(), beanForOutput.getCropType().getCropName() + " (Proposed)");
                problem.add("Minimum " + beanForOutput.getCropType().getCropName() + " (Proposed)", linear, Operator.LE, beanForOutput.getProposedAcres());
                PlantingProfitLogger.info(beanForOutput.getProposedAcres());
            }
        }

		/* Minimum required crop group */

        if (cropsGroups != null) {
            for (CropsGroup cropsGroup : cropsGroups) {
                if (!cropsGroup.getMaximumAcres().equals("") && !cropsGroup.getMaximumAcres().equals("0")) {
                    PlantingProfitLogger.info("Adding Groups Maximum");
                    linear = new Linear();
                    for (CropType cropType : cropsGroup.getCropTypes()) {
                        linear.add(1.0, cropType.getCropName());
                        if (cropType.getCropForwardSales().getFirmchecked().equals("true")) {
                            linear.add(1.0, cropType.getCropName() + " (Contract)");
                        } else if (cropType.getCropForwardSales().getProposedchecked()) {
                            linear.add(1.0, cropType.getCropName() + " (Proposed)");
                        }
                    }
                    problem.add("Maximum " + cropsGroup.getCropsGroupName(), linear, Operator.LE, Double.parseDouble(cropsGroup.getMaximumAcres()));
                }
                if (!cropsGroup.getMinimumAcres().equals("") && !cropsGroup.getMinimumAcres().equals("0")) {
                    PlantingProfitLogger.info("Adding Groups Minimum");
                    linear = new Linear();
                    for (CropType cropType : cropsGroup.getCropTypes()) {
                        linear.add(1.0, cropType.getCropName());
                        if (cropType.getCropForwardSales().getFirmchecked().equals("true")) {
                            linear.add(1.0, cropType.getCropName() + " (Contract)");
                        } else if (cropType.getCropForwardSales().getProposedchecked()) {
                            linear.add(1.0, cropType.getCropName() + " (Proposed)");
                        }
                    }
                    problem.add("Minimum " + cropsGroup.getCropsGroupName(), linear, Operator.GE, Double.parseDouble(cropsGroup.getMinimumAcres()));
                }
            }
        }

        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

            /**
             * @author Raja
             * @since 14 Dec, 2015
             * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
             */

            if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                continue;
            }

            problem.setVarType(beanForOutput.getCropType().getCropName(), Double.class);
            if (beanForOutput.getFirmAcres() > zeroDouble || beanForOutput.getProposedAcres() > zeroDouble) {
                problem.setVarType(beanForOutput.getCropType().getCropName() + ((beanForOutput.getFirmAcres() > zeroDouble) ? " (Contract)" : " (Proposed)"), Double.class);
            }
        }
        Solver solver = factory.get();
        Result result = solver.solve(problem);
        PlantingProfitLogger.info(result);
        /**
         * For setting solver run count
         */
        setSolverRunCount();
        return result;
    }

    @Override
    public List<String[]> generateCombination(List<CropBeanForOutput> cropBeanForOutputList, Set<CropsGroup> cropsGroups, List<FieldInfoView> fieldInfoViews) {
        List<String[]> combinationsArray = null;
        int arrayLength = 0;
        for (FieldInfoView fieldInfoView : fieldInfoViews) {
            if (fieldInfoView.getFallow().equals("false")) {
                arrayLength++;
            }
        }
        String[][] arrayForCombinations = new String[arrayLength][];
        int loop = 0;
        int size = 0;
        List<CropType> cropTypes = new ArrayList<CropType>();
        for (FieldInfoView fieldInfoView : fieldInfoViews) {
            if (fieldInfoView.getFallow().equals("false")) {
                size = 0;
                for (CropFieldChocies chocies : fieldInfoView.getChocies()) {
                    if (chocies.getCropFieldChoice().equals("true")) {
                        size++;
                    }
                }
                arrayForCombinations[loop++] = new String[size];
            }
        }
        loop = 0;
        for (FieldInfoView fieldInfoView : fieldInfoViews) {
            if (fieldInfoView.getFallow().equals("false")) {
                size = 0;
                for (CropFieldChocies chocies : fieldInfoView.getChocies()) {
                    if (chocies.getCropFieldChoice().equals("true")) {
                        arrayForCombinations[loop][size++] = fieldInfoView.getFieldName() + "###" + chocies.getCropType().getCropName();
                        cropTypes.add(chocies.getCropType());
                    }
                }
                loop++;
            }
        }
        setMinRequiredCrops(cropBeanForOutputList);
        setMinRequiredCropsForGroups(cropsGroups);
        Set<String> minLengthSet = new HashSet<String>();
        minLengthSet.addAll(getMinRequiredCrops());
        minLengthSet.addAll(getMinRequiredCropsForGroups());
        try {
            PlantingProfitLogger.info("Started generating combinations " + new Date());
            combinationsArray = generateCombinationsForAllLength(arrayForCombinations, minLengthSet.size());
        } catch (Throwable e) {
            PlantingProfitLogger.error(e);
            combinationsArray = new ArrayList<String[]>();
//			errorFlag = true;
        }
        PlantingProfitLogger.info("Total generated combinations = " + combinationsArray.size() + " Start time = " + new Date());
        return combinationsArray;
    }


    @Override
    public Map<String, Object> getBestResultFromLinearProgramingForField(
            List<CropBeanForOutput> cropBeanForOutputList,
            List<CropResourceUsageView> resourceUsageViews,
            Set<CropsGroup> cropsGroups, List<FieldInfoView> fieldInfoViews, List<String[]> array) {
//		boolean errorFlag = false;
//		String[] bigBestCase = null;
//		Long bigBestCaseProfit = 0l;
//		Result bigBestResult = null;
        /**
         * @chnaged - Abhishek
         * @date - 27-01-2016
         * @desc - for checking maximum profit digits
         * @logic - getting big and small count and comparing both for higher count, greater count result considered real profit
         */
//		String[] smallBestCase = null;
//		Long smallBestCaseProfit = 0l;
//		Result smallBestResult = null;
//		int bigValueCount = 0;
//		int smallValueCount = 0;
//		Result bigResult = null;
//		Result smallResult = null;
//		int bestStrategyOnBasisOfResources;
//		int totalCombination = 0;
//		int satisfiedCombination = 1;
//		for (List<String> combinationSet : array) {
//			PlantingProfitLogger.info(combinationSet.toString());
//		}

        /**
         * @changed - Abhishek
         * @date - 19-10-2016
         * @desc - implemented threads for processing of calculation thereby reducing the time to process
         */

        final List<List<String[]>> combinationsListForCalculationList = processCombinationsForCalculation(array);

        Set<Object> resultList = new HashSet<>();

        final int CORE_POOL_SIZE = 100;
        final int MAX_POOL_SIZE = 1000;
        final long KEEP_ALIVE_TIME = 5000;

        ExecutorService executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//		ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(combinationsListForCalculationList.size());
        long start = System.currentTimeMillis();
        PlantingProfitLogger.warn("Task started for farm id : " + resourceUsageViews.get(0).getCropResourceUsage().getFarmInfo().getFarm().getFarmId() + " with " + combinationsListForCalculationList.size() + " threads : " + new Date());

        Thread thread;
        TempListHolder tempListHolder;

//		ThreadGroup threadGroup = new ThreadGroup("Output Calculation ThreadGroup");

        for (List<String[]> combinationsListForCalculation : combinationsListForCalculationList) {
            tempListHolder = new TempListHolder();

            LinearProgrammingResultBean linearProgrammingResultBean = new LinearProgrammingResultBean();

            linearProgrammingResultBean.setCombination(combinationsListForCalculation);
            linearProgrammingResultBean.setCropBeanForOutputList(cropBeanForOutputList);
            linearProgrammingResultBean.setCropsGroups(cropsGroups);
            linearProgrammingResultBean.setFieldInfoViews(fieldInfoViews);
            linearProgrammingResultBean.setResourceUsageViews(resourceUsageViews);


            tempListHolder.setLinearProgrammingResultBean(linearProgrammingResultBean);
            tempListHolder.setResultSet(resultList);


//			thread = new Thread(threadGroup, tempListHolder);
//			thread.start();
            thread = new Thread(tempListHolder);
            executorService.submit(thread);
        }
        executorService.shutdown();
        boolean finished = false;
        try {
//            finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
//            finished = executorService.awaitTermination(2, TimeUnit.MINUTES);
//            finished = executorService.awaitTermination(3, TimeUnit.MINUTES);
            finished = executorService.awaitTermination(4, TimeUnit.MINUTES);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }


        Map<String, Object> map = new HashedMap();
        if (finished) {
            map = processSetForBestCase(map, resultList);
            PlantingProfitLogger.warn("Task completed : " + new Date());
        } else {
            PlantingProfitLogger.info("Task not completed : " + new Date());
            executorService.shutdownNow();
        }
        long stop = System.currentTimeMillis();
        PlantingProfitLogger.info("Time taken to process : " + (stop - start));

//		if(bigValueCount > smallValueCount){
//			bestCase = bigBestCase;
//			bestResult = bigBestResult;
//		} else if(bigValueCount < smallValueCount){
//			bestCase = smallBestCase;
//			bestResult = smallBestResult;
//		}


        PlantingProfitLogger.info("Total generated combinations = " + array.size() + " End time = " + new Date());
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("Best_Case", bestCase);
//		map.put("Best_Result", bestResult);
        return map;
    }

    /**
     * @changed - Abhishek
     * @date - 19-10-2016
     * @desc - implemented threads for processing of calculation thereby reducing the time to process
     */
    private List<List<String[]>> processCombinationsForCalculation(List<String[]> combinations) {

        Integer combinationLength = combinations.size();
        List<List<String[]>> setsLists = new ArrayList<>();

        String comLenStr = combinationLength.toString();

        if (comLenStr.length() == 4) {
            Double nearestThousand = Math.ceil((combinationLength + 999) / 1000) * 1000;
            Double tenPercent = (nearestThousand * 10) / 100;
            Double sets = nearestThousand / tenPercent;

            getSubList(sets.intValue(), combinationLength, tenPercent.intValue(), combinations, setsLists);


        } else if (comLenStr.length() >= 5) {

            Double nearestThousand = Math.ceil((combinationLength + 999) / 1000) * 1000;

            Double onePercent = (nearestThousand * 1) / 100;
            Double sets = nearestThousand / onePercent;

            getSubList(sets.intValue(), combinationLength, onePercent.intValue(), combinations, setsLists);

        } else {
            setsLists.add(combinations);
        }

        return setsLists;
    }

    /**
     * @changed - Abhishek
     * @date - 19-10-2016
     * @desc - implemented threads for processing of calculation thereby reducing the time to process
     */
    private void getSubList(int noOfSets, int combinationLength, int percentage,
                            List<String[]> array,
                            List<List<String[]>> collectionToHold) {
        boolean flag = false;
        for (int j = 1; j <= noOfSets; j++) {
            int limit, startLimit;
            if (j == noOfSets) {
                limit = combinationLength;
            } else {
                limit = j * percentage;
            }

            if ((combinationLength - limit) < 0) {
                limit = combinationLength;
                flag = true;
            }

            if (j == 1) {
                startLimit = 0;
            } else {
                startLimit = ((j * percentage) - percentage) + 1;
            }
            collectionToHold.add(array.subList(startLimit, limit));

            if (flag) {
                break;
            }
        }
    }

    /**
     * @changed - Abhishek
     * @date - 19-10-2016
     * @desc - implemented threads for processing of calculation thereby reducing the time to process
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> processSetForBestCase(Map<String, Object> map, Set<Object> resultList) {
        String[] bestCase = null;
        Result bestResult = null;
        Long bigHighest = Long.MIN_VALUE, smallHighest = Long.MIN_VALUE;
        int smallCount = 0, bigCount = 0;

        String[] bigBestCase = null, smallBestCase = null;
        Result bigBestResult = null, smallBestResult = null;

        for (Object o : resultList) {

            Map<String, Object> result = (Map<String, Object>) o;

            Long bigBestCaseProfit = (Long) result.get("bigBestCaseProfit");
            Long smallBestCaseProfit = (Long) result.get("smallBestCaseProfit");

            if (bigBestCaseProfit.intValue() > bigHighest.intValue()) {
                bigCount++;
                bigHighest = bigBestCaseProfit;
                bigBestCase = (String[]) result.get("bigBestCase");
                bigBestResult = (Result) result.get("bigBestResult");
            } else if (smallBestCaseProfit.intValue() > smallHighest.intValue()) {
                smallCount++;
                smallHighest = smallBestCaseProfit;
                smallBestCase = (String[]) result.get("smallBestCase");
                smallBestResult = (Result) result.get("smallBestResult");
            }

        }
        if (bigCount > smallCount) {
            bestCase = bigBestCase;
            bestResult = bigBestResult;
        } else if (bigCount < smallCount) {
            bestCase = smallBestCase;
            bestResult = smallBestResult;
        }

        map.put("Best_Case", bestCase);
        map.put("Best_Result", bestResult);

        return map;

    }

    @Override
    public Map<String[], Result> getLinearProgramingResultForField(List<CropBeanForOutput> cropBeanForOutputList, List<CropResourceUsageView> resourceUsageViews,
                                                                   Set<CropsGroup> cropsGroups, List<String[]> combinationSetList, List<FieldInfoView> fieldInfoViews) {
        long start = System.currentTimeMillis();
        Map<String[], Result> resultMap = new HashMap<>();
        SolverFactory factory = new SolverFactoryLpSolve();
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, 1000);
        Solver solver = factory.get();

        for (String[] combinationSet : combinationSetList) {
            // use lp_solve
//		SolverFactory factory = new SolverFactoryGLPK();
//		SolverFactory factory = new SolverFactoryCPLEX();

            Problem problem = new Problem();
            Linear linear = null;
            CropType cropType = null;
            CropTypeView cropTypeView = null;

		/* Profit by crop */
            linear = new Linear();
            List<String> combinationList = Arrays.asList(combinationSet);
            for (String combination : combinationSet) {
                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                    /**
                     * @author Raja
                     * @since 14 Dec, 2015
                     * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                     */

                    if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                        continue;
                    }


                    if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                        cropType = beanForOutput.getCropType();
                        cropTypeView = new CropTypeView(cropType);
                        if (cropType.getCropYieldFieldVariances() != null && cropType.getCropYieldFieldVariances().getFieldInfo().getFieldName().equals(combination.split("###")[0])) {
//						PlantingProfitLogger.info("In field Difference");
//						String yield = cropTypeView.getExpCropYieldFieldStr().equals("0") || cropTypeView.getExpCropYieldFieldStr().equals("") ? cropTypeView.getIntExpCropYieldWithDecimal() : cropTypeView.getExpCropYieldFieldStrWithOneDecimal();
                            String yield = cropTypeView.getExpCropYieldFieldStr().equals("0") || cropTypeView.getExpCropYieldFieldStr().equals("") ? cropTypeView.getIntExpCropYieldWithDecimal() : cropTypeView.getExpCropYieldFieldStrWithOneDecimal();
                            Double variableProductionCost = cropTypeView.getVarProductionCost().equals(zeroDouble) ? Double.parseDouble(cropTypeView.getCalculatedVariableProductionCostWithoutCommaAndDollar()) : cropTypeView.getVarProductionCost();
//						PlantingProfitLogger.info(calculateProfit(yield, cropTypeView.getIntExpCropPrice(), variableProductionCost)+"--------"+yield+"--------"+cropTypeView.getIntExpCropPrice()+"--------"+variableProductionCost+"--------"+combination);

						/* linear for profit for each crop */
                            linear.add(calculateProfit(yield, cropTypeView.getIntExpCropPrice(), variableProductionCost), combination);

                            if (beanForOutput.getFirmAcres() > zeroDouble) {
                                linear.add(calculateProfit(yield, BigDecimal.valueOf(Double.parseDouble(cropTypeView.getPriceSimple())), variableProductionCost), beanForOutput.getCropType().getCropName() + " (Contract)");
//							PlantingProfitLogger.info(calculateProfit(yield, BigDecimal.valueOf(Double.parseDouble(cropTypeView.getPriceSimple())), variableProductionCost) +"----------"+ combination + " (Contract)");
                            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                linear.add(calculateProfit(yield, BigDecimal.valueOf(Double.parseDouble(cropTypeView.getPriceSimple())), variableProductionCost), beanForOutput.getCropType().getCropName() + " (Proposed)");
//							PlantingProfitLogger.info(calculateProfit(yield, BigDecimal.valueOf(Double.parseDouble(cropTypeView.getPriceSimple())), variableProductionCost) +"----------"+ combination + " (Proposed)");
                            }
                        } else {

						/* linear for profit for each crop */

                            linear.add(beanForOutput.getProfit(), combination);
                            if (beanForOutput.getFirmAcres() > zeroDouble) {
                                linear.add(beanForOutput.getForwardprofit(), combination + " (Contract)");
                            /*linear.add(1.0, combination + " (Contract)");*/
//							PlantingProfitLogger.info(beanForOutput.getForwardprofit() + "----------" + combination + " (Contract)");
                            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                linear.add(beanForOutput.getForwardprofit(), combination + " (Proposed)");
                            /*linear.add(1.0, combination + " (Proposed)");*/
//							PlantingProfitLogger.info(beanForOutput.getForwardprofit() + "----------" + combination + " (Proposed)");
                            }
                        }
                        break;
                    }
                }
            }
            problem.setObjective(linear, OptType.MAX);


            for (String combination : combinationSet) {
                for (FieldInfoView fieldInfoView : fieldInfoViews) {
                    if (combination.split("###")[0].equals(fieldInfoView.getFieldName())) {
                        linear = new Linear();
                        linear.add(1, combination);
                        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                            /**
                             * @author Raja
                             * @since 14 Dec, 2015
                             * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                             */

                            if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                                continue;
                            }

                            if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                                if (beanForOutput.getFirmAcres() > zeroDouble) {
                                    linear.add(1, combination + " (Contract)");
//								PlantingProfitLogger.info(1 +"----------"+ combination + " (Contract)");
                                } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                    linear.add(1, combination + " (Proposed)");
//								PlantingProfitLogger.info(1 +"----------"+ combination + " (Proposed)");
                                }
                                break;
                            }
                        }
                        problem.add(linear, "=", Double.parseDouble(fieldInfoView.getFieldSize()));
                    }
                }
            }

		/*Resources Calculation*/
            for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
                if (cropResourceUsageView.getCropResourceUse().equals("Land")) {
                    continue;
                }
                linear = new Linear();
                for (String combination : combinationSet) {
                    for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                        /**
                         * @author Raja
                         * @since 14 Dec, 2015
                         * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                         */

                        if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                            continue;
                        }

                        if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                            if (cropResourceUsageView.getCropResourceUse().equals("Capital")) {
                                cropType = beanForOutput.getCropType();
                                if (cropType.getCropYieldFieldVariances() != null && cropType.getCropYieldFieldVariances().getFieldInfo().getFieldName().equals(combination.split("###")[0]) && cropResourceUsageView.getResourseOverrideAmount() != null) {
                                    Double overrideAmount = Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount());
                                    linear.add(overrideAmount, combination);
                                    if (beanForOutput.getFirmAcres() > zeroDouble) {
                                        linear.add(overrideAmount, combination + " (Contract)");
//										PlantingProfitLogger.info(overrideAmount +"----------"+ combination + " (Contract)");
                                    } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                        linear.add(overrideAmount, combination + " (Proposed)");
//										PlantingProfitLogger.info(overrideAmount +"----------"+ combination + " (Proposed)");
                                    }
                                } else {
                                    linear.add(beanForOutput.getVariableProductionCost(), combination);
                                    if (beanForOutput.getFirmAcres() > zeroDouble) {
                                        linear.add(beanForOutput.getVariableProductionCost(), combination + " (Contract)");
//										PlantingProfitLogger.info(beanForOutput.getVariableProductionCost() +"----------"+ combination + " (Contract)");
                                    } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                        linear.add(beanForOutput.getVariableProductionCost(), combination + " (Proposed)");
//										PlantingProfitLogger.info(beanForOutput.getVariableProductionCost() +"----------"+ combination + " (Proposed)");
                                    }
                                }
                            } else {
                                if (cropResourceUsageView.isActive()) {
                                for (CropResourceUsageFieldVariances cropResourceUsageFieldVariances : beanForOutput.getCropType().getCropResourceUsageFieldVariances()) {
                                    if (cropResourceUsageFieldVariances.getCropFieldResourceUse().equals(cropResourceUsageView.getCropResourceUse())) {
                                        cropType = beanForOutput.getCropType();
                                        if (cropType.getCropYieldFieldVariances() != null && beanForOutput.getCropType().getCropYieldFieldVariances().getFieldInfo().getFieldName().equals(combination.split("###")[0]) && cropResourceUsageView.getResourseOverrideAmount() != null) {
                                                Double overrideAmount = Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount());
                                                linear.add(overrideAmount, combination);
                                                if (beanForOutput.getFirmAcres() > zeroDouble) {
                                                    linear.add(overrideAmount, combination + " (Contract)");
//											PlantingProfitLogger.info(overrideAmount +"----------"+ combination + " (Contract)");
                                                } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                                    linear.add(overrideAmount, combination + " (Proposed)");
//											PlantingProfitLogger.info(overrideAmount +"----------"+ combination + " (Proposed)");
                                                }
                                            }
                                         else {
                                            linear.add(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())), combination);
                                            if (beanForOutput.getFirmAcres() > zeroDouble) {
                                                linear.add(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())), combination + " (Contract)");
//											PlantingProfitLogger.info(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())) +"----------"+ combination + " (Contract)");
                                            } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                                linear.add(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())), combination + " (Proposed)");
//											PlantingProfitLogger.info(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropResourceUsageFieldVariances.getCropResourceAmount())) +"----------"+ combination + " (Proposed)");
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                          }
                        }

                    }
                }
                problem.add(linear, "<=", Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()));
            }

		/*	Forward Sales	*/
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                if (beanForOutput.getFirmAcres() <= zeroDouble && beanForOutput.getProposedAcres() <= zeroDouble) {
                    continue;
                }

                /**
                 * @author Raja
                 * @since 14 Dec, 2015
                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                 */

                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                    continue;
                }

                linear = new Linear();
                for (String combination : combinationSet) {
                    if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                        if (beanForOutput.getFirmAcres() > zeroDouble) {
                            linear.add(1, combination + " (Contract)");
//						PlantingProfitLogger.info(1 +"----------"+ combination + " (Contract)");
                        } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                            linear.add(1, combination + " (Proposed)");
//						PlantingProfitLogger.info(1 +"----------"+ combination + " (Proposed)");
                        }
                    }
                }
                if (beanForOutput.getFirmAcres() > zeroDouble) {
                    problem.add(linear, "=", beanForOutput.getFirmAcres());
                } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                    problem.add(linear, "<=", beanForOutput.getProposedAcres());
                }
            }

		/* Minimum required crop  */
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                if (beanForOutput.getMinAcre() <= zeroDouble) {
                    continue;
                }

                /**
                 * @author Raja
                 * @since 14 Dec, 2015
                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                 */

                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                    continue;
                }

                linear = new Linear();
                for (String combination : combinationList) {
                    if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                        linear.add(1, combination);
                    }
                }
                problem.add(linear, ">=", beanForOutput.getMinAcre()/* + beanForOutput.getFirmAcres()*/);
            }

		/* Maximum required crop */
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                if (beanForOutput.getMaxAcre() <= zeroDouble) {
                    continue;
                }

                /**
                 * @author Raja
                 * @since 14 Dec, 2015
                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                 */

                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                    continue;
                }

                linear = new Linear();
                for (String combination : combinationSet) {
                    if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                        linear.add(1, combination);
                    }
                }
                problem.add(linear, "<=", beanForOutput.getMaxAcre()/* + beanForOutput.getFirmAcres()*/);
            }


		/* Minimum required crop group */
            for (CropsGroup cropsGroup : cropsGroups) {
                if (Double.parseDouble(cropsGroup.getMinimumAcres()) <= zeroDouble) {
                    continue;
                }
                linear = new Linear();
                for (String combination : combinationSet) {
                    for (CropType cropType1 : cropsGroup.getCropTypes()) {
                        if (combination.split("###")[1].equals(cropType1.getCropName())) {
                            linear.add(1, combination);
                            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                                /**
                                 * @author Raja
                                 * @since 14 Dec, 2015
                                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                                 */

                                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                                    continue;
                                }

                                if (beanForOutput.getCropType().equals(cropType1)) {
                                    if (beanForOutput.getFirmAcres() > zeroDouble) {
                                        linear.add(1, combination + " (Contract)");
//									PlantingProfitLogger.info(1 +"----------"+ combination + " (Contract)");
                                    } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                        linear.add(1, combination + " (Proposed)");
//									PlantingProfitLogger.info(1 +"----------"+ combination + " (Proposed)");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                problem.add(linear, ">=", Double.parseDouble(cropsGroup.getMinimumAcres()));
            }

		/* Maximum required crop group */
            for (CropsGroup cropsGroup : cropsGroups) {
                if (Double.parseDouble(cropsGroup.getMaximumAcres()) <= zeroDouble) {
                    continue;
                }
                linear = new Linear();
                for (String combination : combinationSet) {
                    for (CropType cropType1 : cropsGroup.getCropTypes()) {
                        if (combination.split("###")[1].equals(cropType1.getCropName())) {
                            linear.add(1, combination);
                            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                                /**
                                 * @author Raja
                                 * @since 14 Dec, 2015
                                 * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                                 */

                                if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                                    continue;
                                }

                                if (beanForOutput.getCropType().equals(cropType1)) {
                                    if (beanForOutput.getFirmAcres() > zeroDouble) {
                                        linear.add(1, combination + " (Contract)");
//									PlantingProfitLogger.info(1 +"----------"+ combination + " (Contract)");
                                    } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                                        linear.add(1, combination + " (Proposed)");
//									PlantingProfitLogger.info(1 +"----------"+ combination + " (Proposed)");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                problem.add(linear, "<=", Double.parseDouble(cropsGroup.getMaximumAcres()));
            }

            for (String combination : combinationSet) {
                problem.setVarType(combination, Double.class);
                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                    /**
                     * @author Raja
                     * @since 14 Dec, 2015
                     * @summary Ignore crops that has negative profit unless minimum crop acreage is specified
                     */

                    if (beanForOutput.getMinAcre() == 0.0 && beanForOutput.getProfit() < 0.0) {
                        continue;
                    }

                    if (combination.split("###")[1].equals(beanForOutput.getCropType().getCropName())) {
                        if (beanForOutput.getFirmAcres() > zeroDouble) {
                            problem.setVarType(combination + " (Contract)", Double.class);
                        } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                            problem.setVarType(combination + " (Proposed)", Double.class);
                        }
                        break;
                    }
                }
            }


            Result result = solver.solve(problem);
            if (result != null) {
                resultMap.put(combinationSet, result);
            }

            /**
             * For setting solver run count
             */
            setSolverRunCount();
        }

        long stop = System.currentTimeMillis();
        PlantingProfitLogger.warn("Total time taken to process : " + (stop - start));

        return resultMap;
    }


    @SuppressWarnings("unused")
    private Double calculateProfit(String yield, BigDecimal price, String productionCost) {
        Double yieldDouble = Double.parseDouble(yield);
        Double priceDouble = price.doubleValue();
        Double productionCostDouble = Double.parseDouble(productionCost);
        Double profit = ((yieldDouble * priceDouble) - productionCostDouble);
        return profit;
    }

    private Double calculateProfit(String yield, BigDecimal price, Double varProductionCost) {
        Double yieldDouble = Double.parseDouble(yield);
        Double priceDouble = price.doubleValue();
        return (yieldDouble * priceDouble) - varProductionCost;
    }

    public Set<String> getMinRequiredCropsForGroups() {
        return minRequiredCropsForGroups;
    }

    public void setMinRequiredCropsForGroups(Set<CropsGroup> cropsGroups) {
        this.minRequiredCropsForGroups = new HashSet<String>();
        for (CropsGroup cropsGroup : cropsGroups) {
            if (Double.parseDouble(cropsGroup.getMinimumAcres()) > zeroDouble) {
                for (CropType cropType : cropsGroup.getCropTypes())
                    this.minRequiredCropsForGroups.add(cropType.getCropName());
            }
        }
    }

    public Set<String> getMinRequiredCrops() {
        return minRequiredCrops;
    }

    public void setMinRequiredCrops(List<CropBeanForOutput> cropBeanForOutputs) {
        this.minRequiredCrops = new HashSet<String>();
        for (CropBeanForOutput cropBeanForOutput : cropBeanForOutputs) {
            if (cropBeanForOutput.getMinAcre() > zeroDouble) {
                this.minRequiredCrops.add(cropBeanForOutput.getCropType().getCropName());
            }
            if (cropBeanForOutput.getFirmAcres() > zeroDouble) {
                this.minRequiredCrops.add(cropBeanForOutput.getCropType().getCropName());
            }
        }
//		for(CropsGroup cropsGroup : cropsGroups){
//			if(Double.parseDouble(cropsGroup.getMinimumAcres()) > new Double(0)){
//				for(CropType cropType : cropsGroup.getCropTypes())
//				this.minRequiredCrops.add(cropType.getCropName());
//			}
//		}
    }

    private boolean validateIfCombinationSatisfy(String[] combination) {
        boolean flag = false;
        for (String cropName : getMinRequiredCrops()) {
            flag = false;
            for (String string : combination) {
                if (string.split("###")[1].equals(cropName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return flag;
            }
        }
        for (String cropName : getMinRequiredCropsForGroups()) {
            flag = false;
            for (String string : combination) {
                if (string.split("###")[1].equals(cropName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return flag;
            }
        }
        return true;
    }

    private List<String[]> generateCombinationsForAllLength(String[][] array, int minLength) {
        List<String[]> combinations = new ArrayList<String[]>();
        int index;
        int arrayLength = array.length;
        int maskedArrayLength;
        int kLength, c, lLength, mLength;
        String[][] maskedArray;
        String[] combination;
        double arrayLengthPower = Math.pow(2, arrayLength);
        for (int combinationLength = arrayLength; combinationLength >= minLength; combinationLength--) {
            for (int i = 0; i < arrayLengthPower; ++i) {
                c = 0;
                for (int j = 1; j <= arrayLengthPower; j <<= 1)
                    if ((i & j) != 0)
                        ++c;
                if (c == combinationLength) {
                    maskedArray = new String[combinationLength][];
                    maskedArrayLength = maskedArray.length;
                    for (int l = 1, j = 0, k = 0; l <= arrayLengthPower; l <<= 1, ++j)
                        if ((i & l) != 0) {
                            maskedArray[k] = array[j];
                            ++k;
                        }
                    lLength = 1;
                    for (int j = 0; j < maskedArrayLength; ++j)
                        lLength *= maskedArray[j].length;
                    for (int j = 0; j < lLength; ++j) {
                        combination = new String[combinationLength];
                        index = 0;
                        mLength = j;
                        for (int k = maskedArrayLength - 1; k >= 0; --k) {
                            kLength = maskedArray[k].length;
                            combination[index++] = maskedArray[k][mLength % kLength];
                            mLength /= kLength;
                        }
                        if (minLength > 0 && validateIfCombinationSatisfy(combination)) {
                            combinations.add(combination);
//							PlantingProfitLogger.info(Arrays.toString(combination)+ " and in if total combinations are = "+combinations.size());
                        } else if (minLength == 0) {
                            combinations.add(combination);
//							PlantingProfitLogger.info(Arrays.toString(combination)+ " and in else if total combinations are = "+combinations.size());
                        }
                    }
                }
            }
            // if(combinations.size() > 0){
            // break;
            // }
        }
        return combinations;
    }


    private int checkBestStrategyOnTheBasisOfResources(Result bestResult, String[] bestCase, Result result, String[] combinationSet, List<CropResourceUsageView> resourceUsageViews, List<CropBeanForOutput> cropBeanForOutputList) {
//		PlantingProfitLogger.info("Trying to find best strategy when Estimated Income of two strategy is same.");
        Double bestResultResourceUsagePercentage = zeroDouble;
        Double newResultResourceUsagePercentage = zeroDouble;
        Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariancesSet = null;

        for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
            if (cropResourceUsageView.getCropResourceUse().equals("Land")) {
                continue;
            }
            Double totalResourceUsage = zeroDouble;

            for (String str : bestCase) {

                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                    if (beanForOutput.getCropType().getCropName().equals(str.split("###")[1])) {
                        cropResourceUsageFieldVariancesSet = beanForOutput.getCropType().getCropResourceUsageFieldVariances();

                        for (CropResourceUsageFieldVariances fieldVariances : cropResourceUsageFieldVariancesSet) {

                            if (fieldVariances.getCropFieldResourceUse().equals(cropResourceUsageView.getCropResourceUse())) {
                                CropType cropType = beanForOutput.getCropType();

                                if (cropType.getCropYieldFieldVariances() != null && cropType.getCropYieldFieldVariances().getFieldInfo().getFieldName().equals(str.split("###")[0]) && cropResourceUsageView.getResourseOverrideAmount() != null) {
                                    totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * bestResult.get(str).doubleValue();

                                    if (beanForOutput.getFirmAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * bestResult.get(str + " (Contract)").doubleValue();
                                    } else if (beanForOutput.getProposedAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * bestResult.get(str + " (Proposed)").doubleValue();
                                    }
                                } else {
                                    totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * bestResult.get(str).doubleValue();
                                    if (beanForOutput.getFirmAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * bestResult.get(str + " (Contract)").doubleValue();
                                    } else if (beanForOutput.getProposedAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * bestResult.get(str + " (Proposed)").doubleValue();
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            bestResultResourceUsagePercentage += (totalResourceUsage * 100) / Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank());
            totalResourceUsage = zeroDouble;
            for (String str : combinationSet) {

                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {

                    if (beanForOutput.getCropType().getCropName().equals(str.split("###")[1])) {

                        for (CropResourceUsageFieldVariances fieldVariances : beanForOutput.getCropType().getCropResourceUsageFieldVariances()) {

                            if (fieldVariances.getCropFieldResourceUse().equals(cropResourceUsageView.getCropResourceUse())) {
                                CropType cropType = beanForOutput.getCropType();

                                if (cropType.getCropYieldFieldVariances() != null && cropType.getCropYieldFieldVariances().getFieldInfo().getFieldName().equals(str.split("###")[0]) && cropResourceUsageView.getResourseOverrideAmount() != null) {
                                    totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * result.get(str).doubleValue();

                                    if (beanForOutput.getFirmAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * result.get(str + " (Contract)").doubleValue();
                                    } else if (beanForOutput.getProposedAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(cropResourceUsageView.getResourseOverrideAmount()) * result.get(str + " (Proposed)").doubleValue();
                                    }

                                } else {
                                    totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * result.get(str).doubleValue();

                                    if (beanForOutput.getFirmAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * result.get(str + " (Contract)").doubleValue();
                                    } else if (beanForOutput.getProposedAcres() > 0) {
                                        totalResourceUsage += Double.parseDouble(AgricultureStandardUtils.removeAllCommas(fieldVariances.getCropResourceAmount())) * result.get(str + " (Proposed)").doubleValue();
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            newResultResourceUsagePercentage += (totalResourceUsage * 100) / Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank());
        }

//		PlantingProfitLogger.info("Best = "+(bestResultResourceUsagePercentage / (resourceUsageViews.size()-1)) +" New = "+ (newResultResourceUsagePercentage / (resourceUsageViews.size()-1)));
        double v = bestResultResourceUsagePercentage / (resourceUsageViews.size() - 1);
        double v1 = newResultResourceUsagePercentage / (resourceUsageViews.size() - 1);
        if (v <= v1) {
            return 0;
        } else {
            return 1;
        }
    }


    private void setSolverRunCount() {
        int solverCount = 0;
        try {
            Object obj = httpSession.getAttribute("solverCount");

            if (obj != null) {
                solverCount = Integer.parseInt(obj.toString());
            }

            solverCount++;

            httpSession.removeAttribute("solverCount");
            httpSession.setAttribute("solverCount", solverCount);
//			PlantingProfitLogger.warn("Current solver run count is : " + solverCount);
        } catch (Exception e) {
//			PlantingProfitLogger.info(e.getMessage());
        }


    }

    /**
     * @changed - Abhishek
     * @date - 19-10-2016
     * @desc - implemented threads for processing of calculation thereby reducing the time to process
     */
    private class TempListHolder implements Runnable {
        private LinearProgrammingResultBean linearProgrammingResultBean;
        private Set<Object> resultSet;

        public LinearProgrammingResultBean getLinearProgrammingResultBean() {
            return linearProgrammingResultBean;
        }

        public void setLinearProgrammingResultBean(LinearProgrammingResultBean linearProgrammingResultBean) {
            this.linearProgrammingResultBean = linearProgrammingResultBean;
        }

        public Set<Object> getResultSet() {
            return resultSet;
        }

        public void setResultSet(Set<Object> resultSet) {
            this.resultSet = resultSet;
        }

        @Override
        public void run() {

            String[] bigBestCase = null;
            Long bigBestCaseProfit = 0L;
            Result bigBestResult = null;

            String[] smallBestCase = null;
            Long smallBestCaseProfit = 0L;
            Result smallBestResult = null;

            int bigValueCount = 0;
            int smallValueCount = 0;

            LinearProgrammingResultBean linearProgrammingResultBean = getLinearProgrammingResultBean();

            List<String[]> combinationList = linearProgrammingResultBean.getCombination();

            Result bigResult = null;
            Result smallResult = null;
            int bestStrategyOnBasisOfResources;

            Map<String[], Result> resultMap = getLinearProgramingResultForField(linearProgrammingResultBean.getCropBeanForOutputList(),
                    linearProgrammingResultBean.getResourceUsageViews(),
                    linearProgrammingResultBean.getCropsGroups(),
                    combinationList,
                    linearProgrammingResultBean.getFieldInfoViews());

            Set<String[]> keySet = resultMap.keySet();

            for (String[] combination : keySet) {
                Result result = resultMap.get(combination);

//			totalCombination++;
//			if(!validateIfCombinationSatisfy(combinationSet)){
//				continue;
//			}
//			PlantingProfitLogger.info("Total Combination = "+totalCombination+" Satisfied Combination = "+satisfiedCombination++);
                if (result != null) {
//				PlantingProfitLogger.info("Result for " + Arrays.toString(combinationSet) + " = " + result);

                    Long potentialProfit = result.getObjective().longValue();
                    if (potentialProfit.toString().length() > 9) {
                        bigValueCount++;
                        bigResult = result;
                        if (bigResult.getObjective().longValue() == bigBestCaseProfit) {
                            bestStrategyOnBasisOfResources = checkBestStrategyOnTheBasisOfResources(bigBestResult,
                                    bigBestCase, bigResult, combination,
                                    linearProgrammingResultBean.getResourceUsageViews(),
                                    linearProgrammingResultBean.getCropBeanForOutputList());
                            if (bestStrategyOnBasisOfResources == 1) {
                                bigBestCase = combination;
                                bigBestCaseProfit = bigResult.getObjective().longValue();
                                bigBestResult = bigResult;
                            }
                        } else if (bigResult.getObjective().longValue() > bigBestCaseProfit) {
                            bigBestCase = combination;
                            bigBestCaseProfit = bigResult.getObjective().longValue();
                            bigBestResult = bigResult;
                        }
                    } else if (potentialProfit.toString().length() <= 9) {
                        smallValueCount++;
                        smallResult = result;
                        if (smallResult.getObjective().longValue() == smallBestCaseProfit) {
                            bestStrategyOnBasisOfResources = checkBestStrategyOnTheBasisOfResources(smallBestResult, smallBestCase,
                                    smallResult, combination,
                                    linearProgrammingResultBean.getResourceUsageViews(),
                                    linearProgrammingResultBean.getCropBeanForOutputList());
                            if (bestStrategyOnBasisOfResources == 1) {
                                smallBestCase = combination;
                                smallBestCaseProfit = smallResult.getObjective().longValue();
                                smallBestResult = smallResult;
                            }
                        } else if (smallResult.getObjective().longValue() > smallBestCaseProfit) {
                            smallBestCase = combination;
                            smallBestCaseProfit = smallResult.getObjective().longValue();
                            smallBestResult = smallResult;
                        }
                    }
                }


            }

            Map<String, Object> result = new HashedMap();

            result.put("bigBestCase", bigBestCase);
            result.put("bigBestCaseProfit", bigBestCaseProfit);
            result.put("bigBestResult", bigResult);

            result.put("smallBestCase", smallBestCase);
            result.put("smallBestCaseProfit", smallBestCaseProfit);
            result.put("smallBestResult", smallBestResult);

            result.put("bigValueCount", bigValueCount);
            result.put("smallValueCount", smallValueCount);

            getResultSet().add(result);

        }
    }
}