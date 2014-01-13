package com.tti.test;

import com.processEngine.MotorProcesos;

public class ProbarProcessEngine {
	public static void main(String[] args) {
		MotorProcesos motor = new MotorProcesos();
		motor.Crear();
		System.out.println("Total definiciones: " + motor.getDefiniciones());
	}
	
}
