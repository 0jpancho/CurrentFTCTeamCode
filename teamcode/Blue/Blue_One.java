package org.firstinspires.ftc.teamcode.Blue;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Joseph Pancho on 12/6/2017.
 */
@Autonomous(name = "Blue One", group = "Stuff")
public class Blue_One extends LinearOpMode {

    public DcMotor frontLeft, frontRight, backLeft, backRight, lift;
    public Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight;

    public final int LEVEL_ONE = 1;
    public final int LEVEL_TWO = 2;

    public final int SERVOS_OPEN = 1;
    public final int SERVOS_CLOSE = 2;

    public final int DRIVE_FORWARD = 1;
    public final int DRIVE_BACKWARD = 2;
    public final int STRAFE_LEFT = 3;
    public final int STRAFE_RIGHT = 4;

    public final double autonDriveSpeed = 0.5;

    public AHRS navX;
    private final int NAVX_DIM_I2C_PORT = 0;


    public void runOpMode() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        lift = hardwareMap.dcMotor.get("lift");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");

        topLiftRight = hardwareMap.servo.get("topLiftRight");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");

        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        navX = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);

        waitForStart();

        while (opModeIsActive() && getRuntime() >= 0 && getRuntime() <= 5) {
            liftState(LEVEL_TWO);
            servoState(SERVOS_CLOSE);
            drive(DRIVE_FORWARD, 2);
            turnLeft(-60);
            turnRight(60);
        }
    }
    public void liftState(int state) {

        if (state == LEVEL_ONE) {
            lift.setTargetPosition(0);
        }

        else if (state == LEVEL_TWO) {
            lift.setTargetPosition(1500);
        }
    }

    public void servoState (int state) {

        if (state == SERVOS_OPEN) {
            topLiftLeft.setPosition(0.4);
            bottomLiftLeft.setPosition(0.6);

            topLiftRight.setPosition(0.6);
            bottomLiftRight.setPosition(0.4);
        }

        else if (state == SERVOS_CLOSE) {
            topLiftLeft.setPosition(0);
            bottomLiftLeft.setPosition(1);

            topLiftRight.setPosition(1);
            bottomLiftRight.setPosition(0);
        }
    }

    public void drive (int direction, double time) {

        double startTime = getRuntime();

        while (time > getRuntime() - startTime  && opModeIsActive()) {

            if (direction == DRIVE_FORWARD) {
                frontLeft.setPower(-autonDriveSpeed);
                backLeft.setPower(-autonDriveSpeed);

                frontRight.setPower(autonDriveSpeed);
                backRight.setPower(autonDriveSpeed);
            }

            else if (direction == DRIVE_BACKWARD) {
                frontLeft.setPower(autonDriveSpeed);
                backLeft.setPower(autonDriveSpeed);

                frontRight.setPower(-autonDriveSpeed);
                backRight.setPower(-autonDriveSpeed);
            }

            else if (direction == STRAFE_LEFT) {
                frontLeft.setPower(-autonDriveSpeed);
                backLeft.setPower(autonDriveSpeed);

                frontRight.setPower(-autonDriveSpeed);
                backRight.setPower(autonDriveSpeed);
            }

            else if (direction == STRAFE_RIGHT) {

                frontLeft.setPower(autonDriveSpeed);
                backLeft.setPower(-autonDriveSpeed);

                frontRight.setPower(autonDriveSpeed);
                backRight.setPower(-autonDriveSpeed);
            }
        }
    }
    public void turnLeft(double degrees) {

        double power = 0.35;
        double yaw  = navX.getYaw();

        while (yaw >= degrees && opModeIsActive()) {

            frontLeft.setPower(power);
            backLeft.setPower(power);

            frontRight.setPower(-power);
            backRight.setPower(-power);

            idle();
        }
    }

    public void turnRight(double degrees) {

        double power = 0.35;
        double yaw  = navX.getYaw();

        while (yaw <= degrees && opModeIsActive()) {

            frontLeft.setPower(power);
            backLeft.setPower(power);

            frontRight.setPower(power);
            backRight.setPower(power);

            idle();
        }
    }
}