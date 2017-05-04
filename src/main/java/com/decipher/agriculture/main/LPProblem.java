package com.decipher.agriculture.main;

import com.decipher.util.PlantingProfitLogger;
/*import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;*/
import lpsolve.*;
import net.sf.javailp.*;

import java.util.ArrayList;
import java.util.List;

public class LPProblem {

	/**
	 * SolverFactoryLpSolve
	 */
	public static void main(String[] args) {
//		System.setProperty("java.library.path", "/usr/local/lib/jni/");
//		SolverFactory factory = new SolverFactoryGLPK();
		SolverFactory factory = new SolverFactoryLpSolve();	// use lp_solve
		factory.setParameter(Solver.VERBOSE, 0);
		// factory.setParameter(Solver.POSTSOLVE, 0);
		factory.setParameter(Solver.TIMEOUT, 100);
		Problem problem = new Problem();

		/* Profit by each crop */
		Linear linear = new Linear();
		linear.add(143, "Crop A");
		linear.add(60, "Crop B");
		problem.setObjective(linear, OptType.MAX);

		/* Acre for crop */
		linear = new Linear();
		linear.add(1, "Crop A");
		linear.add(1, "Crop B");
		problem.add(new Constraint("Land", linear, Operator.LE, 143));
		linear = new Linear();
		linear.add(1, "Crop A");
		problem.add(new Constraint(linear, Operator.GE, 0));
		linear = new Linear();
		linear.add(1, "Crop B");
		problem.add(linear, ">=", 0);
		linear = new Linear();
		linear.add(120, "Crop A");
		linear.add(210, "Crop B");
		problem.add(new Constraint("Capital", linear, Operator.LE, 15000));
		linear = new Linear();
		linear.add(110, "Crop A");
		linear.add(30, "Crop B");
		problem.add(new Constraint("Water", linear, Operator.LE, 7000));

		linear = new Linear();
		linear.add(1, "Crop A");
		problem.add(new Constraint("Crop A maximum Limit", linear, Operator.LE, 143));
		linear = new Linear();
		linear.add(1, "Crop B");
		problem.add(new Constraint("Crop B maximum Limit", linear, Operator.LE, 60));

//		linear = new Linear();
//		linear.add(1, "Crop A");
//		problem.add(new Constraint("Crop A minimum Limit", linear, Operator.GE, 12));
//		linear = new Linear();
//		linear.add(1, "Crop B");
//		problem.add(new Constraint("Crop B minimum Limit", linear, Operator.GE, 12));

		problem.setVarType("Crop A", Double.class);
		problem.setVarType("Crop B", Double.class);

		Solver solver = factory.get();
		Result result = solver.solve(problem);
		System.out.println("result = " + result);
		System.out.println("Capital " + result.getDualValue("Capital"));
		System.out.println("Land " + result.getDualValue("Land"));
		System.out.println("Water " + result.getDualValue("Water"));
		System.out.println("Crop A maximum Limit " + result.getDualValue("Crop A maximum Limit"));
		System.out.println("Crop B maximum Limit " + result.getDualValue("Crop B maximum Limit"));
		System.out.println("Crop A minimum Limit " + result.getDualValue("Crop A minimum Limit"));
		System.out.println("Crop B minimum Limit " + result.getDualValue("Crop B minimum Limit"));
	}

	/**
	 * SolverFactoryCplex
	 */
	public static void main2(String arg[]){
//		SolverFactory solver = new SolverFactoryGLPK();
		SolverFactory solver = new SolverFactoryCPLEX();

		Problem problem = new Problem();

		Linear linear = new Linear();
		linear.add(40, "x"); // -> Corn
		linear.add(30, "y"); // -> Oats
		problem.setObjective(linear, OptType.MAX);

		// Constraints
		Linear c1 = new Linear();
		c1.add(0, "x");
		problem.add(new Constraint(c1, Operator.GE, 0));

		Linear c2 = new Linear();
		c1.add(0, "y");
		problem.add(new Constraint(c2, Operator.GE, 0));

		Linear c3 = new Linear();
		c3.add(2, "x");
		c3.add(1, "y");
		problem.add(new Constraint(c3, Operator.LE, 320));

		Linear c4 = new Linear();
		c4.add(1, "x");
		c4.add(1, "y");
		problem.add(new Constraint(c4, Operator.LE, 240));

		problem.setVarType("x", Double.class);
		problem.setVarType("y", Double.class);
		System.loadLibrary("cplex1263");

		SolverCPLEX solver1 = (SolverCPLEX) solver.get();
//		Solver solver1 = solver.get();
		Result result = solver1.solve(problem);

		System.out.println("result = " + result);
		System.out.println("result.getDualValue(\"x\") = " + result.getDualValue("x"));
		System.out.println("result.getDualValue(\"y\") = " + result.getDualValue("y"));

//		sensitivity();

	};

