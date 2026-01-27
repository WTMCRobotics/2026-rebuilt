package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

import frc.robot.Constants;

public class RetractIntake extends Command {
    IntakeSubsystem intakeSubsystem;
    double target;

    public RetractIntake(IntakeSubsystem intakeSubsystem, double target) {
        this.intakeSubsystem = intakeSubsystem;
        this.target = target;
    }

    private final PIDController controller = new PIDController(
       Constants.INTAKE_RETRACTOR_P,
        Constants.INTAKE_RETRACTOR_I,
        Constants.INTAKE_RETRACTOR_D
    );

    public void initialize() {
        controller.setTolerance(Constants.INTAKE_RETRACTOR_TOLERANCE);
        controller.setSetpoint(target);
    }

    public void execute() {
        intakeSubsystem.setExtender(controller.calculate(intakeSubsystem.getEncoderPosition()));
    }

    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setExtender(0);
    }
}
