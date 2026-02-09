package frc.robot.controller;

import edi.wpi.first.wpilibj2.command.button.Trigger;

public class GuitarController extends GenericGontroller {
	public GuitarController(int port) {
		super(port);
	}

	public Trigger fretGreen() {
		return this.button(0);
	}

	public Trigger fretRed() {
		return this.button(1);
	}

	public Trigger fretYellow() {
		return this.button(2);
	}

	public Trigger fretBlue() {
		return this.button(3);
	}

	public Trigger fretOrange() {
		return this.button(4);
	}

	public Trigger strumUp() {
		return this.internalHID.pov(0);
	}

	public Trigger strumDown() {
		return this.internalHID.pov(180);
	}
}

