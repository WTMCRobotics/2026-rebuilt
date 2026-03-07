package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.IntakeSubsystemEnum;

public class SetIntake extends Command {
    IntakeSubsystem intakeSubsystem;
    IntakeSubsystemEnum intakeEnums;
    double invert = 0;

    public SetIntake(IntakeSubsystem intakeSubsystem, IntakeSubsystemEnum intakeEnums) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeEnums = intakeEnums;
    }

    public void initialize() {
        switch(intakeEnums) {
            case INTAKE_EXTEND:
                invert = -1;
                break;
            case INTAKE_RETRACT:
                invert = 1;
                break;
        }

        // intakeSubsystem.setRightExtender(-Constants.INTAKE_EXTENDER_SPEED * invert);
    }

    public void execute() {
        if(intakeSubsystem.getEncoderLeftPosition() < Constants.INTAKE_BUMP_MAX && intakeSubsystem.getEncoderLeftPosition() > Constants.INTAKE_BUMP_MIN) {
            intakeSubsystem.setLeftExtender(Constants.INTAKE_LEFT_OVER_BUMP_SPEED * invert);
            intakeSubsystem.setRightExtender(Constants.INTAKE_LEFT_OVER_BUMP_SPEED * invert);
        } else {
            intakeSubsystem.setLeftExtender(Constants.INTAKE_EXTENDER_SPEED * invert);
            intakeSubsystem.setRightExtender(Constants.INTAKE_EXTENDER_SPEED * invert);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopExtender();
    }
}
