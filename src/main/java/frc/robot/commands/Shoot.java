package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import pabeles.concurrency.IntOperatorTask.Max;
import frc.robot.controller.GuitarController;
import frc.robot.Constants;

public class Shoot extends Command {
    ShooterSubsystem shooterSubsystem;
    CommandSwerveDrivetrain drivetrain;
    GuitarController guitar;
    double rpm;

    double hubX = 0; //(DriverStation.getAlliance().get() == Alliance.Red) ? Constants.HUB_X_RED : Constants.HUB_X_BLUE;
    double hubY = Constants.HUB_Y;

    double startTime;

    public Shoot(ShooterSubsystem shooterSubsystem, CommandSwerveDrivetrain drivetrain, GuitarController guitar) {
        this.shooterSubsystem = shooterSubsystem;
        this.drivetrain = drivetrain;
        this.guitar = guitar;
    }

    public void initialize() {
        startTime = Timer.getFPGATimestamp();

        hubX = drivetrain.getHubX();
    }

    public double map(double fromMin, double fromMax, double toMin, double toMax, double value) {
        double fromDelta = fromMax - fromMin;
        double toDelta = toMax - toMin;
        
        return ((value - fromMin) / fromDelta) * toDelta + toMin;
    }

    public void execute() {
        double hubDistance = drivetrain.getDistanceFrom(hubX, hubY);
        double goal_speed;
        
        if (DriverStation.isAutonomous()) {
            goal_speed = Constants.AUTON_SHOOTER_SPEED;
        } else {
            if (guitar.fretRed().getAsBoolean()) {
            goal_speed = map(
                    0.1, 
                0.9, 
                    Constants.RPMLEEVERTHINGYMAJIG_MIN,
                Constants.RPMLEEVERTHINGYMAJIG_MAX,
                    guitar.getLeverAxis());
            } else { 
                goal_speed = shooterSubsystem.getGoalSpeed(hubDistance);
            }
        }

        SmartDashboard.putNumber("Shooter Setpoint", goal_speed);
        SmartDashboard.putNumber("Hub Distance", hubDistance);
        SmartDashboard.putNumber("Shooter motor voltage", shooterSubsystem.getShooterMotorVoltage());
        SmartDashboard.putNumber("Hub X", hubX);
        SmartDashboard.putNumber("Hub Y", hubY);
        SmartDashboard.putString("Aliance", DriverStation.getAlliance().get().toString());

        shooterSubsystem.setShooter(goal_speed);
        if (Math.max(0, Timer.getFPGATimestamp() - startTime - Constants.TIME_TO_REV) > 0) {
            shooterSubsystem.setFeeder(Constants.FEEDER_SPEED);
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
