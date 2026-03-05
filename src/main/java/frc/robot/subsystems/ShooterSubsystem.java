package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    TalonFX shooter = new TalonFX(Constants.SHOOTER_MOTOR_ID);
    SparkMax feeder = new SparkMax(Constants.FEEDER_MOTOR_ID, MotorType.kBrushless);
    
    SparkMaxConfig shooterConfig = new SparkMaxConfig();

    public void setShooter(double speed) {
        shooter.set(speed);
    }

    public void stopShooter(){
        shooter.stopMotor();
    }

    public void setFeeder(double speed) {
        feeder.set(speed);
    }

    public double getFeeder() {
        return feeder.get();
    }

    public double getShooter() {
        return shooter.get();
    }

    public double getEncoderPosition() {
        return shooter.getPosition().getValueAsDouble();
    }

    public double getShooterEncoderVelocity() {
        return shooter.getVelocity().getValueAsDouble();
    }

    public double getFeederEncoderVelocity() {
        return feeder.getEncoder().getVelocity();
    }

    public void resetEncoder() {
        shooter.setPosition(0);
    }

    public void setEncoderPosition(double position) {
        shooter.setPosition(position);
    }
}
