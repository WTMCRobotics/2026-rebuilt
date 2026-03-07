package frc.robot;

import com.pathplanner.lib.path.PathConstraints;

public class Constants {
    // Shooter
    public static final int SHOOTER_MOTOR_ID = 19;
    public static final double SHOOTER_SPEED = -0.75;

    public static final double SHOOTER_P = 0.02;
    public static final double SHOOTER_I = 0.0;
    public static final double SHOOTER_D = 0.0;
    public static final double SHOOTER_TOLERANCE = 0.4;
    
    // Feeder
    public static final int FEEDER_MOTOR_ID = 8;
    public static final double FEEDER_SPEED_RPM = 200;

    public static final double FEEDER_P = 0.02;
    public static final double FEEDER_I = 0.0;
    public static final double FEEDER_D = 0.0;
    public static final double FEEDER_TOLERANCE = 0.4;

    // Intake
    public static final int INTAKE_MOTOR_ID = 52;
    public static final int INTAKE_EXTENDER_LEFT_ID = 50;
    public static final int INTAKE_EXTENDER_RIGHT_ID = 51;
    
    public static final double INTAKE_SPEED = -0.75;
    public static final double INTAKE_EXTENDER_SPEED = 0.4;
    public static final double INTAKE_LEFT_OVER_BUMP_SPEED = 1.0;

    public static final double INTAKE_BUMP_MAX = 57.8;
    public static final double INTAKE_BUMP_MIN = 9.5;
    
    // Climb
    public static final int CLIMB_DIRECTION_ID = 60;
    public static final int CLIMB_RIGHT_ID = 61;
    public static final int CLIMB_LEFT_ID = 62;

    public static final double CLIMB_DIRECTION_UP_P = 0.02;
    public static final double CLIMB_DIRECTION_UP_I = 0.0;
    public static final double CLIMB_DIRECTION_UP_D = 0.0;
    public static final double CLIMB_DIRECTION_TOLERANCE = 0.4;
    
    public static final double CLIMB_DIRECTION_DOWN_P = 0.02;
    public static final double CLIMB_DIRECTION_DOWN_I = 0.0;
    public static final double CLIMB_DIRECTION_DOWN_D = 0.0;

    public static final double CLIMB_DIRECTION_TARGET = 0.3;
    public static final double CLIMB_DIRECTION_ZERO = 0.0;


    public static final double CLIMB_ARM_UP_P = 0.02;
    public static final double CLIMB_ARM_UP_I = 0.0;
    public static final double CLIMB_ARM_UP_D = 0.0;

    public static final double CLIMB_ARM_DOWN_P = 0.02;
    public static final double CLIMB_ARM_DOWN_I = 0.0;
    public static final double CLIMB_ARM_DOWN_D = 0.0;

    public static final double CLIMB_ARM_TOLERANCE = 0.4;
    public static final double CLIMB_ARM_TARGET = 0.0;
    public static final double CLIMB_ARM_ZERO = 0.0;

    // Vision
        // All measured from robot center
    public static final double CAMERA1_X_COMPONENT_METERS = 0.0; // Front is + x
    public static final double CAMERA1_Y_COMPONENT_METERS = 0.0; // Up is + z
    public static final double CAMERA1_Z_COMPONENT_METERS = 0.4445; // Left is + y
    
    public static final double CAMERA1_Y_ROTATION_RADIANS = 0.2 * Math.PI;
    public static final double CAMERA1_X_ROTATION_RADIANS = 0.0;
    public static final double CAMERA1_Z_ROTATION_RADIANS = Math.PI;
    
    public static final double CAMERA2_X_COMPONENT_METERS = 0.0; // Front is + x
    public static final double CAMERA2_Y_COMPONENT_METERS = 0.0; // Up is + z
    public static final double CAMERA2_Z_COMPONENT_METERS = 0.0; // Left is + y
    
    public static final double CAMERA2_Y_ROTATION_RADIANS = 0.0;
    public static final double CAMERA2_X_ROTATION_RADIANS = 0.0;
    public static final double CAMERA2_Z_ROTATION_RADIANS = 0.0;

    // Field Data
    public static final double FIELD_X_INCHES = 651.2;
    public static final double FIELD_Y_INCHES = 317.7;

    public static final double HUB_X_RED = 158.6;
    public static final double HUB_X_BLUE = FIELD_X_INCHES - 158.6;
    public static final double HUB_Y = FIELD_Y_INCHES / 2;

    // Pathfinding
    public static final PathConstraints PATH_CONSTRAINTS = new PathConstraints(1.75, 2, Math.PI * 1.5, Math.PI * 2.5);
    public static final double HUB_ALIGN_POWER_COEF = 1.14;

}
