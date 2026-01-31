package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class IntakeSubsystem implements Subsystem {
    SparkMax intake = new SparkMax(Constants.INTAKE_MOTOR_ID, MotorType.kBrushless);
    SparkMax intakeExtender = new SparkMax(Constants.INTAKE_EXTENDER_ID, MotorType.kBrushless);

    public void setExtender(double speed) {
        intakeExtender.set(speed);
    }

    public void setIntake(double speed) {
        intake.set(speed);
    }

    public double getEncoderPosition() {
        return intakeExtender.getEncoder().getPosition();
    }


    public void resetEncoder() {
        intakeExtender.getEncoder().setPosition(0);
    }

    public Boolean limitSwitchHit() {
        return intakeExtender.getReverseLimitSwitch().isPressed();
    }
}
