package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class encoderTEST extends LinearOpMode {
    private DcMotor testTest;
    private Servo servoPain;

    @Override
    public void runOpMode()  {
        testTest = hardwareMap.get(DcMotor.class, "testTest");
        servoPain = hardwareMap.get(Servo.class, "servoPain");

        testTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("haha finally working f u", testTest.getCurrentPosition());
            telemetry.update();

            if (gamepad1.a) {
                testTest.setTargetPosition(1120);
                testTest.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                testTest.setPower(1);

                while (testTest.isBusy()) {

                }
                testTest.setPower(0);

            }

        }
    }
}
