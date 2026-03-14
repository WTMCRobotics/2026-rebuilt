// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Optional;
import java.util.Set;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import frc.robot.controller.XboxController;
import frc.robot.controller.GuitarController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;

import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystemEnum;
import frc.robot.commands.Shoot;
import frc.robot.commands.Intake;
import frc.robot.commands.ResetIntake;
import frc.robot.commands.SetIntake;
import frc.robot.commands.ClimbLeft;
import frc.robot.commands.ClimbRight;
import frc.robot.commands.ClimbDirection;
import frc.robot.commands.Rev;

public class RobotContainer {

    private final Field2d m_field = new Field2d();
    private Mechanism2d m_mech = new Mechanism2d(3,3);

    public XboxController controller = new XboxController(0);
    public GuitarController coDriverController = new GuitarController(1);

    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final Pose3d poseA = new Pose3d();

    

    private final SendableChooser<Command> autoChooser;

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain;

    private ShooterSubsystem shooter;
    private IntakeSubsystem intake;
    private ClimbSubsystem climb;


    public RobotContainer() {
        shooter = new ShooterSubsystem();
        intake = new IntakeSubsystem();
        
        drivetrain = TunerConstants.createDrivetrain();

        NamedCommands.registerCommand("Climb", new DeferredCommand(() -> new SequentialCommandGroup(
                drivetrain.pathFindToPose(getClimbSpot()),
                new ParallelDeadlineGroup(new WaitCommand(1.0), new ClimbDirection(climb, 0.2)),
                new ParallelDeadlineGroup(new WaitCommand(1.0), new ClimbLeft(climb, 0.2), new ClimbRight(climb, -0.2))
            ), Set.of(drivetrain)));

        NamedCommands.registerCommand("Intake", new Intake(intake, Constants.INTAKE_SPEED));
        NamedCommands.registerCommand("Deploy Intake",new ParallelDeadlineGroup(new WaitCommand(1.5),new SetIntake(intake, IntakeSubsystemEnum.INTAKE_EXTEND)));
        NamedCommands.registerCommand("Retract Intake",new SetIntake(intake, IntakeSubsystemEnum.INTAKE_RETRACT));

        NamedCommands.registerCommand("Shoot", new Shoot(shooter, drivetrain, coDriverController));
        NamedCommands.registerCommand("Rev", new Rev(shooter, drivetrain));

        configureBindings();
        
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        SmartDashboard.putData("Field", m_field);
        SmartDashboard.putData("Mech", m_mech);
        SmartDashboard.putData("Reset Intake", new ResetIntake(intake));



        Logger.recordOutput("MyPose", poseA);
    }
    public int updateFieldOdometry() {
        return 1;
    }

    private double getRotationRate() {
        if (!controller.x().getAsBoolean()) {
            return -joystick.getRightX() * MaxAngularRate;
        }

        double hubX = drivetrain.getHubX();
        double hubY = Constants.HUB_Y;

        double targetRotation = drivetrain.getAngleTowards(hubX, hubY).getRadians();
        double currentRotation = drivetrain.getRotation3d().getZ();
        double relativeRotation = currentRotation - targetRotation;
        double motorPower = Math.sqrt(Math.abs(relativeRotation)) * -Math.signum(relativeRotation) * Constants.HUB_ALIGN_POWER_COEF;

        return motorPower;
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-Math.pow(joystick.getLeftY() * MaxSpeed, 2) * Math.signum(joystick.getLeftY())) // Drive forward with negative Y (forward)
                    .withVelocityY(-Math.pow(joystick.getLeftX() * MaxSpeed, 2) * Math.signum(joystick.getLeftX())) // Drive left with negative X (left)
                    .withRotationalRate(getRotationRate()) // Drive counterclockwise with negative X (left)
            )
        );
        
        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        coDriverController.fretGreen().and(coDriverController.highFretboard()).whileTrue(new Shoot(shooter, drivetrain, coDriverController));
        coDriverController.fretRed().and(coDriverController.highFretboard()).whileTrue(new Shoot(shooter, drivetrain, coDriverController));
        coDriverController.fretYellow().and(coDriverController.highFretboard()).whileTrue(new Intake(intake, Constants.INTAKE_SPEED));
        coDriverController.fretBlue().and(coDriverController.highFretboard()).whileTrue(new Intake(intake, -Constants.INTAKE_SPEED));

        coDriverController.strumUp().whileTrue(new SetIntake(intake, IntakeSubsystemEnum.INTAKE_EXTEND));
        coDriverController.strumDown().whileTrue(new SetIntake(intake, IntakeSubsystemEnum.INTAKE_RETRACT));

        coDriverController.strumLeft().whileTrue(new ClimbDirection(climb, 0.25));
        coDriverController.strumRight().whileTrue(new ClimbDirection(climb, -0.25));
        coDriverController.fretGreen().and(coDriverController.lowFretboard()).whileTrue(new ClimbLeft(climb, 0.5));
        coDriverController.fretRed().and(coDriverController.lowFretboard()).whileTrue(new ClimbLeft(climb, -0.5));
        coDriverController.fretYellow().and(coDriverController.lowFretboard()).whileTrue(new ClimbRight(climb, -0.5));
        coDriverController.fretBlue().and(coDriverController.lowFretboard()).whileTrue(new ClimbRight(climb, 0.5));


        // Orchestra
        
        SmartDashboard.putString("song", "");
        SmartDashboard.putData(
            "Play Song",
            Commands.runOnce(
                () -> {
                    drivetrain.loadSong(SmartDashboard.getString("song", null));
                    System.out.println("Playing Music 1");
                    // drivetrain.getSqOrchestra().stop();
                    drivetrain.getSqOrchestra().play();
                    System.out.println("Playing Music 2");
                },
                drivetrain
            )
        );
        
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public Pose2d getClimbSpot() {
        Pose2d goalSpot = new Pose2d(new Translation2d(0,0), new Rotation2d(0,0));
    Optional<Alliance> optionalAlliance = DriverStation.getAlliance();
        if(optionalAlliance.isPresent()) {
           if(optionalAlliance.get().toString().equals("Red")) {
                goalSpot = new Pose2d(new Translation2d(0,0), new Rotation2d(0,0));
           } else if(optionalAlliance.get().toString().equals("Blue")) {
                goalSpot = new Pose2d(new Translation2d(0,0), new Rotation2d(0,0));
           }
        }

        return goalSpot;
    }

}
