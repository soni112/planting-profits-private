package com.decipher.agriculture.main;

import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;

public class LPProblemByField {

	public static void main(String[] args) {
		SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
		factory.setParameter(Solver.VERBOSE, 0);
		factory.setParameter(Solver.TIMEOUT, 100);
		Problem problem = new Problem();

		/* Profit by each crop */
		Linear linear = new Linear();
		linear.add(10, "Crop A Field A");
		linear.add(10, "Crop B Field A");
		linear.add(10, "Crop A Field B");
		linear.add(10, "Crop B Field B");
		linear.add(10, "Crop A Field C");
		linear.add(10, "Crop B Field C");
		linear.add(10, "Crop A Field D");
		linear.add(10, "Crop B Field D");
		problem.setObjective(linear, OptType.MAX);

		linear = new Linear();
		linear.add(1, "Crop A Field A");
		linear.add(1, "Crop B Field A");
		problem.add(linear, "<=", 10);
		
		linear = new Linear();
		linear.add(1, "Crop A Field B");
		linear.add(1, "Crop B Field B");
		problem.add(linear, "<=", 10);

		linear = new Linear();
		linear.add(1, "Crop A Field C");
		linear.add(1, "Crop B Field C");
		problem.add(linear, "=", 20);

		linear = new Linear();
		linear.add(1, "Crop A Field D");
		linear.add(1, "Crop B Field D");
		problem.add(linear, "<=", 10);

		problem.setVarType("Crop A Field A", int.class);
		problem.setVarType("Crop B Field A", int.class);
		problem.setVarType("Crop A Field B", int.class);
		problem.setVarType("Crop B Field B", int.class);
		problem.setVarType("Crop A Field C", int.class);
		problem.setVarType("Crop B Field C", int.class);
		problem.setVarType("Crop A Field D", int.class);
		problem.setVarType("Crop B Field D", int.class);
		Solver solver = factory.get();
		Result result = solver.solve(problem);
		System.out.println(result);
		String val = "Crop A Field C";
		System.out.println(result.get(val)+"-----------"+result.getPrimalValue(val)+"-----------"+result.getDualValue(val));
	}
}