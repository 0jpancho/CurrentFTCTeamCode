package org.firstinspires.ftc.teamcode.TestModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Joseph Pancho on 12/5/2017.
 */
@Autonomous(name = "Forward Encoder Test", group = "Shit")
public class Drive_Forward_Test extends LinearOpMode {

    public DcMotor frontLeft, frontRight, backLeft, backRight;

    final float wheelCircumference = (float) Math.PI * 4;
    final float ppr = 1120;
    final public double autonDriveSpeed = 0.35;

    public final float inches = 12;

    public void runOpMode()
    {
        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        telemetry.addData("Current Function", "Start Driving Forward");
        idle();

        while (opModeIsActive()) {

            frontLeft.setPower(autonDriveSpeed);
            frontRight.setPower(autonDriveSpeed);

            backLeft.setPower(autonDriveSpeed);
            backRight.setPower(autonDriveSpeed);

            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            telemetry.addData("Front Left Enc Counts", frontLeft.getCurrentPosition());
            telemetry.addData("Front Right Enc Counts", frontRight.getCurrentPosition());
            telemetry.addData("Back Left Enc Counts", backLeft.getCurrentPosition());
            telemetry.addData("Back Right Enc Counts", backRight.getCurrentPosition());

            telemetry.addData("Current Function", "Drive Forward");
            telemetry.update();
            idle();
        }

        waitForStart();
    }
}
