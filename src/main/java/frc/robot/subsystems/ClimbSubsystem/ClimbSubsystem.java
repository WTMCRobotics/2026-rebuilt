package frc.robot.subsystems.ClimbSubsystem;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;


import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ClimbSubsystem implements Subsystem {
    SparkMax climbAngler = new SparkMax(Constants.CLIMB_DIRECTION_ID, MotorType.kBrushless);
    SparkMax climbRight = new SparkMax(Constants.CLIMB_RIGHT_ID, MotorType.kBrushless);
    SparkMax climbLeft = new SparkMax(Constants.CLIMB_LEFT_ID, MotorType.kBrushless);
    
    public ClimbSubsystem() {
    }

    public void setClimbAngler(double speed) {
        climbAngler.set(speed);
    }

    public void setClimbLeft(double speed) {
        climbLeft.set(speed);
    }
    
    public void setClimbRight(double speed) {
        climbRight.set(speed);
    }
}
