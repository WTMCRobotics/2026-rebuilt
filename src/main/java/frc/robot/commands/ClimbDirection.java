package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem.ClimbDirectionEnum;
import frc.robot.subsystems.ClimbSubsystem.ClimbSubsystem;

public class ClimbDirection extends Command {
    ClimbSubsystem climbSubsystem;
    ClimbDirectionEnum direction;
   
    public ClimbDirection(ClimbSubsystem climbSubsystem, ClimbDirectionEnum direction) {
        this.climbSubsystem = climbSubsystem;
        this.direction = direction;
    }
    
    public void initialize() {
    }

    public void execute() {
        climbSubsystem.setClimbAngler(direction);
    }

    @Override
    public boolean isFinished() {
        return climbSubsystem.anglerIsAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.stopAngler();
    }
}
