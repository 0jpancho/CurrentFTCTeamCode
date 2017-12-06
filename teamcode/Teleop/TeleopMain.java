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

    DcMotor frontLeft, frontRight, backLeft, backRight, lift, relic, balStone;
    Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight, relicLeft, relicRight;

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

        relic = hardwareMap.dcMotor.get("relic");
        relicLeft = hardwareMap.servo.get("relicLeft");
        relicRight = hardwareMap.servo.get("relicRight");

        balStone = hardwareMap.dcMotor.get("balStone");

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        else if(gamepad1.y)
        {
            frontLeft.setPower(1);
            backRight.setPower(-1);
        }
        else if(gamepad1.a)
        {
            frontLeft.setPower(-1);
            backRight.setPower(1);
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
    //Controls the lift motor for the glyph manipulator
    if (gamepad2.left_stick_y < -0.25){
        lift.setPower(-liftSpeed);
    }

    else if (gamepad2.left_stick_y > 0.25) {
        lift.setPower(liftSpeed);
    }

    else {
        lift.setPower(0);
    }

    //Pinball control for glyph arms

    //Left glyph arm control
    if (gamepad2.left_bumper) {
        topLiftLeft.setPosition(0);
        bottomLiftLeft.setPosition(1);
    }

    else {
        topLiftLeft.setPosition(0.4);
        bottomLiftLeft.setPosition(0.6);
    }

    //Right glyph arm control
    if (gamepad2.right_bumper) {
        topLiftRight.setPosition(1);
        bottomLiftRight.setPosition(0);
    }

    else {
        topLiftRight.setPosition(0.6);
        bottomLiftRight.setPosition(0.4);
    }

    /*
    * After a a minute and thirty seconds, at end game, enable relic manipulator control.
    * Makes sure that the operator only has control of this manipulator when needed.
     */
    if (getRuntime() > 30) {

        if (gamepad2.right_stick_y < -0.25) {
            relic.setPower(0.15);
        }

        else if (gamepad2.right_stick_y > 0.25) {
            relic.setPower(-0.15);
        }

        else {
            relic.setPower(0);
        }

        if (gamepad2.a) {
            balStone.setPower(1);
        }

        else {
            balStone.setPower(0);
        }

        if (gamepad2.a) {
            relicLeft.setPosition(1);
            relicRight.setPosition(0);
        }
        else if (gamepad2.b) {
            relicLeft.setPosition(0);
            relicRight.setPosition(1);
        }

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
    telemetry.addData("Front Left Enc Counts", frontLeft.getCurrentPosition());
        telemetry.addData("Front Right Enc Counts", frontRight.getCurrentPosition());
        telemetry.addData("Back Left Enc Counts", backLeft.getCurrentPosition());
        telemetry.addData("Back Right Enc Counts", backRight.getCurrentPosition());
        telemetry.addData("Lift Enc Counts", lift.getCurrentPosition());
        telemetry.update();
}}
