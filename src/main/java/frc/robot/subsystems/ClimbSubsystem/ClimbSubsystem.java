package frc.robot.subsystems.ClimbSubsystem;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ClimbSubsystem implements Subsystem {
    SparkMax climbAngler = new SparkMax(Constants.CLIMB_DIRECTION_ID, MotorType.kBrushless);
    SparkMax climbRight = new SparkMax(Constants.CLIMB_RIGHT_ID, MotorType.kBrushless);
    SparkMax climbLeft = new SparkMax(Constants.CLIMB_LEFT_ID, MotorType.kBrushless);

    SparkMaxConfig climbConfig = new SparkMaxConfig();

    public ClimbSubsystem() {
    }

    public void setClimbAngler(double speed) {
        climbAngler.set(speed);
    }

    public void setClimb(ClimbDirectionEnum direction) {
        double P = 0;
        double I = 0;
        double D = 0;

        double goal = 0;

        switch(direction) {
            case CLIMB_DIRECTION_UP:
                P = Constants.CLIMB_ARM_UP_P;
                I = Constants.CLIMB_ARM_UP_I;
                D = Constants.CLIMB_ARM_UP_D;
                goal = Constants.CLIMB_ARM_TARGET;
                break;
            case CLIMB_DIRECTION_DOWN:
                P = Constants.CLIMB_ARM_DOWN_P;
                I = Constants.CLIMB_ARM_DOWN_I;
                D = Constants.CLIMB_ARM_DOWN_D;
                goal = Constants.CLIMB_ARM_ZERO;
                break;
        }

        climbConfig.closedLoop
            .p(P)
            .i(I)
            .d(D)
            .outputRange(-(Constants.CLIMB_ARM_TOLERANCE/2), Constants.CLIMB_ARM_TOLERANCE/2);
        
        climbLeft.configure(climbConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        climbRight.configure(climbConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        
        climbLeft.set(goal);
        climbRight.set(goal);
    }

    public void stopClimb() {
        climbLeft.stopMotor();
        climbRight.stopMotor();
    }

    public double getClimbAngler() {
        return climbAngler.getEncoder().getPosition();
    }

    public boolean isAtSetpoint() {
        return climbLeft.getClosedLoopController().isAtSetpoint() && climbRight.getClosedLoopController().isAtSetpoint();
    }

    public void resetClimbs() {
        climbRight.getEncoder().setPosition(0);
        climbLeft.getEncoder().setPosition(0);
    }

    public void resetDirection() {
        climbAngler.getEncoder().setPosition(0);
    }
}
