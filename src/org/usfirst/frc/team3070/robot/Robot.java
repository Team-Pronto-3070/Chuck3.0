package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends IterativeRobot {
	TalonSRX tal0, tal1, tal2, tal3;
	Joystick xbox;
	Compressor comp;
	Solenoid sol1, sol2, sol3, sol4, sol5, sol6;
	public final double DEAD_ZONE = .2;
	public final double RAMP_IT_UP = .5;
	public double lturn_coefficient;
	public double rturn_coefficient;
	enum driveMode {
		tank, vidya
	}
	driveMode selCM = driveMode.tank;

	public void robotInit() {
		tal0 = new TalonSRX(0); // RIGHT
		tal1 = new TalonSRX(1); // left
		tal2 = new TalonSRX(2); // left
		tal3 = new TalonSRX(3); // right
		xbox = new Joystick(2);
		comp = new Compressor();
		sol1 = new Solenoid(0);
		sol2 = new Solenoid(1);
		tal0.set(ControlMode.PercentOutput, 0);
		tal1.set(ControlMode.PercentOutput, 0);
		tal2.set(ControlMode.PercentOutput, 0);
		tal3.set(ControlMode.PercentOutput, 0);
		tal0.setInverted(true);
		tal1.setInverted(true);
		tal2.setInverted(true);
		tal3.setInverted(true);
		sol1.set(false);
		sol2.set(true);
	}

	public void teleopPeriodic() {
		if(xbox.getRawButton(1)) {
			if(selCM == driveMode.tank) {
				selCM = driveMode.vidya;
			} else if(selCM == driveMode.vidya) {
				selCM = driveMode.tank;
			}
		}
		drive(selCM);
		if (xbox.getRawButton(2) == true) {
			sol1.set(true);
			sol2.set(false);
		} else {
			sol1.set(false);
			sol2.set(true);
		}
		if (xbox.getRawButton(1)) {
			comp.stop();
		}
		if (xbox.getRawButton(3)) {
			comp.start();
		}
	}

	public void testPeriodic() {

	}

	public void drive(driveMode dm) {
		if (dm == driveMode.tank) {
			if (Math.abs(xbox.getRawAxis(1)) > DEAD_ZONE) {
				tal1.set(ControlMode.PercentOutput, xbox.getRawAxis(1) / 2);
				tal2.set(ControlMode.PercentOutput, xbox.getRawAxis(1) / 2);
			} else {
				tal1.set(ControlMode.PercentOutput, 0);
				tal2.set(ControlMode.PercentOutput, 0);
			}
			if (Math.abs(xbox.getRawAxis(5)) > DEAD_ZONE) {
				tal0.set(ControlMode.PercentOutput, -xbox.getRawAxis(5) / 2);
				tal3.set(ControlMode.PercentOutput, -xbox.getRawAxis(5) / 2);
			} else {
				tal0.set(ControlMode.PercentOutput, 0);
				tal3.set(ControlMode.PercentOutput, 0);
			}
		} else if (dm == driveMode.vidya) {
			if (!(Math.abs(xbox.getRawAxis(3)) > DEAD_ZONE && Math.abs(xbox.getRawAxis(4)) > DEAD_ZONE)) {
				getTurnCos();
				if (Math.abs(xbox.getRawAxis(3)) > DEAD_ZONE) {
					tal0.set(ControlMode.PercentOutput, (-xbox.getRawAxis(3) / 2) + rturn_coefficient);
					tal1.set(ControlMode.PercentOutput, (xbox.getRawAxis(3) / 2) + lturn_coefficient);
					tal2.set(ControlMode.PercentOutput, (xbox.getRawAxis(3) / 2) + lturn_coefficient);
					tal3.set(ControlMode.PercentOutput, (-xbox.getRawAxis(3) / 2) + rturn_coefficient);
				} else if (Math.abs(xbox.getRawAxis(4)) > DEAD_ZONE) {
					tal0.set(ControlMode.PercentOutput, (xbox.getRawAxis(4) / 2) + rturn_coefficient);
					tal1.set(ControlMode.PercentOutput, (-xbox.getRawAxis(4) / 2) + lturn_coefficient);
					tal2.set(ControlMode.PercentOutput, (-xbox.getRawAxis(4) / 2) + lturn_coefficient);
					tal3.set(ControlMode.PercentOutput, (xbox.getRawAxis(4) / 2) + rturn_coefficient);
				}
			}
		}
	}

	public void getTurnCos() {
		if (Math.abs(xbox.getRawAxis(1)) > DEAD_ZONE) {
			if (xbox.getRawAxis(1) > 0) {
				lturn_coefficient = xbox.getRawAxis(1);
			} else if (xbox.getRawAxis(1) > 0) {
				rturn_coefficient = xbox.getRawAxis(1);
			}
		}
	}
}