package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
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

    private final PIDController controller = new PIDController(
        Constants.SHOOTER_P,
        Constants.SHOOTER_I,
        Constants.SHOOTER_D
    );

    public Shoot(ShooterSubsystem shooterSubsystem, CommandSwerveDrivetrain drivetrain) {
        this.shooterSubsystem = shooterSubsystem;
        this.drivetrain = drivetrain;
    }

    public void initialize() {
        controller.setTolerance(Constants.SHOOTER_TOLERANCE);
        controller.setSetpoint(rpm);
    }

    public double getGoalSpeed(double distance) { // TODO: Find the formula to convert motorspeed(RPM) to Meters per second && include air resistance calulations
        final double GRAVITAS = 9.80665;
        final double HUB_HEIGHT = 1.8288;
        double airResistance = 0;
        Translation2d position = drivetrain.getRobotPosition();
        double velocityInMeters = (distance * Math.sqrt(2*GRAVITAS))/(2* Math.cos(65) * Math.sqrt(distance * Math.tan(65) - HUB_HEIGHT));
        
        return 20;
    }

    public void execute() {
        shooterSubsystem.setShooter(controller.calculate(shooterSubsystem.getShooterEncoderVelocity()));
        if(controller.atSetpoint()){
            shooterSubsystem.setFeeder(Constants.FEEDER_MOTOR_FORCE);
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
