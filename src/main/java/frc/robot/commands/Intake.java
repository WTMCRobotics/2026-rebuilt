package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;

public class Intake extends Command {
    IntakeSubsystem intakeSubsystem;
    double speed;

    public Intake(IntakeSubsystem intakeSubsystem, double speed) {
        this.intakeSubsystem = intakeSubsystem;
        this.speed = speed;
    }

    public void initialize() {
        intakeSubsystem.setIntake(Constants.INTAKE_SPEED);
    }

    public void execute() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setIntake(0);
    }
}