	/**
	 * Senstivity analysis
	 */
	private static void sensitivity(){
//		SolverFactory solverFactory = new SolverFactoryGLPK();
//		SolverFactory solverFactory = new SolverFactoryLpSolve();
		SolverFactory solverFactory = new SolverFactoryCPLEX();
		Solver solver = solverFactory.get();


		Problem problem = new Problem();

		String corn = "corn";
		String barley = "barley";
		String wheat = "wheat";
		String potatoes = "potatoes";
		String sugar_beets = "sugar_beets";
		String hay = "hay";
		String l = "labor";
		String a = "area";
		String p = "prod_cost";

		Linear linear = new Linear();
		linear.add(316.0, corn);
		linear.add(210.0, barley);
		linear.add(223.0, wheat);
		linear.add(672.0, potatoes);
		linear.add(423.0, sugar_beets);
		linear.add(288.0, hay);
		problem.setObjective(linear, OptType.MAX);

		// Add constraints
		Linear areaLinear = new Linear();
		areaLinear.add(1, corn);
		areaLinear.add(1, barley);
		areaLinear.add(1, wheat);
		areaLinear.add(1, potatoes);
		areaLinear.add(1, sugar_beets);
		areaLinear.add(1, hay);
		problem.add(l, areaLinear, Operator.LE, 1500);

		Linear prodCost = new Linear();
		prodCost.add(121.40, corn);
		prodCost.add(72.50, barley);
		prodCost.add(122.40, wheat);
		prodCost.add(611.12, potatoes);
		prodCost.add(517.20, sugar_beets);
		prodCost.add(99.89, hay);
		problem.add(new Constraint(a, prodCost, Operator.LE, 250000));

		Linear labor = new Linear();
		labor.add(0.3, corn);
		labor.add(0.2, barley);
		labor.add(0.3, wheat);
		labor.add(0.9, potatoes);
		labor.add(0.5, sugar_beets);
		labor.add(0.1, hay);
		problem.add(new Constraint(p, labor, Operator.LE, 15000));

        /*Linear minimumAcreageSB = new Linear();
        minimumAcreageSB.add(1, sugar_beets);
        problem.add(new Constraint("minimum_sb", minimumAcreageSB, Operator.GE, 300));

        Linear minimumAcreageWheat = new Linear();
        minimumAcreageWheat.add(1, wheat);
        problem.add(new Constraint("minimum_wheat", minimumAcreageWheat, Operator.GE, 400));

        Linear maximumAcreagePotatoes = new Linear();
        maximumAcreagePotatoes.add(1, potatoes);
        problem.add(new Constraint("maximum_potatoes", maximumAcreagePotatoes, Operator.LE, 500));*/

		problem.setVarLowerBound(sugar_beets, 300);
		problem.setVarLowerBound(wheat, 400);
		problem.setVarUpperBound(potatoes, 500);

		/*problem.setVarType(corn, Double.class);
		problem.setVarType(barley, Double.class);
		problem.setVarType(wheat, Double.class);
		problem.setVarType(potatoes, Double.class);
		problem.setVarType(sugar_beets, Double.class);
		problem.setVarType(hay, Double.class);*/

		Result result = solver.solve(problem);
		System.out.println("result = " + result);
		System.out.println("result.getDualValue(corn) = " + result.getPrimalValue(corn));
		System.out.println("result.getDualValue(barley) = " + result.getDualValue(barley));
		System.out.println("result.getDualValue(wheat) = " + result.getDualValue(wheat));
		System.out.println("result.getDualValue(potatoes) = " + result.getDualValue(potatoes));
		System.out.println("result.getDualValue(sugar_beets) = " + result.getDualValue(sugar_beets));
		System.out.println("result.getDualValue(hay) = " + result.getDualValue(hay));
		System.out.println("result.getDualValue(\"area\") = " + result.getPrimalValue(a));
		System.out.println("result.getDualValue(\"prod_cost\") = " + result.getPrimalValue(p));
		System.out.println("result.getDualValue(\"labor\") = " + result.get(l));
	}

