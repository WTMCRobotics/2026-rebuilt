package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class ResetIntake extends Command{

    IntakeSubsystem intakeSubsystem;

    public ResetIntake(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    public void initialize() {
        intakeSubsystem.setExtender(Constants.INTAKE_EXTENDER_RETRACT);
    }

    public void execute() {

    }
    
    public boolean isFinished() {
        return intakeSubsystem.limitSwitchHit();
    }

    public void end(boolean interrupted) {
        intakeSubsystem.resetEncoder();
        intakeSubsystem.setExtender(0);
    }
}
