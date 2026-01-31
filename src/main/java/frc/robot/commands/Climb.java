package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem.ClimbDirectionEnum;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;

public class Climb extends Command {
    ClimbSubsystem climbSubsystem;
    ClimbDirectionEnum direction;

    public Climb(ClimbDirectionEnum direction) {
        this.direction = direction;
    }

    public void initialize() {
        climbSubsystem.setClimb(direction);
    }

    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return climbSubsystem.isAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.stopClimb();
    }
}
