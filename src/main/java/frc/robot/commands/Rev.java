package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants;

public class Rev extends Command {
    ShooterSubsystem shooterSubsystem;
    CommandSwerveDrivetrain drivetrain;

    double hubX = (DriverStation.getAlliance().get() == Alliance.Red) ? Constants.HUB_X_BLUE : Constants.HUB_X_RED;
    double hubY = Constants.HUB_Y;

    private final PIDController shooterController = new PIDController(
        Constants.SHOOTER_P,
        Constants.SHOOTER_I,
        Constants.SHOOTER_D
    );


    public Rev (ShooterSubsystem shooterSubsystem,CommandSwerveDrivetrain drivetrain) {
        this.shooterSubsystem = shooterSubsystem;
        this.drivetrain=drivetrain;
    }

    public void initialize() {
        shooterController.setTolerance(Constants.SHOOTER_TOLERANCE);
    }

    public void execute() {
        // shooterSubsystem.setShooter(Constants.SHOOTER_SPEED);
        // shooterSubsystem.setFeeder(Constants.FEEDER_MOTOR_FORCE);

        shooterController.setSetpoint(shooterSubsystem.getGoalSpeed(drivetrain.getDistanceFrom(hubX, hubY)));
        shooterSubsystem.setShooter(shooterController.calculate(shooterSubsystem.getShooterEncoderVelocity()));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopShooter();
    }
}
