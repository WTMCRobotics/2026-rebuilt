package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

import frc.robot.Constants;

public class Shoot extends Command {
    ShooterSubsystem shooterSubsystem;
    double rpm;

    public Shoot(ShooterSubsystem shooterSubsystem, double rpm) {
        this.shooterSubsystem = shooterSubsystem;
        this.rpm = rpm;
    }

    private final PIDController controller = new PIDController(
        Constants.SHOOTER_P,
        Constants.SHOOTER_I,
        Constants.SHOOTER_D
    );

    public void initialize() {
        controller.setTolerance(Constants.SHOOTER_TOLERANCE);
        controller.setSetpoint(rpm);
    }

    public void execute() {
        shooterSubsystem.setShooter(controller.calculate(shooterSubsystem.getEncoderVelocity()));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.setShooter(0);
    }
}
