package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation2d;
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

    public double getGoalSpeed(double distance, CommandSwerveDrivetrain drivetrain) { // TODO: Find the formula to convert motorspeed(RPM) to Meters per second && include air resistance calulations
        final double GRAVITAS = 9.80665;
        final double HUB_HEIGHT = 1.8288;
        double airResistance = 0;
        Translation2d position = drivetrain.getRobotPosition();
        double velocityInMeters = (distance * Math.sqrt(2*GRAVITAS))/(2* Math.cos(65) * Math.sqrt(distance * Math.tan(65) - HUB_HEIGHT));
        
        return 20;
    }
}
