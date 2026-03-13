package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;

public class ClimbLeft extends Command {
    ClimbSubsystem climbSubsystem;
    double speed;

    public ClimbLeft(ClimbSubsystem climbSubsystem, double speed) {
        this.climbSubsystem = climbSubsystem;
        this.speed = speed;
    }

    public void initialize() {
        climbSubsystem.setClimbLeft(speed);
    }

    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.setClimbLeft(0);
    }
}
