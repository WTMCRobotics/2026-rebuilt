package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    MotorController shooter = new SparkMax(Constants.ShooterMotorID, MotorType.kBrushless);

    public void setShooter(double speed) {
        shooter.set(speed);
    }
}
