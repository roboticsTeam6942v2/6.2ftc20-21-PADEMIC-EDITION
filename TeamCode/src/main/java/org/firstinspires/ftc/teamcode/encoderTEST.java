package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class encoderTEST extends LinearOpMode {
    private DcMotor testTest;

    @Override
    public void runOpMode()  {
        testTest = hardwareMap.get(DcMotor.class, "testTest");

        testTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while(opModeIsActive()) {

            if (gamepad1.a) {
                testTest.setTargetPosition(1120);
                testTest.setPower(1);
                while (testTest.isBusy()) {
                }

            }

        }
    }
}
