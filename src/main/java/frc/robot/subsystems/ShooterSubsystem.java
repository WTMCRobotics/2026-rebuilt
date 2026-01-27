package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    SparkMax shooter = new SparkMax(Constants.SHOOTER_MOTOR_ID, MotorType.kBrushless);

    public void setShooter(double speed) {
        shooter.set(speed);
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
}
