package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Joseph Pancho on 10/18/2017.
 */

public class Robot {

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

    public LinearOpMode l;
    Telemetry realTelemetry;

    public double headingOffset = 0;

    /*
    * Function that allows us to create multiple autonomous modes without
    * having to redeclare hardware maps or variables. Allows us to have
    * autonomous functions on standby depending on what the autonomous requires
    * Credit to FTC Team 5998 for structure concept
     */
    public void initialize(LinearOpMode Input, HardwareMap hardwareMap, Telemetry telemetry) {
        l = Input;
        realTelemetry = telemetry;

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

        realTelemetry.addData("Current Function", "Initialize");
        realTelemetry.update();
        l.idle();
    }

    public void resetDriveEncoders() throws InterruptedException {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        l.idle();

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        realTelemetry.addData("Current Function", "Reset Drive Encoders");
        realTelemetry.update();
        l.idle();
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
        realTelemetry.addData("Current Function", "Start Drive");
        l.idle();

        if (direction == DRIVE_FORWARD && l.opModeIsActive()) {
            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            realTelemetry.addData("Current Function", "Drive Forward");
            l.idle();
        }

        else if (direction == DRIVE_BACKWARD && l.opModeIsActive()) {
            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            realTelemetry.addData("Current Function", "Drive Backward");
            l.idle();
        }

        else if (direction == STRAFE_LEFT && l.opModeIsActive()) {
            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            realTelemetry.addData("Current Function", "Strafe Left");
            l.idle();
        }

        else if (direction == STRAFE_RIGHT && l.opModeIsActive()) {
            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            realTelemetry.addData("Current Function", "Strafe Right");
            l.idle();
        }

        realTelemetry.addData("Current Function", "Drive Successful");
        realTelemetry.update();
        l.idle();
    }

    // Function used to rotate to a specific angle in autonomous
    public void turnAngle(double angle, double power){

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        realTelemetry.addData("Current Function", "Start Turn Angle");
        l.idle();

        //Rotate left
        while(getHeading() < angle && l.opModeIsActive()){
            frontLeft.setPower(-power);
            frontRight.setPower(-power);

            backLeft.setPower(-power);
            backRight.setPower(-power);

            realTelemetry.addData("Current Heading", getHeading());
            realTelemetry.addData("Current Function", "Rotate Left");
            realTelemetry.update();
            l.idle();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);

        //Rotate right
        while(getHeading() > angle && l.opModeIsActive()) {
            frontLeft.setPower(power);
            frontRight.setPower(power);

            backLeft.setPower(power);
            backRight.setPower(power);

            realTelemetry.addData("Current Heading", getHeading());
            realTelemetry.addData("Current Function", "Rotate Right");
            realTelemetry.update();
            l.idle();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);

        realTelemetry.update();
        l.idle();
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

        realTelemetry.addData("Current Function", "Start Move On Color");
        l.idle();

        colorSensor.enableLed(ledEnable);

        //If Red Alliance, red particle facing sensor, strafe opposite direction
        if (colorSensor.red() > colorSensor.blue() && team == RED && l.opModeIsActive()){
            drive(8, STRAFE_LEFT);
            realTelemetry.addData("Current Function", "Move on Color Strafe Left (RED)");
            l.idle();
        }
        //If Red Alliance, red particle not detected, strafe right
        else if (colorSensor.red() < colorSensor.blue() && team == RED && l.opModeIsActive()){
            drive(8, STRAFE_RIGHT);
            realTelemetry.addData("Current Function", "Move on Color Strafe Right (RED)");
            l.idle();
        }
        //If Blue Alliance, blue particle facing sensor, strafe opposite direction
        else if (colorSensor.blue() > colorSensor.red() && team == BLUE && l.opModeIsActive()){
            drive(8, STRAFE_LEFT);
            realTelemetry.addData("Current Function", "Move on Color Strafe Left (BLUE)");
            l.idle();
        }
        //If Blue Alliance, blue particle not detected, strafe right
        else if (colorSensor.blue() < colorSensor.red() && team == BLUE && l.opModeIsActive()){
            drive(8, STRAFE_RIGHT);
            realTelemetry.addData("Current Function", "Move on Color Strafe Right (BLUE)");
            l.idle();
        }

        colorSensor.enableLed(false);
        realTelemetry.addData("Current Function", "Move on Color Successful");
        realTelemetry.update();

        l.idle();
    }

    public void testDrive(double power, double time){

        while (l.getRuntime() > time && l.getRuntime() <= time && l.opModeIsActive()) {

            frontLeft.setPower(power);
            backLeft.setPower(power);

            frontRight.setPower(power);
            backRight.setPower(power);

            realTelemetry.addData("Current Function", "Test Drive Successful");
            realTelemetry.update();

            l.idle();
        }
    }
}

