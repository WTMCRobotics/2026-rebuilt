package frc.robot;

import com.pathplanner.lib.path.PathConstraints;

public class Constants {
    // Earth
    public static final double GRAVITAS = 9.80665;

    // Shooter
    public static final int SHOOTER_MOTOR_ID = 19;

    public static final double RPMLEEVERTHINGYMAJIG_MIN = 1.0;
    public static final double RPMLEEVERTHINGYMAJIG_MAX = 0.4;
    public static final double TIME_TO_REV = 1.0;

    public static final double SHOOTER_P = 0.0001;
    public static final double SHOOTER_I = 0.1;
    public static final double SHOOTER_D = 0.0;

    public static final double SHOOT_ANGLE_RADIANS = Math.toRadians(65);
    public static final double SHOOTER_RPS_CORRECTION = 0.05;
    public static final double AUTON_SHOOTER_SPEED = 0.6;

    public static final double SHOOTER_DIAMETER = 5.0 / 39.37; 
    public static final double SHOOTER_TOLERANCE = 3.0; 
    
    // Feeder
    public static final int FEEDER_MOTOR_ID = 8;
    public static final double FEEDER_SPEED = -0.5;

    // Intake
    public static final int INTAKE_MOTOR_ID = 52;
    public static final int INTAKE_EXTENDER_LEFT_ID = 50;
    public static final int INTAKE_EXTENDER_RIGHT_ID = 51;
    
    public static final double INTAKE_SPEED = -0.6;
    public static final double INTAKE_EXTENDER_SPEED = 0.4;
    public static final double INTAKE_LEFT_OVER_BUMP_SPEED = 1.0;

    public static final double INTAKE_BUMP_MAX = 57.8;
    public static final double INTAKE_BUMP_MIN = 9.5;
    
    // Climb
    public static final int CLIMB_DIRECTION_ID = 60;
    public static final int CLIMB_RIGHT_ID = 61;
    public static final int CLIMB_LEFT_ID = 62;

    public static final double CLIMB_SPEED = 0.5;
    public static final double CLIMB_ANGLE_SPEED = 0.25;

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
    
    public static final double CAMERA1_Y_ROTATION_RADIANS = Math.toRadians(4);
    public static final double CAMERA1_X_ROTATION_RADIANS = 0.0;
    public static final double CAMERA1_Z_ROTATION_RADIANS = Math.PI;
    
    public static final double CAMERA2_X_COMPONENT_METERS = 0.0; // Front is + x
    public static final double CAMERA2_Y_COMPONENT_METERS = 0.0; // Up is + z
    public static final double CAMERA2_Z_COMPONENT_METERS = 0.0; // Left is + y
    
    public static final double CAMERA2_Y_ROTATION_RADIANS = 0.0;
    public static final double CAMERA2_X_ROTATION_RADIANS = 0.0;
    public static final double CAMERA2_Z_ROTATION_RADIANS = 0.0;

    // Field Data
    public static final double FIELD_X_METERS = 16.5405;
    public static final double FIELD_Y_METERS = 8.0696;

    public static final double HUB_X_RED = FIELD_X_METERS - 4.0284;
    public static final double HUB_X_BLUE = 4.0284;
    public static final double HUB_Y = FIELD_Y_METERS / 2;

    public static final double HUB_HEIGHT = 1.8288;

    // Pathfinding
    public static final PathConstraints PATH_CONSTRAINTS = new PathConstraints(1.75, 2, Math.PI * 1.5, Math.PI * 2.5);
    public static final double HUB_ALIGN_POWER_COEF = 1.2;
    public static final double HUB_ALIGN_HEIGHT = 0.5;
    public static final double HUB_ALIGN_DISTANCE = 0.34;

}
