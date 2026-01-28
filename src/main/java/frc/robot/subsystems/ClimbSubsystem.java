package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ClimbSubsystem implements Subsystem {
    SparkMax climbDirection = new SparkMax(Constants.CLIMB_DIRECTION_ID, MotorType.kBrushless);
    SparkMax climbRight = new SparkMax(Constants.CLIMB_RIGHT_ID, MotorType.kBrushless);
    SparkMax climbLeft = new SparkMax(Constants.CLIMB_LEFT_ID, MotorType.kBrushless);

    public void setClimbDirection(double speed) {
        climbDirection.set(speed);
    }

    public void setClimbRight(double speed) {
        climbRight.set(speed);
    }

    public void setClimbLeft(double speed) {
        climbLeft.set(speed);
    }

    public double getClimbDirection() {
        return climbDirection.getEncoder().getPosition();
    }

    public double getClimbLeftPosition() {
        return climbLeft.getEncoder().getPosition();
    }

    public double getClimbRightPosition() {
        return climbRight.getEncoder().getPosition();
    }

    public void resetClimbs() {
        climbRight.getEncoder().setPosition(0);
        climbLeft.getEncoder().setPosition(0);
    }

    public void resetDirection() {
        climbDirection.getEncoder().setPosition(0);
    }
}
