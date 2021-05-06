package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class UltimateGoalAutonomous extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor conveyor;
    private DcMotor grabbingRollerRight;
    private DcMotor grabbingRollerLeft;
    private DcMotor diskLauncher;

    private final double diameter = 4;
    private final double tickCount = 1120;

    private final double circumference = diameter * Math.PI;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        // maps picking up and moving systems
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        conveyor.setDirection(DcMotorSimple.Direction.FORWARD);//swap with REVERSE if the motor goes the wrong way

        grabbingRollerRight = hardwareMap.get(DcMotor.class, "grabbingRollerRight");
        grabbingRollerRight.setDirection(DcMotorSimple.Direction.REVERSE);//swap with REVERSE if the motor goes the wrong way

        grabbingRollerLeft = hardwareMap.get(DcMotor.class, "grabbingRollerLeft");
        grabbingRollerLeft.setDirection(DcMotorSimple.Direction.REVERSE);//swap with REVERSE if the motor goes the wrong way

        diskLauncher = hardwareMap.get(DcMotor.class, "diskLauncher");
        diskLauncher.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();


        while (opModeIsActive()) {

            driveForward(4, 0.6);

            }
        }
        public void driveForward ( int inches, double speed){
            Math.round((inches / circumference) * tickCount);

            rightFront.setTargetPosition(inches);
            leftFront.setTargetPosition(inches);
            rightRear.setTargetPosition(inches);
            leftRear.setTargetPosition(inches);

            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightFront.setPower(speed);
            leftFront.setPower(speed);
            rightRear.setPower(speed);
            leftRear.setPower(speed);

        }
    }