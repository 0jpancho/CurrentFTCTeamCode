package org.firstinspires.ftc.teamcode.Teleop;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Joseph Pancho on 10/18/2017.
 */
@TeleOp(name = "Main Teleop", group = "Sctuff")
public class TeleopMain extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight, lift;
    Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight;

    AHRS navX;
    private final int NAVX_DIM_I2C_PORT = 0;

    final double driveSpeed = 1;
    final double turnSpeed = 0.5;
    final double strafeSpeed = 1;

    final double liftSpeed = 0.5;

    boolean servoState = false;

    @Override
    public void init(){
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        navX = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);

        lift = hardwareMap.dcMotor.get("lift");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");

        topLiftRight = hardwareMap.servo.get("topLiftRight");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");
    }

    @Override
    public void loop(){

        telemetry.addData("Heading", navX.getYaw());

        /*forward*/
        if (gamepad1.left_stick_y < -0.25) {
            frontLeft.setPower(-driveSpeed);
            backLeft.setPower(-driveSpeed);

            frontRight.setPower(driveSpeed);
            backRight.setPower(driveSpeed);

            telemetry.addData("Drive Direction", "Forward");
        }

        /*backward*/
        else if (gamepad1.left_stick_y > 0.25) {
            frontLeft.setPower(driveSpeed);
            backLeft.setPower(driveSpeed);

            frontRight.setPower(-driveSpeed);
            backRight.setPower(-driveSpeed);

            telemetry.addData("Drive Direction", "Backward");
        }

        /*strafe left*/
        else if (gamepad1.left_stick_x < -0.25) {
            frontLeft.setPower(strafeSpeed);
            frontRight.setPower(strafeSpeed);

            backLeft.setPower(-strafeSpeed);
            backRight.setPower(-strafeSpeed);

            telemetry.addData("Drive Direction", "Strafe Left");
        }

        /*strafe right*/
        else if (gamepad1.left_stick_x > 0.25) {
            frontLeft.setPower(-strafeSpeed);
            frontRight.setPower(-strafeSpeed);

            backLeft.setPower(strafeSpeed);
            backRight.setPower(strafeSpeed);

            telemetry.addData("Drive Direction", "Strafe Right");
        }
        /*turn right*/
        else if (gamepad1.b) {
            frontLeft.setPower(-turnSpeed);
            frontRight.setPower(-turnSpeed);

            backLeft.setPower(-turnSpeed);
            backRight.setPower(-turnSpeed);

            telemetry.addData("Drive Direction", "Rotate Right");
        }
        /*turn left*/
        else if (gamepad1.x)
        {
            frontLeft.setPower(turnSpeed);
            frontRight.setPower(turnSpeed);

            backLeft.setPower(turnSpeed);
            backRight.setPower(turnSpeed);

            telemetry.addData("Drive Direction", "Rotate Left");
        }
        /*dead zone*/
        else if (gamepad1.left_stick_y < 0.25 && gamepad1.left_stick_y > -0.25 &&
                gamepad1.left_stick_x < 0.25 && gamepad1.left_stick_x > -0.25) {

            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            telemetry.addData("Drive Direction", "Stop Motors (Deadzone)");
        }

    if (gamepad2.right_stick_y < -0.25){
        lift.setPower(-liftSpeed);
    }

    else if (gamepad2.right_stick_y > 0.25) {
        lift.setPower(liftSpeed);
    }

    else {
        lift.setPower(0);
    }
    /*
    if (gamepad2.right_bumper  && servoState == false) {

        servoState = true;

        if  (topLiftLeft.getPosition() == 0  || bottomLiftLeft.getPosition() == 1
                || topLiftRight.getPosition() == 1 || bottomLiftRight.getPosition() == 0) {
            topLiftLeft.setPosition(0.4);
            bottomLiftLeft.setPosition(0.6);

            topLiftRight.setPosition(0.6);
            bottomLiftRight.setPosition(0.4);
        }

        else if (topLiftLeft.getPosition() == 0.4 || bottomLiftLeft.getPosition() == 0.6
                || topLiftRight.getPosition() == 0.6 || bottomLiftRight.getPosition() == 0.4) {
            topLiftLeft.setPosition(0);
            bottomLiftLeft.setPosition(1);

            topLiftRight.setPosition(1);
            bottomLiftRight.setPosition(0);
        }
    }

    else if (!gamepad2.right_bumper) {
        servoState = false;
    }
    */
    /*
    if (gamepad2.left_bumper) {
        topLiftLeft.setPosition(0.4);
        bottomLiftLeft.setPosition(0.6);

        topLiftRight.setPosition(0.6);
        bottomLiftRight.setPosition(0.4);
    }

    if (gamepad2.right_bumper) {
        topLiftLeft.setPosition(0);
        bottomLiftLeft.setPosition(1);

        topLiftRight.setPosition(1);
        bottomLiftRight.setPosition(0);
    }
    */

    //Left Side
    if (gamepad2.left_bumper) {
        topLiftLeft.setPosition(0);
        bottomLiftLeft.setPosition(1);
    }

    else {
        topLiftLeft.setPosition(0.4);
        bottomLiftLeft.setPosition(0.6);
    }

    if (gamepad2.right_bumper) {
        topLiftRight.setPosition(1);
        bottomLiftRight.setPosition(0);
    }

    else {
        topLiftRight.setPosition(0.6);
        bottomLiftRight.setPosition(0.4);
    }
    telemetry.addData("Servo State", servoState);
    telemetry.addData("Gamepad 2 Right Bumper", gamepad2.right_bumper);

        telemetry.update();
}}
