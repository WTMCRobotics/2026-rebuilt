package frc.robot.subsystems.IntakeSubsystem;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class IntakeSubsystem implements Subsystem {
    SparkMax intake = new SparkMax(Constants.INTAKE_MOTOR_ID, MotorType.kBrushless);
    SparkMax intakeExtenderLeft = new SparkMax(Constants.INTAKE_EXTENDER_LEFT_ID, MotorType.kBrushless);
    SparkMax intakeExtenderRight = new SparkMax(Constants.INTAKE_EXTENDER_RIGHT_ID, MotorType.kBrushless);

    SparkMaxConfig intakeConfig = new SparkMaxConfig();

    SparkClosedLoopController loopConfigLeft = intakeExtenderLeft.getClosedLoopController(); 
    SparkClosedLoopController loopConfigRight = intakeExtenderRight.getClosedLoopController(); 

    public void setIntake(double speed) {
        intake.set(speed);
    }
    
    public void setExtender(IntakeSubsystemEnum direction) {
        double invert = 0;

        switch(direction) {
            case INTAKE_EXTEND:
                invert = -1;
                break;
            case INTAKE_RETRACT:
                invert = 1;
                break;
        }

        intakeExtenderLeft.set(Constants.INTAKE_EXTENDER_SPEED * invert);
        intakeExtenderRight.set(-Constants.INTAKE_EXTENDER_SPEED * invert);
    } 

    public boolean extenderIsAtSetpoint() {
        return false; // TODO: limit switch!!!
    }

    public void resetEncoder() {
        intakeExtenderLeft.getEncoder().setPosition(0);
        intakeExtenderRight.getEncoder().setPosition(0);
    }

    public void setEncoderPosition(double position) {
        intakeExtenderLeft.getEncoder().setPosition(position);
        intakeExtenderRight.getEncoder().setPosition(position);
    }

    public void stopExtender() {
        intakeExtenderLeft.stopMotor();
        intakeExtenderRight.stopMotor();
    }

    public Boolean limitSwitchHit() {
       return intakeExtenderLeft.getReverseLimitSwitch().isPressed() && intakeExtenderRight.getReverseLimitSwitch().isPressed();
    }

    public double getIntake() {
        return intake.getEncoder().getVelocity();
    }

    public double getEncoderLeftPosition() {
        return intakeExtenderLeft.getEncoder().getPosition();
    }

    public double getEncoderRightPosition() {
        return intakeExtenderRight.getEncoder().getPosition();
    }
}
