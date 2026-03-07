package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;

public class ResetIntake extends Command {
    IntakeSubsystem intakeSubsystem;
    double speed;

    public ResetIntake(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    public void initialize() {
        intakeSubsystem.resetLeftEncoder();
        intakeSubsystem.resetRightEncoder();
    }

    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
