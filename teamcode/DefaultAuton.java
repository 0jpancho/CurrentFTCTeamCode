package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by ShambotGold on 11/4/2017.
 */
@Autonomous(name = "Default Auton", group = "Auton")
public class DefaultAuton extends LinearOpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;

    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");

        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        waitForStart();

        while (getRuntime() >= 0 && getRuntime() <= 2 && opModeIsActive()) {

            frontLeft.setPower(-0.7);
            backLeft.setPower(-0.7);

            frontRight.setPower(0.7);
            backRight.setPower(0.7);
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);
    }
}
