package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants;
public class Shoot extends Command {
    ShooterSubsystem shooterSubsystem;
    CommandSwerveDrivetrain drivetrain;
    double rpm;

    double hubX = (DriverStation.getAlliance().get() == Alliance.Red) ? Constants.HUB_X_BLUE : Constants.HUB_X_RED;
    double hubY = Constants.HUB_Y;

    public Shoot(ShooterSubsystem shooterSubsystem, CommandSwerveDrivetrain drivetrain) {
        this.shooterSubsystem = shooterSubsystem;
        this.drivetrain = drivetrain;
    }

    public void initialize() {
    }

    public void execute() {
        double distance = drivetrain.getDistanceFrom(hubX, hubY);
        double goalSpeed = 0; // TODO

        shooterSubsystem.setShooterSpeed(goalSpeed);

        if(shooterSubsystem.getShooterEncoderVelocity() >= goalSpeed) {
            shooterSubsystem.setFeeder(Constants.FEEDER_MOTOR_FORCE);
        } else {
            shooterSubsystem.setFeeder(0);
        }
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
