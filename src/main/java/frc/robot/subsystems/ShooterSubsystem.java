package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
    TalonFX shooter = new TalonFX(Constants.SHOOTER_MOTOR_ID);
    SparkMax feeder = new SparkMax(Constants.FEEDER_MOTOR_ID, MotorType.kBrushless);
    
    SparkMaxConfig shooterConfig = new SparkMaxConfig();

    public ShooterSubsystem() {
        SmartDashboard.putNumber("Shooter Setpoint", 0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter velocity", getShooterEncoderVelocity());
        SmartDashboard.putNumber("Feeder velocity", getFeederEncoderVelocity());
    }

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

    public double MetersToRPS(double metersPerSecond) {
        return metersPerSecond / (Constants.SHOOTER_DIAMETER * Math.PI);
    }

    public double getGoalSpeed(double distance) {
        // Translation2d position = drivetrain.getRobotPosition();
        // double velocityInMeters = (distance * Math.sqrt(2*GRAVITAS))/(2* Math.cos(65) * Math.sqrt(distance * Math.tan(65) - HUB_HEIGHT));
        
        double vel = (distance * Math.sqrt(2*Constants.GRAVITAS))/(2*Math.cos(Constants.SHOOT_ANGLE_RADIANS) * Math.sqrt(distance * Math.tan(Constants.SHOOT_ANGLE_RADIANS) - Constants.HUB_HEIGHT));
        vel = MetersToRPS(vel) * Constants.SHOOTER_RPS_CORRECTION;

        return vel;
    }
}
