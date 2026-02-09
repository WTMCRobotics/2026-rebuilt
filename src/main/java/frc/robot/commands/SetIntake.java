package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystemEnum;

public class SetIntake extends Command {
    IntakeSubsystem intakeSubsystem;
    IntakeSubsystemEnum intakeEnums;

    public SetIntake(IntakeSubsystem intakeSubsystem, IntakeSubsystemEnum intakeEnums) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeEnums = intakeEnums;
    }

    public void initialize() {
        intakeSubsystem.setExtender(intakeEnums);
    }

    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return intakeSubsystem.extenderIsAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopExtender();
    }
}
