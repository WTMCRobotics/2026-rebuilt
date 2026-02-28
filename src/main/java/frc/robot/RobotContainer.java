// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;

// import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
// import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import frc.robot.controller.XboxController;
import frc.robot.controller.GuitarController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;

import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ClimbSubsystem.ClimbDirectionEnum;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystemEnum;
import frc.robot.commands.Shoot;
import frc.robot.commands.Intake;
import frc.robot.commands.SetIntake;
import frc.robot.commands.Climb;
import frc.robot.commands.ClimbDirection;
import frc.robot.commands.ResetIntake;

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
        // shooter = new ShooterSubsystem();
        // intake = new IntakeSubsystem();
        
        drivetrain = TunerConstants.createDrivetrain();

        NamedCommands.registerCommand("Climb Up", new Climb(climb, ClimbDirectionEnum.CLIMB_DIRECTION_UP));
        NamedCommands.registerCommand("Climb Down", new Climb(climb, ClimbDirectionEnum.CLIMB_DIRECTION_DOWN));
        NamedCommands.registerCommand("Intake", new Intake(intake, Constants.INTAKE_SPEED));
        NamedCommands.registerCommand("Shoot", new Shoot(shooter, drivetrain));
        NamedCommands.registerCommand("Deploy Intake", new SetIntake(intake, IntakeSubsystemEnum.INTAKE_EXTEND));

        configureBindings();
        
        SmartDashboard.putData("Reset intake thingamajig", new ResetIntake(intake));
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        SmartDashboard.putData("Field", m_field);
        SmartDashboard.putData("Mech", m_mech);

        System.out.println("ROBOT CONTAINER");

        Logger.recordOutput("MyPose", poseA);
    }
    public int updateFieldOdometry() {
        return 1;
    }

    private double getRotationRate() {
        if (!coDriverController.fretGreen().getAsBoolean()) {
            return -joystick.getRightX() * MaxAngularRate;
        }

        double hubX = (DriverStation.getAlliance().get() == Alliance.Red) ? Constants.HUB_X_BLUE : Constants.HUB_X_RED;
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
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
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

        coDriverController.fretGreen().whileTrue(new Shoot(shooter, drivetrain));
        coDriverController.fretRed().whileTrue(new Intake(intake, Constants.INTAKE_SPEED));
        coDriverController.strumUp().onTrue(new SetIntake(intake, IntakeSubsystemEnum.INTAKE_EXTEND));
        coDriverController.strumDown().onTrue(new SetIntake(intake, IntakeSubsystemEnum.INTAKE_RETRACT));
        coDriverController.strumLeft().onTrue(new Climb(climb, ClimbDirectionEnum.CLIMB_DIRECTION_UP));
        coDriverController.strumRight().onTrue(new Climb(climb, ClimbDirectionEnum.CLIMB_DIRECTION_UP));
        coDriverController.startButton().onTrue(new ClimbDirection(climb, ClimbDirectionEnum.CLIMB_DIRECTION_UP));
        coDriverController.backButton().onTrue(new ClimbDirection(climb, ClimbDirectionEnum.CLIMB_DIRECTION_DOWN));
        

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