	/**
	 * Cplex Solver IloCplex
	 */
	public static void main3(String arg[]){
		/**
		 * Cplex Solver
		 */
		try {
			/*IloCplex cplex = new IloCplex();

			// variables
			IloNumVar x = cplex.numVar(0, Double.MAX_VALUE, "x");
			IloNumVar y = cplex.numVar(0, Double.MAX_VALUE, "y");

			// expressions
			IloLinearNumExpr objective = cplex.linearNumExpr();
			objective.addTerm(0.12, x);
			objective.addTerm(0.15, y);

			// define objective
			cplex.addMinimize(objective);

			// define constraints
			List<IloRange> constraints = new ArrayList<IloRange>();

			constraints.add(cplex.addGe(cplex.sum(cplex.prod(60, x),cplex.prod(60, y)), 300));
			constraints.add(cplex.addGe(cplex.sum(cplex.prod(12, x),cplex.prod(6, y)), 36));
			constraints.add(cplex.addGe(cplex.sum(cplex.prod(10, x),cplex.prod(30, y)), 90));

			IloLinearNumExpr num_expr = cplex.linearNumExpr();
			num_expr.addTerm(2, x);
			num_expr.addTerm(-1,y);

			constraints.add(cplex.addEq(num_expr, 0));

			num_expr = cplex.linearNumExpr();
			num_expr.addTerm(1,y);
			num_expr.addTerm(-1,x);

			constraints.add(cplex.addLe(num_expr,8));

			// display option
//			cplex.setParam(IloCplex.Param.Simplex.Display, 0);
			cplex.setParam(IloCplex.DoubleParam.TiLim, 1000);

			// solve
			if (cplex.solve()) {
				System.out.println("obj = "+cplex.getObjValue());
				System.out.println("x   = "+cplex.getValue(x));
				System.out.println("y   = "+cplex.getValue(y));
				for (int i=0;i<constraints.size();i++) {
					System.out.println("dual constraint "+(i+1)+"  = "+cplex.getDual(constraints.get(i)));
					System.out.println("slack constraint "+(i+1)+" = "+cplex.getSlack(constraints.get(i)));
				}
			}
			else {
				System.out.println("Model not solved");
			}

			cplex.end();*/
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * LpSolve Solver
	 */
	public static void main4(String[] args) {
		try {
			// Create a problem with 4 variables and 0 constraints

			lpsolve.LpSolve solver = lpsolve.LpSolve.makeLp(0, 5);

			// add constraints
			solver.strAddConstraint("3 2 2 1 2", LpSolve.LE, 4);
			solver.strAddConstraint("0 4 3 1 4", LpSolve.GE, 3);

			// set objective function
			solver.strSetObjFn("2 3 -2 3 -2");
//			solver.setSimplextype(solver.SIMPLEX_DUAL_PRIMAL);
			// solve the problem
			solver.solve();

			// print solution
			System.out.println("Value of objective function: " + solver.getObjective());
			double[] ptrPrimalSolution = solver.getPtrPrimalSolution();
			double[] var = solver.getPtrVariables();
			double[] ptrDualSolution = solver.getPtrDualSolution();
			for (int i = 0; i < var.length; i++) {
				System.out.println("Value of var[" + i + "] = " + var[i]);
			}

			for (int i = 0; i < ptrDualSolution.length; i++) {
				System.out.println("Dual Value of ptrDualSolution[" + i + "] = " + ptrDualSolution[i]);
			}

			for (int i = 0; i < ptrPrimalSolution.length; i++) {
				System.out.println("Dual Value of ptrPrimalSolution[" + i + "] = " + ptrPrimalSolution[i]);
			}

			// delete the problem and free memory
			solver.deleteLp();
		}
		catch (LpSolveException e) {
			e.printStackTrace();
		}
	}
}