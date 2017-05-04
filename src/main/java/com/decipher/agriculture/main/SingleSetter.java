package com.decipher.agriculture.main;

public class SingleSetter {

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.setAll("Hello1", "Hello2", "Hello3", "Hello4");
		System.out.println(demo.getA());
		System.out.println(demo.getB());
		System.out.println(demo.getC());
		System.out.println(demo.getD());
	}

}

class Demo {
	private String a;
	private String b;
	private String c;
	private String d;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public void setAll(String a, String b, String c, String d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
}