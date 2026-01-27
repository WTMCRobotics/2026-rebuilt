package frc.robot.controller;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * An internal class for raw input bindings based on {@link CommandGenericHID}.
 * @see CommandGenericHID
 * @see Trigger
 */
public class GenericController {
    protected CommandGenericHID internalHID;
    public GenericController(int port) {
        this.internalHID = new CommandGenericHID(port);
    }

    /**
     * Provides a trigger linked to a button channel.
     * @param id Channel id of the requested button.
     * @return A {@link Trigger} corresponding to the state of the button.
     */
    protected Trigger button(int id) {
        return this.internalHID.button(id);
    }

    /**
     * Provides the value of an analog axis.
     * @param id Channel id of the requested axis.
     * @return A double corresponding to the axis' value.
     */
    protected double axis(int id) {
        return this.internalHID.getRawAxis(id);
    }

    /**
     * Get the direction of a dpad.
     * @param id Channel id of the requested dpad.
     * @return An int corresponding to the dpad's angle.
     */
    protected int dpad(int id) {
        return this.internalHID.getHID().getPOV(id);
    }
}
