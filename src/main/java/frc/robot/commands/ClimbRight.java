package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;

public class ClimbRight extends Command {
    ClimbSubsystem climbSubsystem;
    double speed;

    public ClimbRight(ClimbSubsystem climbSubsystem, double speed) {
        this.climbSubsystem = climbSubsystem;
        this.speed = speed;
    }

    public void initialize() {
        climbSubsystem.setClimbRight(speed);
    }

    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.setClimbRight(0);
    }
}
