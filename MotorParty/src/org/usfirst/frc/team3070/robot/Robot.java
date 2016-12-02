
package org.usfirst.frc.team3070.robot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends IterativeRobot {
    CANTalon tal0, tal1, tal2, tal3;
    Joystick xbox;
    Compressor comp;
    Solenoid sol1, sol2, sol3, sol4, sol5, sol6;
    
    public final double DEAD_ZONE = .2;
    public final double RAMP_IT_UP = .5;
    public void robotInit() {
      tal0 = new CANTalon(0); //RIGHT
      tal1 = new CANTalon(1); //left
      tal2 = new CANTalon(2); //left
      tal3 = new CANTalon(3); //right
      xbox = new Joystick(2);
      comp = new Compressor(0);
      sol1 = new Solenoid(0);
      sol2 = new Solenoid(1);
      sol3 = new Solenoid(2);
      sol4 = new Solenoid(3);
      sol5 = new Solenoid(4);
      sol6 = new Solenoid(5);
      tal0.setVoltageRampRate(RAMP_IT_UP);
      tal1.setVoltageRampRate(RAMP_IT_UP);
      tal2.setVoltageRampRate(RAMP_IT_UP);
      tal3.setVoltageRampRate(RAMP_IT_UP);
      tal0.set(0);
      tal1.set(0);
      tal2.set(0);
      tal3.set(0);
      comp.start();
    }
 
    public void teleopPeriodic() {
    	drive();
    	if ( xbox.getRawButton(2) == true) {
    		sol1.set(true);
    		sol2.set(false);
    	} else {
    		sol1.set(false);
    		sol2.set(true);
    	}
    }
    public void testPeriodic() {
    
    }
    public void drive() {
    	if(Math.abs(xbox.getRawAxis(1)) > DEAD_ZONE) {
    		tal1.set(-xbox.getRawAxis(1));
    		tal2.set(-xbox.getRawAxis(1));
    	} else {
    		tal1.set(0);
    		tal2.set(0);
    	}
    	if(Math.abs(xbox.getRawAxis(5)) > DEAD_ZONE) {
    		tal0.set(xbox.getRawAxis(5));
    		tal3.set(xbox.getRawAxis(5));
    	} else {
    		tal0.set(0);
    		tal3.set(0);
    	}
    }
    
}

