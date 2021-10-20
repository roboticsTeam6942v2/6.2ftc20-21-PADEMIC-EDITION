package org.firstinspires.ftc.teamcode.FreightFrenzy2022;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class arm extends LinearOpMode {

    private DcMotor arm;
    boolean isUp = false;

    @Override
    public void runOpMode() {

        // Name strings must match up with the config on the Robot Controller app.
        // initialize four DcMotors for the drive train, and add the speed variable
        arm = hardwareMap.get(DcMotor.class, "arm");

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // looping code during the game
        while (!isStopRequested()) {

            telemetry.addData("Status", "Running");
            telemetry.addData("Ticks: ", arm.getCurrentPosition());
            telemetry.update();

            if (gamepad1.a && isUp==false) {
                arm.setTargetPosition(-2190);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(1);
                isUp=true;
                while (arm.isBusy()) {
                }
            }
            else if (gamepad1.a && isUp==true){
                arm.setTargetPosition(0);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(1);
                isUp=false;
                while (arm.isBusy()) {
                }
            }
            }
        }
    }