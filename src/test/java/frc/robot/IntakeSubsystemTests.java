package frc.robot;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import frc.robot.commands.SetIntake;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem.*;


@TestInstance(Lifecycle.PER_CLASS)
public class IntakeSubsystemTests {
    static double DELTA = 1e-2;

    IntakeSubsystem intakeSubsystem;


    @BeforeAll
    void setup() {
        intakeSubsystem = new IntakeSubsystem();
    }


    @Test
    void testSetIntake() {
        intakeSubsystem.setIntake(5.0);
        assertEquals(5.0, intakeSubsystem.getIntake(), DELTA);
    }

    @Test
    void testResetLeftEncoder() {
        intakeSubsystem.setEncoderPosition(12.0);
        intakeSubsystem.resetEncoder();
        assertEquals(0.0, intakeSubsystem.getEncoderLeftPosition(), DELTA);
    }

    @Test
    void testResetRightEncoder() {
        intakeSubsystem.setEncoderPosition(12.0);
        intakeSubsystem.resetEncoder();
        assertEquals(0.0, intakeSubsystem.getEncoderRightPosition(), DELTA);
    }
    @Test
    // add mocking of the limit switch
    void testlimitSwitchHit() {
        assertFalse(intakeSubsystem.limitSwitchHit());
    }
}
