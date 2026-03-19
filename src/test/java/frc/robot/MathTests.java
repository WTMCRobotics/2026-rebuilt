package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.Mocks.MockShooterSubsystem;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;


@TestInstance(Lifecycle.PER_CLASS)
public class MathTests {
    static double DELTA = 1e-2;

    CommandSwerveDrivetrain drivetrain;

    ShooterSubsystem shooterSubsystem;


    @BeforeAll
    void setup() {
        // shooterSubsystem = new ShooterSubsystem();

    }

    @Test
    void rotationRateTest() {
        double motorPower = 0;


        final double L = 1.7;
        final double K = 3.1;

        // double hubX = drivetrain.getHubX();
        // double hubY = Constants.HUB_Y;

        double targetRotation = 3.0; // drivetrain.getAngleTowards(hubX, hubY).getRadians();
        double currentRotation = 4.0; // -drivetrain.getRotation3d().getZ();
        double relativeRotation = currentRotation - targetRotation;
        motorPower = L * Math.copySign(Math.pow(Math.abs(relativeRotation), 1 / K), relativeRotation); // * Constants.HUB_ALIGN_POWER_COEF;

        // SmartDashboard.putNumber("L", L);
        // SmartDashboard.putNumber("K", K);
        // SmartDashboard.putNumber("HubX", hubX);
        // SmartDashboard.putNumber("HubY", hubY);
        // SmartDashboard.putNumber("targetrotation", targetRotation);
        // SmartDashboard.putNumber("currentRotation", currentRotation);
        SmartDashboard.putNumber("relativerotation", relativeRotation);

        assertEquals(1.7, motorPower);
    }
    @Test
    void metersPerSecondtoRPS() {
       
        assertEquals(Math.floor(1503.8262/60 * 1000) / 1000, Math.floor(MockShooterSubsystem.MetersToRPS(10) * 1000) / 1000);
    }

    @Test
    void shootVelocityTest() {
        double targetVel = 834.72;

        double b = -2 * (Math.sin(Constants.SHOOT_ANGLE_RADIANS) * Math.cos(Constants.SHOOT_ANGLE_RADIANS)) / Constants.GRAVITAS;
        double c = 2 * Math.pow(Math.cos(Constants.SHOOT_ANGLE_RADIANS), 2) * Constants.HUB_HEIGHT / Constants.GRAVITAS;
        double targetVelSquared = Math.pow(targetVel, 2);

        b *= targetVelSquared;
        c *= targetVelSquared;

        double distance = (-b + Math.sqrt(Math.pow(b, 2) - 4 * c)) / 2;


        assertEquals(targetVel, MockShooterSubsystem.getGoalSpeed(distance) * (Constants.SHOOTER_DIAMETER * Math.PI) / Constants.SHOOTER_RPS_CORRECTION);
    }
    
}
