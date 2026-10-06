package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.ExampleSubsystem;

@TeleOp
public class DrivingSample extends LinearOpMode {

    static final boolean FIELD_CENTRIC = false;

    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot(hardwareMap, telemetry);
        ExampleSubsystem exampleSubsystem = new ExampleSubsystem(robot);

        // the extended gamepad object
        GamepadEx driverOp = new GamepadEx(gamepad1);

        driverOp.getGamepadButton(GamepadKeys.Button.A)
                .and(driverOp.getGamepadButton(GamepadKeys.Button.B).negate())
                .whenActive(exampleSubsystem.moveDown());
        driverOp.getGamepadButton(GamepadKeys.Button.B)
                .and(driverOp.getGamepadButton(GamepadKeys.Button.A).negate())
                .whenActive(exampleSubsystem.moveUp());
                // .whenActive(new InstantCommand(() -> exampleSubsystem.doMoveUp()));

        waitForStart();

        while (!isStopRequested()) {

            CommandScheduler.getInstance().run();

            robot.drive.arcadeDrive(
                    driverOp.getLeftY(),
                    driverOp.getLeftX(),
                    false
            );

            //   if (!FIELD_CENTRIC) {
            //       // optional fourth parameter for squared inputs
            //       robot.drive.driveRobotCentric(
            //               driverOp.getLeftX(),
            //               driverOp.getLeftY(),
            //               driverOp.getRightX(),
            //               false
            //       );
            //   } else {
            //       // optional fifth parameter for squared inputs
            //       robot.drive.driveFieldCentric(
            //               driverOp.getLeftX(),
            //               driverOp.getLeftY(),
            //               driverOp.getRightX(),
            //               robot.imu.getRotation2d().getDegrees(),   // gyro value passed in here must be in degrees
            //               false
            //       );
            //   }
            }
        }

}
