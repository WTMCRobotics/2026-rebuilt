package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.controller.GuitarController;
import frc.robot.Constants;

public class Shoot extends Command {
    ShooterSubsystem shooterSubsystem;
    CommandSwerveDrivetrain drivetrain;
    GuitarController guitar;
    double rpm;

    double hubX = (DriverStation.getAlliance().get() == Alliance.Red) ? Constants.HUB_X_BLUE : Constants.HUB_X_RED;
    double hubY = Constants.HUB_Y;

    private final PIDController shooterController = new PIDController(
        Constants.SHOOTER_P,
        Constants.SHOOTER_I,
        Constants.SHOOTER_D
    );

    private final PIDController feederController = new PIDController(
        Constants.FEEDER_P,
        Constants.FEEDER_I,
        Constants.FEEDER_D
    );

    public Shoot(ShooterSubsystem shooterSubsystem, CommandSwerveDrivetrain drivetrain, GuitarController guitar) {
        this.shooterSubsystem = shooterSubsystem;
        this.drivetrain = drivetrain;
        this.guitar = guitar;
    }

    public void initialize() {
        shooterController.setTolerance(Constants.SHOOTER_TOLERANCE);
        feederController.setTolerance(Constants.FEEDER_TOLERANCE);
        feederController.setSetpoint(Constants.FEEDER_SPEED_RPM);
    }

    public double map(double fromMin, double fromMax, double toMin, double toMax, double value) {
        double fromDelta = fromMax - fromMin;
        double toDelta = toMax - toMin;
        
        return ((value - fromMin) / fromDelta) * toDelta + toMin;
    }

    public void execute() {
        // shooterSubsystem.setShooter(Constants.SHOOTER_SPEED);
        // shooterSubsystem.setFeeder(Constants.FEEDER_SPEED);
        
        double setpoint;
        if (guitar.fretRed().getAsBoolean()) {
            setpoint = -map(
                0.1, 
                0.9, 
                Constants.RPMLEEVERTHINGYMAJIG_MIN,
                Constants.RPMLEEVERTHINGYMAJIG_MAX,
                guitar.getLeverAxis());
        } else { 
            setpoint = shooterSubsystem.getGoalSpeed(drivetrain.getDistanceFrom(hubX, hubY));
        }

        double shooterCalculate = -shooterController.calculate(shooterSubsystem.getShooterEncoderVelocity(), setpoint);
        SmartDashboard.putNumber("Shooter calculation", shooterCalculate);

        shooterSubsystem.setShooter(shooterCalculate);

        if ((shooterSubsystem.getShooterEncoderVelocity() == shooterController.getSetpoint())) {
            shooterSubsystem.setFeeder(feederController.calculate(-shooterSubsystem.getFeederEncoderVelocity()));
        } else {
            shooterSubsystem.setFeeder(0.0);
        }

        SmartDashboard.putNumber("Shooter Setpoint", shooterController.getSetpoint());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopShooter();
        shooterSubsystem.setFeeder(0);
        shooterController.setSetpoint(0);
    }
}
