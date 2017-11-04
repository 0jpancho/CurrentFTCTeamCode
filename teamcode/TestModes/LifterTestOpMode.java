package org.firstinspires.ftc.teamcode.TestModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Joseph on 11/1/2017.
 */
@TeleOp(name = "Lifter Test", group = "Test Modes")
public class LifterTestOpMode extends OpMode {

    DcMotor lift;
    Servo topLiftLeft, topLiftRight, bottomLiftLeft, bottomLiftRight;

    final double liftSpeed = 0.5;

    boolean servoState = false;

    public void init() {

        lift = hardwareMap.dcMotor.get("lift");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        topLiftRight = hardwareMap.servo.get("topLiftRight");

        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");
    }

    public void loop() {

        if (gamepad2.right_stick_y < -0.25){
            lift.setPower(liftSpeed);
        }

        else if (gamepad2.right_stick_y > 0.25) {
            lift.setPower(-liftSpeed);
        }

        else {
            lift.setPower(0);
        }

        if (gamepad2.right_bumper  && servoState == false) {

            servoState = true;

            if  (topLiftLeft.getPosition() == 0) {
                topLiftLeft.setPosition(1);
                bottomLiftLeft.setPosition(0);

                topLiftRight.setPosition(0);
                bottomLiftRight.setPosition(1);
            }

            else if (topLiftLeft.getPosition() == 1) {
                topLiftLeft.setPosition(0);
                bottomLiftLeft.setPosition(1);

                topLiftRight.setPosition(0);
                bottomLiftRight.setPosition(1);
            }
        }

        else if (!gamepad2.right_bumper) {
            servoState = false;
        }
    }
}