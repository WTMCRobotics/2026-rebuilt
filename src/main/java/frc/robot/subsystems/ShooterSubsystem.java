package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    SparkMax shooter = new SparkMax(Constants.ShooterMotorID, MotorType.kBrushless);

    public void setShooter(double speed) {
        shooter.set(speed);
    }

    public double getEncoder() {
        return shooter.getEncoder().getPosition();
    }

    public void resetEncoder() {
        shooter.getEncoder().setPosition(0);
    }
}
