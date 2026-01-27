package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

import frc.robot.Constants;

public class Climb extends Command {
    IntakeSubsystem intakeSubsystem;
    double speed;

    public Climb(IntakeSubsystem intakeSubsystem, double speed) {
        this.intakeSubsystem = intakeSubsystem;
        this.speed = speed;
    }

    public void initialize() {
        intakeSubsystem.setIntake(0);
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
