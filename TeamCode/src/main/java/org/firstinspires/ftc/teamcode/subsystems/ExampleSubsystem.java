package org.firstinspires.ftc.teamcode.subsystems;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.util.TelemetryData;

public class ExampleSubsystem implements Subsystem {

    Robot robot;
    public ExampleSubsystem(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void periodic() {
        //robot.telemetry.addData("Servo Position", robot.exampleServo.get());
    }

    public void doMoveUp() {
        double x = robot.rampServo.get();
        robot.rampServo.set(x + .05);
    }

    public void doMoveDown() {
        double x = robot.rampServo.get();
        robot.rampServo.set(x - .05);
    }

    public void doIntakeIn() {
        robot.intakeMotor.set(1);
    }

    public void doIntakeOut() {
        robot.intakeMotor.set(-1);
    }

    public Command moveUp() {
        return new InstantCommand(this::doMoveUp);
    }

    public Command moveDown() {
        return new InstantCommand(this::doMoveDown);
    }

    public Command IntakeIn(){return new InstantCommand(this::doIntakeIn);}
}
