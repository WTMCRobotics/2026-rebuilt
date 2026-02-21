package frc.robot.subsystems.IntakeSubsystem;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
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
        double P = 0;
        double I = 0;
        double D = 0;

        double goal = 0;

        switch(direction) {
            case INTAKE_EXTEND:
                P = Constants.INTAKE_EXTENDER_P;
                I = Constants.INTAKE_EXTENDER_I;
                D = Constants.INTAKE_EXTENDER_D;
                goal = Constants.INTAKE_EXTENDER_TARGET;
                break;
            case INTAKE_RETRACT:
                P = Constants.INTAKE_RETRACTOR_P;
                I = Constants.INTAKE_RETRACTOR_I;
                D = Constants.INTAKE_RETRACTOR_D;
                goal = Constants.INTAKE_EXTENDER_ZERO;
                break;
        }
        intakeConfig.closedLoop
            .p(P)
            .i(I)
            .d(D)
            .outputRange(-(Constants.INTAKE_EXTENDER_TOLERANCE/2), Constants.INTAKE_EXTENDER_TOLERANCE/2);
        
        loopConfigLeft.setSetpoint(goal, ControlType.kPosition);
        loopConfigRight.setSetpoint(goal, ControlType.kPosition);

        intakeExtenderLeft.configure(intakeConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        intakeExtenderRight.configure(intakeConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    } 

    public boolean extenderIsAtSetpoint() {
        return intakeExtenderLeft.getClosedLoopController().isAtSetpoint() && intakeExtenderRight.getClosedLoopController().isAtSetpoint();
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
