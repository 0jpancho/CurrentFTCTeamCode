package org.firstinspires.ftc.teamcode.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by Joseph on 11/12/2017.
 */
@Autonomous(name = "Blue Test", group = "Sctuff")
public class BlueTest extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode() throws InterruptedException {

        robot.initialize(BlueTest.this, hardwareMap, telemetry);

        waitForStart();

        robot.driveForward(12);
        robot.turnLeft(120);
    }
}
