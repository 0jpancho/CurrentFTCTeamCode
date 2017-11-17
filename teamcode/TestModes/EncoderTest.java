package org.firstinspires.ftc.teamcode.TestModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by ShambotGold on 11/6/2017.
 */
@Autonomous(name = "Encoder Test", group = "Test Modes")
public class EncoderTest extends LinearOpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;

    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();



        while (getRuntime() >= 0 && getRuntime() <= 10 && opModeIsActive()) {

            frontLeft.setPower(0.25);
            frontRight.setPower(0.25);

            backLeft.setPower(0.25);
            backRight.setPower(0.25);

            frontLeft.setTargetPosition(2000);
            backLeft.setTargetPosition(2000);

            frontRight.setTargetPosition(-2000);
            backRight.setTargetPosition(-2000);

            telemetry.addData("Encoder Counts", frontLeft.getCurrentPosition());
            telemetry.update();
        }

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}
