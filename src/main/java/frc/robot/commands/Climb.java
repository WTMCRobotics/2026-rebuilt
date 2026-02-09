package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem.ClimbDirectionEnum;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;

public class Climb extends Command {
    ClimbSubsystem climbSubsystem;
    ClimbDirectionEnum direction;

    public Climb(ClimbSubsystem climbSubsystem, ClimbDirectionEnum direction) {
        this.climbSubsystem = climbSubsystem;
        this.direction = direction;
    }

    public void initialize() {
        climbSubsystem.setClimb(direction);
    }

    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return climbSubsystem.climbIsAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.stopClimb();
    }
}
