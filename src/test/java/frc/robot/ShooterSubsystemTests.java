package frc.robot;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import frc.robot.subsystems.ShooterSubsystem;

@TestInstance(Lifecycle.PER_CLASS)
public class ShooterSubsystemTests {
    static double DELTA = 1e-2;

    ShooterSubsystem shooterSubsystem;

    @BeforeAll
    void setup() {
        shooterSubsystem = new ShooterSubsystem();
    }

    @Test
    void testSetShooterSpeed() {
        shooterSubsystem.setShooterSpeed(100.0);
        assertEquals(100.0, shooterSubsystem.getEncoderVelocity(), DELTA);
    }

    @Test
    void testSetFeeder() {
        shooterSubsystem.setFeeder(0.9);
        assertEquals(0.9, shooterSubsystem.getFeeder(), DELTA);
    }

    @Test
    void testResetEncoder() {
        shooterSubsystem.setEncoderPosition(12.0);
        shooterSubsystem.resetEncoder();
        assertEquals(0.0, shooterSubsystem.getEncoderPosition(), DELTA);
    }

    @Test
    void testStopShooter() {
        shooterSubsystem.setShooterSpeed(50.0);
        shooterSubsystem.stopShooter();
        assertEquals(0.0, shooterSubsystem.getEncoderVelocity(), DELTA);
    }
}
