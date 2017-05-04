package com.decipher.test;

/*import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.FunctionsUtils;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.PrimalDualMethod;*/

/**
 * Created by abhishek on 20-01-2016.
 */
public class JoptimizerTest {
    public static void main(String[] args) {

        /*// Objective function (linear)
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[] { 15, 20 }, 0);

        // Inquality constraints
        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[2];
        inequalities[0] = new LinearMultivariateRealFunction(new double[] { -1,  0 }, 0);
        inequalities[1] = new LinearMultivariateRealFunction(new double[] {  0, -1 }, 0);

        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{0.9, 0.1});
        or.setFi(inequalities);
        // Equality constraints
        or.setA(new double[][]{{1, 1}});
        or.setB(new double[]{1});
        or.setTolerance(1.E-9);

        // optimization
        PrimalDualMethod opt = new PrimalDualMethod();
        opt.setOptimizationRequest(or);

        int  returnCode = 0;
        try {

            returnCode = opt.optimize();

            double[] sol = opt.getOptimizationResponse().getSolution();
//            sol[0] = 0;
//            sol[1] = 1;

            System.out.println("sol = " + sol);


        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }
}
