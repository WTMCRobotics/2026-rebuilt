package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystemEnum;

public class ResetIntake extends Command{

    IntakeSubsystem intakeSubsystem;

    public ResetIntake(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    public void initialize() {
        intakeSubsystem.setExtender(IntakeSubsystemEnum.INTAKE_RETRACT);
    }

    public void execute() {

    }
    
    public boolean isFinished() {
        return intakeSubsystem.limitSwitchHit();
    }

    public void end(boolean interrupted) {
        intakeSubsystem.resetEncoder();
        intakeSubsystem.stopExtender();
    }
}
