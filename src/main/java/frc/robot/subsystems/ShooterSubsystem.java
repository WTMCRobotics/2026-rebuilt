package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    SparkMax shooter = new SparkMax(Constants.SHOOTER_MOTOR_ID, MotorType.kBrushless);
    SparkMax feeder = new SparkMax(Constants.FEEDER_MOTOR_ID, MotorType.kBrushless);
    
    SparkMaxConfig shooterConfig = new SparkMaxConfig();

    SparkClosedLoopController loopConfig = shooter.getClosedLoopController();

    public void setShooterSpeed(double speed) {
        loopConfig.setSetpoint(speed, ControlType.kVelocity);

        shooterConfig.closedLoop
            .p(Constants.SHOOTER_P)
            .i(Constants.SHOOTER_I)
            .d(Constants.SHOOTER_D)
            .outputRange(-(Constants.SHOOTER_TOLERANCE/2), Constants.SHOOTER_TOLERANCE/2);
        
        shooter.configure(shooterConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
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

    public double getEncoderPosition() {
        return shooter.getEncoder().getPosition();
    }

    public double getEncoderVelocity() {
        return shooter.getEncoder().getVelocity();
    }

    public void resetEncoder() {
        shooter.getEncoder().setPosition(0);
    }

    public void setEncoderPosition(double position) {
        shooter.getEncoder().setPosition(position);
    }
}
