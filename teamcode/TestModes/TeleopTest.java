package org.firstinspires.ftc.teamcode.TestModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Joseph Pancho on 10/30/2017.
 */
@TeleOp (name = "Teleop Test", group = "Test Modes")
public class TeleopTest extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;

    double x;
    double y;

    @Override
    public void init() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
    }

    @Override
    public void loop() {

        if (gamepad1.left_stick_y > 0.25) {
            frontLeft.setPower(1);
            backLeft.setPower(1);

            frontRight.setPower(-1);
            backRight.setPower(-1);

            telemetry.addLine("Forward");
        }

        /*backward*/
        else if (gamepad1.left_stick_y < -0.25) {
            frontLeft.setPower(-1);
            backLeft.setPower(-1);

            frontRight.setPower(1);
            backRight.setPower(1);

            telemetry.addData("Thing", 2);
        }

        /*dead zone*/
        else if (gamepad1.left_stick_y < 0.25 && gamepad1.left_stick_y > -0.25 &&
                gamepad1.left_stick_x < 0.25 && gamepad1.left_stick_x > -0.25) {
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            telemetry.addData("Thing", 10);
        }

    }
}
