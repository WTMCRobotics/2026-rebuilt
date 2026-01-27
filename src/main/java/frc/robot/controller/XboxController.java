package frc.robot.controller;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxController extends GenericController {
    private double leftPow = 1.0;
    private double rightPow = 1.0;

    public XboxController(int port) {
        super(port);
    }

    /**
     * Set the curvature of the left stick.
     * @param power The exponent to raise it to.
     * @return The modified {@link XboxController}
     */
    public XboxController leftPower(double power) {
        this.leftPow = power;
        return this;
    }

    /**
     * Set the curvature of the right stick.
     * @param power The exponent to raise it to.
     * @return The modified {@link XboxController}
     */
    public XboxController rightPower(double power) {
        this.rightPow = power;
        return this;
    }

    public double getLeftXAxis() {
        return Math.copySign(Math.pow(Math.abs(this.axis(0)), this.leftPow), this.axis(0));
    }

    public double getLeftYAxis() {
        return Math.copySign(Math.pow(Math.abs(this.axis(1)), this.leftPow), this.axis(1));
    }

    public double getRightXAxis() {
        return Math.copySign(Math.pow(Math.abs(this.axis(4)), this.rightPow), this.axis(4));
    }

    public double getRightYAxis() {
        return Math.copySign(Math.pow(Math.abs(this.axis(5)), this.rightPow), this.axis(5));
    }


    public double getLeftTriggerAxis() {
        return this.axis(2);
    }

    public double getRightTriggerAxis() {
        return this.axis(3);
    }


    public Trigger a() {
        return this.button(0);
    }

    public Trigger b() {
        return this.button(1);
    }

    public Trigger y() {
        return this.button(2);
    }

    public Trigger x() {
        return this.button(3);
    }


    public Trigger leftBumper() {
        return this.button(4);
    }

    public Trigger rightBumper() {
        return this.button(5);
    }


    public Trigger dpadUp() {
        return this.internalHID.pov(0);
    }

    public Trigger dpadRight() {
        return this.internalHID.pov(90);
    }

    public Trigger dpadDown() {
        return this.internalHID.pov(180);
    }

    public Trigger dpadLeft() {
        return this.internalHID.pov(270);
    }
}
