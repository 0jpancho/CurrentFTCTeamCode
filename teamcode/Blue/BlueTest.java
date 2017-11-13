package org.firstinspires.ftc.teamcode.Blue;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Joseph on 11/12/2017.
 */
@Autonomous(name = "Blue Test", group = "Sctuff")
public class BlueTest extends LinearOpMode {

    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public Servo colorArm;
    public ColorSensor colorSensor;

    public BNO055IMU imu;
    public Orientation angles;

    double currentHeading;

    final float wheelCircumference = (float) Math.PI * 4;
    final float ppr = 1120;

    public final int DRIVE_FORWARD = 1;
    public final int DRIVE_BACKWARD = 2;
    public final int STRAFE_LEFT = 3;
    public final int STRAFE_RIGHT = 4;

    public final int HEADING = 1;
    public final int ROLL = 2;
    public final int PITCH = 3;

    public final int RED = 1;
    public final int BLUE = 2;

    public double headingOffset = 0;

    public void runOpMode() throws InterruptedException{

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        //colorArm = hardwareMap.servo.get("colorArm");
        //colorSensor = hardwareMap.colorSensor.get("colorSensor");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        headingOffset = getHeading();

        telemetry.addData("Current Function", "Initialize");
        telemetry.update();
        idle();

        waitForStart();

        //testDrive(0.5, 8);

        drive(8, DRIVE_FORWARD);
        //turnAngle(40, 0.25);
    }

    public void resetDriveEncoders() throws InterruptedException {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Current Function", "Reset Drive Encoders");
        telemetry.update();
        idle();
    }

    public double getAngle(int whichAngle) {
        //Function used to get values from the Rev Hub's internal IMU
        switch (whichAngle) {
            case HEADING:
                return AngleUnit.DEGREES.normalize(angles.firstAngle) - headingOffset;

            case ROLL:
                return AngleUnit.DEGREES.normalize(angles.secondAngle);

            case PITCH:
                return AngleUnit.DEGREES.normalize(angles.thirdAngle);

            default:
                return getAngle(HEADING);
        }
    }

    public double getHeading() {
        return getAngle(HEADING);
    }

    /*public void resetGyro() {
        currentHeading = getAngle(1);
    }
    */
    public void drive(float inches, int direction) throws InterruptedException {
        /*
        * Drive function that converts an input of inches into encoder
        * counts. Allows the motors to drive to a certain distance
         */
        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        resetDriveEncoders();
        telemetry.addData("Current Function", "Start Drive");
        telemetry.update();
        idle();

        while (direction == DRIVE_FORWARD && opModeIsActive()) {
            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            telemetry.addData("Current Function", "Drive Forward");
            telemetry.update();
            idle();
        }

        while (direction == DRIVE_BACKWARD && opModeIsActive()) {
            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            telemetry.addData("Current Function", "Drive Backward");
            telemetry.update();
            idle();
        }

        while (direction == STRAFE_LEFT && opModeIsActive()) {
            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            telemetry.addData("Current Function", "Strafe Left");
            telemetry.update();
            idle();
        }

        while (direction == STRAFE_RIGHT && opModeIsActive()) {
            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            telemetry.addData("Current Function", "Strafe Right");
            telemetry.update();
            idle();
        }

        telemetry.addData("Current Function", "Drive Successful");
        telemetry.update();
        idle();
    }

    // Function used to rotate to a specific angle in autonomous
    public void turnAngle(double angle, double power){

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Current Function", "Start Turn Angle");
        idle();

        //Rotate left
        while(getHeading() < angle && opModeIsActive()){
            frontLeft.setPower(-power);
            frontRight.setPower(-power);

            backLeft.setPower(-power);
            backRight.setPower(-power);

            telemetry.addData("Current Heading", getHeading());
            telemetry.addData("Current Function", "Rotate Left");
            telemetry.update();
            idle();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);

        //Rotate right
        while(getHeading() > angle && opModeIsActive()) {
            frontLeft.setPower(power);
            frontRight.setPower(power);

            backLeft.setPower(power);
            backRight.setPower(power);

            telemetry.addData("Current Heading", getHeading());
            telemetry.addData("Current Function", "Rotate Right");
            telemetry.update();
            idle();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);

        telemetry.update();
        idle();
    }

    //Sets position of color servo in autonomous
    public void setColorArm(int pos){
        colorArm.setPosition(pos);
    }

    /*
    * Function used in order to push particle in autonomous by
    * using our drive functions. Drive directions are assuming
    * the color sensor is mounted facing right.
     */
    public void moveOnColor(double power, int team, boolean ledEnable) throws InterruptedException{

        telemetry.addData("Current Function", "Start Move On Color");
        idle();

        colorSensor.enableLed(ledEnable);

        //If Red Alliance, red particle facing sensor, strafe opposite direction
        if (colorSensor.red() > colorSensor.blue() && team == RED && opModeIsActive()){
            drive(8, STRAFE_LEFT);
            telemetry.addData("Current Function", "Move on Color Strafe Left (RED)");
            idle();
        }

        //If Red Alliance, red particle not detected, strafe right
        else if (colorSensor.red() < colorSensor.blue() && team == RED && opModeIsActive()){
            drive(8, STRAFE_RIGHT);
            telemetry.addData("Current Function", "Move on Color Strafe Right (RED)");
            idle();
        }

        //If Blue Alliance, blue particle facing sensor, strafe opposite direction
        else if (colorSensor.blue() > colorSensor.red() && team == BLUE && opModeIsActive()){
            drive(8, STRAFE_LEFT);
            telemetry.addData("Current Function", "Move on Color Strafe Left (BLUE)");
            idle();
        }

        //If Blue Alliance, blue particle not detected, strafe right
        else if (colorSensor.blue() < colorSensor.red() && team == BLUE && opModeIsActive()){
            drive(8, STRAFE_RIGHT);
            telemetry.addData("Current Function", "Move on Color Strafe Right (BLUE)");
            idle();
        }

        colorSensor.enableLed(false);
        telemetry.addData("Current Function", "Move on Color Successful");
        telemetry.update();

        idle();
    }

    public void testDrive(double power, double time){

        while (getRuntime() > time && getRuntime() <= time & opModeIsActive()) {

            frontLeft.setPower(power);
            backLeft.setPower(power);

            frontRight.setPower(power);
            backRight.setPower(power);
            telemetry.addData("Current Function", "Test Drive Successful");
            telemetry.update();

            idle();
        }
    }
}
