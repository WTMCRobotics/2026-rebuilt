package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

import frc.robot.Constants;

public class Climb extends Command {
    ClimbSubsystem climbSubsystem;
    boolean up;
    private final PIDController controllerLeft;
    private final PIDController controllerRight;


    public Climb(ClimbSubsystem climbSubsystem, boolean up) {
        this.climbSubsystem = climbSubsystem;
        this.up = up;
        
        if(up) {
            controllerLeft = new PIDController(
            Constants.CLIMB_ARM_UP_P,
            Constants.CLIMB_ARM_UP_I,
            Constants.CLIMB_ARM_UP_D
                );

            controllerRight = new PIDController(
            Constants.CLIMB_ARM_UP_P,
            Constants.CLIMB_ARM_UP_I,
            Constants.CLIMB_ARM_UP_D
                );
        } else {
            controllerLeft = new PIDController(
            Constants.CLIMB_ARM_DOWN_P,
            Constants.CLIMB_ARM_DOWN_I,
            Constants.CLIMB_ARM_DOWN_D
                );

            controllerRight = new PIDController(
            Constants.CLIMB_ARM_DOWN_P,
            Constants.CLIMB_ARM_DOWN_I,
            Constants.CLIMB_ARM_DOWN_D
                );
        }


    }
    

    public void initialize() {
        controllerLeft.setTolerance(Constants.CLIMB_ARM_TOLERANCE);
        controllerRight.setTolerance(Constants.CLIMB_ARM_TOLERANCE);
        if (up) {
            controllerLeft.setSetpoint(Constants.CLIMB_ARM_TARGET);
            controllerRight.setSetpoint(Constants.CLIMB_ARM_TARGET);
        } else {
            controllerLeft.setSetpoint(Constants.CLIMB_ARM_ZERO);
            controllerRight.setSetpoint(Constants.CLIMB_ARM_ZERO); 
        }

    }

    public void execute() {
        climbSubsystem.setClimbLeft(controllerLeft.calculate(climbSubsystem.getClimbLeftPosition()));
        climbSubsystem.setClimbRight(controllerRight.calculate(climbSubsystem.getClimbRightPosition()));

    }

    @Override
    public boolean isFinished() {
        return controllerLeft.atSetpoint() && controllerRight.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.setClimbLeft(0);
        climbSubsystem.setClimbRight(0);
    }
}
