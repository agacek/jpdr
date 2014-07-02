package jpdr.aiger;

/***************************************************************************
 Copyright (c) 2006, Armin Biere, Johannes Kepler University.
 Copyright (c) 2006, Daniel Le Berre, Universite d'Artois.

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to
 deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 sell copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 IN THE SOFTWARE.
 ***************************************************************************/
import static jpdr.expr.Expr.FALSE;
import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.equal;
import static jpdr.expr.Expr.not;
import static jpdr.expr.Expr.var;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jpdr.expr.Expr;
import jpdr.expr.Var;

public class JaigerToExpr {
	private static final String PREFIX = "x";
	private final InputStream in;

	private int M, I, L, O, A;
	private Expr P;
	private final List<Expr> Ts = new ArrayList<>();
	private final List<Expr> latched = new ArrayList<>();

	private int charno;
	private int lineno;
	private int prev;

	public JaigerToExpr(InputStream in) {
		this.in = in;
		charno = 0;
		lineno = 0;
		prev = '\n';
	}

	public void parse() throws IOException {
		parseHeader();
		parseLataches();
		parseOutput();
		parseAnds();
	}

	private int next() throws IOException {
		int res = in.read();
		if (res != -1) {
			charno++;
			if (prev == '\n') {
				lineno++;
			}
		}

		prev = res;
		return res;
	}

	private Expr aigerlit2Expr(int l) {
		Expr res;

		assert (0 <= l);
		assert (l <= 2 * M + 1);

		int sign = l & 1;
		int idx = l / 2;

		if (idx == 0) {
			res = FALSE;
		} else {
			res = new Var(PREFIX + idx);
		}

		if (sign != 0) {
			res = not(res);
		}

		return res;
	}

	private void parseError(String msg) throws IOException {
		throw new IOException("line " + lineno + ": character " + charno + ": " + msg);
	}

	private int parseInt(char expected) throws IOException {
		int res, ch;
		ch = next();

		if (ch < '0' || ch > '9') {
			parseError("expected digit");
		}
		res = ch - '0';

		while ((ch = next()) >= '0' && ch <= '9') {
			res = 10 * res + (ch - '0');
		}

		if (ch != expected) {
			parseError("unexpected character");
		}

		return res;
	}

	public void parseHeader() throws IOException {
		if (next() != 'a' || next() != 'i' || next() != 'g' || next() != ' ') {
			parseError("expected 'aig' header line");
		}

		M = parseInt(' ');
		I = parseInt(' ');

		L = parseInt(' ');

		O = parseInt(' ');
		if (O != 1) {
			parseError("expected exactly one output");
		}

		A = parseInt('\n');

		if (M != I + L + A) {
			parseError("invalid header");
		}
	}

	public void parseLataches() throws IOException {
		for (int i = I + 1; i <= I + L; i++) {
			Var v = var(PREFIX + i);
			Expr e = aigerlit2Expr(parseInt('\n'));
			Ts.add(equal(v.prime(), e));
			latched.add(e);
		}
	}

	public void parseOutput() throws IOException {
		P = aigerlit2Expr(parseInt('\n'));
	}

	private int safeGet() throws IOException {
		int ch = next();
		if (ch == -1) {
			parseError("unexpected EOF");
		}
		return ch;
	}

	private int decode() throws IOException {
		int x = 0, i = 0;
		int ch;

		while (((ch = safeGet()) & 0x80) > 0) {
			x |= (ch & 0x7f) << (7 * i++);
		}

		return x | (ch << (7 * i));
	}

	public void parseAnds() throws IOException {
		for (int lhs = 2 * (I + L + 1); lhs <= 2*M; lhs += 2) {
			int tmp = decode();
			assert (tmp < lhs);
			int rhs0 = lhs - tmp;
			tmp = decode();
			assert (tmp <= rhs0);
			int rhs1 = rhs0 - tmp;
			Expr T = equal(aigerlit2Expr(lhs),
					and(aigerlit2Expr(rhs0), aigerlit2Expr(rhs1)));
			Ts.add(T);
		}
	}

	public Expr getInitial() {
		System.out.println("Initial:");
		System.out.println(P);
		latched.stream().map(e -> not(e)).forEach(System.out::println);
		System.out.println();
		
		return and(P, and(latched.stream().map(e -> not(e))));
	}

	public Expr getTransition() {
		System.out.println("Transition:");
		Ts.stream().forEach(System.out::println);
		System.out.println();
		
		return and(Ts.stream());
	}

	public Expr getProperty() {
		System.out.println("Property:");
		System.out.println(P);
		System.out.println();
		
		return P;
	}
}