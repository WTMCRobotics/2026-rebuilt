package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

import frc.robot.Constants;

public class ClimbDirection extends Command {
    ClimbSubsystem climbDirection;
    boolean up;
    private final PIDController controller;
   


    public ClimbDirection(ClimbSubsystem climbDirection, boolean up) {
        this.climbDirection = climbDirection;
        this.up = up;
        
        if(up) {
            controller = new PIDController(
            Constants.CLIMB_DIRECTION_UP_P,
            Constants.CLIMB_DIRECTION_UP_I,
            Constants.CLIMB_DIRECTION_UP_D
                );
        } else {

            controller = new PIDController(
            Constants.CLIMB_DIRECTION_DOWN_P,
            Constants.CLIMB_DIRECTION_DOWN_I,
            Constants.CLIMB_DIRECTION_DOWN_D
                );
        }
    }
    

    public void initialize() {
        controller.setTolerance(Constants.CLIMB_DIRECTION_TOLERANCE);
        if (up) {
            controller.setSetpoint(Constants.CLIMB_DIRECTION_TARGET);
        } else {
            controller.setSetpoint(Constants.CLIMB_DIRECTION_ZERO);
        }

    }

    public void execute() {
        climbDirection.setClimbLeft(controller.calculate(climbDirection.getClimbDirection()));

    }

    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        climbDirection.setClimbDirection(0.0);

    }
}
