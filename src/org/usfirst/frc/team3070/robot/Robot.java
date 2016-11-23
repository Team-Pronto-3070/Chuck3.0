
package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {

    static CANTalon leftOne,leftTwo,rightOne,rightTwo;
    static Solenoid shooter;
    static Compressor compressor;
    static Joystick joyLeft, joyRight;
    
    final static int LEFT_ONE = 0;
    final static int LEFT_TWO = 3;
    final static int RIGHT_ONE = 2;
    final static int RIGHT_TWO = 1;
    final static int LEFT_JOY = 0;
    final static int RIGHT_JOY = 1;
    final static int SHOOTER_P1 = 1;
    final static int SHOOTER_P2 = 0;
    
    final static double DEADZONE = 0.1;
    final static double DEFAULT_SPEED = 0.25;
    public void robotInit() {
       leftOne = new CANTalon(LEFT_ONE);
       leftTwo = new CANTalon(LEFT_TWO);
       rightOne = new CANTalon(RIGHT_ONE);
       rightTwo = new CANTalon(RIGHT_TWO);
       shooter = new Solenoid(SHOOTER_P1,SHOOTER_P2);
       joyLeft = new Joystick(LEFT_JOY);
       joyRight = new Joystick(RIGHT_JOY);
       compressor = new Compressor();
       
       compressor.stop();
       stopAll();
       shooter.set(true);
    }
    
    public void autonomousInit() {

    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
        if(joyRight.getTrigger()){
           shooter.set(false);
        } else {
           shooter.set(true);
        }
        
        //left drive
        if(Math.abs(joyLeft.getRawAxis(1)) > DEADZONE){
           setLeft(joyLeft.getRawAxis(1) /2);
        } else {
           setLeft(0);
        }
        
        //right drive
        if(Math.abs(joyRight.getRawAxis(1)) > DEADZONE){
           setRight(joyRight.getRawAxis(1) /2);
        } else {
           setRight(0);
        }
        
        checkCompressor();
    }
    
    public void testPeriodic() {
       
       checkCompressor();
    }
    
    //Sets left motors to x
    private static void setLeft(double x){
       leftOne.set(x);
       leftTwo.set(x);
    }
    
    //Sets right motors to x
    private static void setRight(double x){
       rightOne.set(-x);
       rightTwo.set(-x);
    }
    
    //Stops all motors
    private static void stopAll(){
       setLeft(0);
       setRight(0);
    }

    //Compressor set to keystrokes on leftJoy
    //Button 6 is start, button 7 is stop
    private static void checkCompressor(){
       if(joyLeft.getRawButton(6)){
          compressor.start();
       }
       if(joyLeft.getRawButton(7)){
          compressor.stop();
       }
    }
}
