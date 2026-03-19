package frc.robot.Mocks;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MockShooterSubsystem extends SubsystemBase {
    
    public static double speed;
    
    public static void setShooter(double Speed) {
        speed = Speed;
    }

    public static double getShooter() {
        return speed;
    }

    public static double MetersToRPS(double metersPerSecond) {
        return metersPerSecond / (Constants.SHOOTER_DIAMETER * Math.PI);
    }

    public static double getGoalSpeed(double distance) {
        // Translation2d position = drivetrain.getRobotPosition();
        // double velocityInMeters = (distance * Math.sqrt(2*GRAVITAS))/(2* Math.cos(65) * Math.sqrt(distance * Math.tan(65) - HUB_HEIGHT));
        
        double vel = (distance * Math.sqrt(2*Constants.GRAVITAS))/(2*Math.cos(Constants.SHOOT_ANGLE_RADIANS) * Math.sqrt(distance * Math.tan(Constants.SHOOT_ANGLE_RADIANS) - Constants.HUB_HEIGHT));
        vel = MetersToRPS(vel) * Constants.SHOOTER_RPS_CORRECTION;

        return vel;
    }
}
