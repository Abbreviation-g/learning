

import java.util.HashMap;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.ArrayExpr;
import com.microsoft.z3.ArraySort;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Goal;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Log;
import com.microsoft.z3.Params;
import com.microsoft.z3.RealExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Symbol;

public class MyZ3Example {
	// -Djava.library.path=dll
	public static void main(String[] args) {

		System.loadLibrary("libz3");
		Log.open("test.log");
		try {
			HashMap<String, String> cfg = new HashMap<String, String>();
			cfg.put("model", "true");
			cfg.put("proof", "true");
			Context ctx = new Context(cfg);
			// simpleAdd(ctx);
			// arrayExample1(ctx);
			arrayExample2(ctx);
			ctx.close();
		} catch (Exception e) {
			System.out.println("z3 exception");
			e.printStackTrace();
		}

	}

	static void simpleAdd(Context ctx) {
		Solver s = ctx.mkSolver();
		RealExpr a = ctx.mkRealConst("a");
		RealExpr c = ctx.mkRealConst("c");
		RealExpr d = ctx.mkRealConst("d");
		RealExpr e = ctx.mkRealConst("e");
		RealExpr x = ctx.mkRealConst("x");
		RealExpr y = ctx.mkRealConst("y");
		ArithExpr left = ctx.mkAdd(x, ctx.mkMul(y, c, e));
		ArithExpr right = ctx.mkAdd(d, a);
		BoolExpr equation = ctx.mkEq(left, right);
		s.add(equation);
		s.add(ctx.mkGt(a, ctx.mkReal(0)));

		Status result = s.check();
		if (result == Status.SATISFIABLE) {
			System.out.println("model for: x + y*c*e = d + a, a > 0");
			System.out.println(s.getModel());
			System.out.println(a.toString());
			Expr a_value = s.getModel().evaluate(a, false);
			System.out.println(a_value.toString());
		} else if (result == Status.UNSATISFIABLE)
			System.out.println("unsat");
		else
			System.out.println("unknow");
	}

	static void arrayExample1(Context ctx) throws TestFailedException {
		System.out.println("ArrayExample1");

		Goal g = ctx.mkGoal(true, false, false);
		ArraySort asort = ctx.mkArraySort(ctx.getIntSort(), ctx.mkBitVecSort(32));
		ArrayExpr aex = (ArrayExpr) ctx.mkConst(ctx.mkSymbol("MyArray"), asort);
		Expr sel = ctx.mkSelect(aex, ctx.mkInt(0));
		g.add(ctx.mkEq(sel, ctx.mkBV(42, 32)));
		Symbol xs = ctx.mkSymbol("x");
		IntExpr xc = (IntExpr) ctx.mkConst(xs, ctx.getIntSort());

		Symbol fname = ctx.mkSymbol("f");
		Sort[] domain = { ctx.getIntSort() };
		FuncDecl fd = ctx.mkFuncDecl(fname, domain, ctx.getIntSort());
		Expr[] fargs = { ctx.mkConst(xs, ctx.getIntSort()) };
		IntExpr fapp = (IntExpr) ctx.mkApp(fd, fargs);

		g.add(ctx.mkEq(ctx.mkAdd(xc, fapp), ctx.mkInt(123)));

		Solver s = ctx.mkSolver();
		for (BoolExpr a : g.getFormulas())
			s.add(a);
		System.out.println("Solver: " + s);

		Status q = s.check();
		System.out.println("Status: " + q);

		if (q != Status.SATISFIABLE)
			throw new RuntimeException();

		System.out.println("Model = " + s.getModel());

		System.out.println("Interpretation of MyArray:\n" + s.getModel().getFuncInterp(aex.getFuncDecl()));
		System.out.println("Interpretation of x:\n" + s.getModel().getConstInterp(xc));
		System.out.println("Interpretation of f:\n" + s.getModel().getFuncInterp(fd));
		System.out.println("Interpretation of MyArray as Term:\n" + s.getModel().getFuncInterp(aex.getFuncDecl()));
	}

	public static void arrayExample2(Context ctx) throws TestFailedException {
		System.out.println("ArrayExample2");
		Log.append("ArrayExample2");

		Sort int_type = ctx.getIntSort();
		Sort array_type = ctx.mkArraySort(int_type, int_type);

		ArrayExpr a1 = (ArrayExpr) ctx.mkConst("a1", array_type);
		ArrayExpr a2 = ctx.mkArrayConst("a2", int_type, int_type);
		Expr i1 = ctx.mkConst("i1", int_type);
		Expr i2 = ctx.mkConst("i2", int_type);
		Expr i3 = ctx.mkConst("i3", int_type);
		Expr v1 = ctx.mkConst("v1", int_type);
		Expr v2 = ctx.mkConst("v2", int_type);

		Expr st1 = ctx.mkStore(a1, i1, v1);
		Expr st2 = ctx.mkStore(a2, i2, v2);

		Expr sel1 = ctx.mkSelect(a1, i3);
		Expr sel2 = ctx.mkSelect(a2, i3);

		/* create antecedent */
		BoolExpr antecedent = ctx.mkEq(st1, st2);

		/*
		 * create consequent: i1 = i3 or i2 = i3 or select(a1, i3) = select(a2, i3)
		 */
		BoolExpr consequent = ctx.mkOr(ctx.mkEq(i1, i3), ctx.mkEq(i2, i3), ctx.mkEq(sel1, sel2));

		/*
		 * prove store(a1, i1, v1) = store(a2, i2, v2) implies (i1 = i3 or i2 = i3 or
		 * select(a1, i3) = select(a2, i3))
		 */
		BoolExpr thm = ctx.mkImplies(antecedent, consequent);
		System.out.println(
				"prove: store(a1, i1, v1) = store(a2, i2, v2) implies (i1 = i3 or i2 = i3 or select(a1, i3) = select(a2, i3))");
		System.out.println(thm);
		prove(ctx, thm, false);
	}

	static void prove(Context ctx, BoolExpr f, boolean useMBQI) throws TestFailedException {
		BoolExpr[] assumptions = new BoolExpr[0];
		prove(ctx, f, useMBQI, assumptions);
	}

	static void prove(Context ctx, BoolExpr f, boolean useMBQI, BoolExpr... assumptions) throws TestFailedException {
		System.out.println("Proving: " + f);
		Solver s = ctx.mkSolver();
		Params p = ctx.mkParams();
		p.add("mbqi", useMBQI);
		s.setParameters(p);
		for (BoolExpr a : assumptions)
			s.add(a);
		s.add(ctx.mkNot(f));
		Status q = s.check();

		switch (q) {
		case UNKNOWN:
			System.out.println("Unknown because: " + s.getReasonUnknown());
			break;
		case SATISFIABLE:
			throw new TestFailedException();
		case UNSATISFIABLE:
			System.out.println("OK, proof: " + s.getProof());
			break;
		}
	}

	@SuppressWarnings("serial")
	static class TestFailedException extends Exception {
		public TestFailedException() {
			super("Check FAILED");
		}
	};
}
