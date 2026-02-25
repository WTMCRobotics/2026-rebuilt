package frc.robot.commands;

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

    public void initialize() {
        shooterSubsystem.setFeeder(Constants.FEEDER_MOTOR_FORCE);
    }

    public void execute() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopShooter();
        shooterSubsystem.setFeeder(0);
    }
}
