package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

import frc.robot.Constants;

public class Shoot extends Command {
    ShooterSubsystem shooterSubsystem;
    double rpm;
    double waitFor;

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
        waitFor = Constants.SHOOTER_TIME_BEFORE_SHOOT + Timer.getFPGATimestamp();
    }

    public void execute() {
        shooterSubsystem.setShooter(controller.calculate(shooterSubsystem.getEncoderVelocity()));
        if(Timer.getFPGATimestamp() >= waitFor){
            shooterSubsystem.setFeeder(Constants.FEEDER_MOTOR_FORCE);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.setShooter(0);
        shooterSubsystem.setFeeder(0);
    }
}
