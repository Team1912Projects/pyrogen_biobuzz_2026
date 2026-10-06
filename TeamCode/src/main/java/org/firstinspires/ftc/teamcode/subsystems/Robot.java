package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.drivebase.DifferentialDrive;
import com.seattlesolvers.solverslib.hardware.RevIMU;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Robot {
    public Telemetry telemetry;
    public ServoEx exampleServo;
    public DifferentialDrive drive;
    //public MecanumDrive drive;
    public RevIMU imu;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        exampleServo = new ServoEx(hardwareMap, "servo_name");

        Motor fl, fr, bl, br;
        fl = new Motor(hardwareMap, "frontLeft");
        fr = new Motor(hardwareMap, "frontRight");
        // bl = new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435);
        // br = new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435);
        // drive = new MecanumDrive(fl, fr, bl, br);
        drive = new DifferentialDrive(fl, fr);

        RevIMU imu = new RevIMU(hardwareMap);
        imu.init();
    }
}
