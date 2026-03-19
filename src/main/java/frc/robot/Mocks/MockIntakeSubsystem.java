package frc.robot.Mocks;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.controller.GuitarController;
import frc.robot.Constants;

public class MockIntakeSubsystem {
    static double speed;

    public static void setIntake(double Speed) {
        speed = Speed;
    }

    public static double getIntake() {
        return speed;
    }
}
