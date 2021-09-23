package org.firstinspires.ftc.teamcode.FreightFrenzy2022;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp
public class TankDrive extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor frontRight;

    @Override
    public void runOpMode() {

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");

        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while(!isStopRequested()) {

            backLeft.setPower(gamepad1.left_stick_y);
            backRight.setPower(gamepad1.right_stick_y);
            frontLeft.setPower(gamepad1.left_stick_y);
            frontRight.setPower(gamepad1.right_stick_y);


        }
    }}