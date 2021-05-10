package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

    private BNO055IMU imu;
    private Orientation angles;

    private final double diameter = 4;
    private final double tickCount = 1120;

    private final double circumference = diameter * Math.PI;

    @Override
    public void runOpMode() {
        //Initializes Gyro stuff, could be improved later but i just wanted to do this for now :P
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //Initializes Drive motors!
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        //RESETS THE DRIVE MOTORS' ENCODER POSITIONS!!! so that where it is now is the new 0.
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets it so that encoder ticks are still determined in the background despite not being used.
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //directions stuff ._.
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
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double heading = angles.firstAngle;
            double roll = angles.secondAngle;
            double pitch = angles.thirdAngle;

            telemetry.addData("Heading", heading);
            telemetry.addData("Roll", roll);
            telemetry.addData("Pitch", pitch);
            telemetry.update();

            driveForward(4, 0.6);
            turnRight(0.5, -90); //maybe it'll turn 90 degrees right?

        }
    }

    public void driveForward ( int inches, double speed){
        inches = (int) Math.round((inches / circumference) * tickCount);

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

        while (rightFront.isBusy() && leftFront.isBusy() && rightRear.isBusy() && leftRear.isBusy()) {
            //This block is so that nothing happens while this motors reach their target positions.
        }

    }

    public void turnRight(double speed, double wantedAngle) {
        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        rightRear.setPower(-speed);
        leftRear.setPower(speed);

        if (wantedAngle == angles.firstAngle) {
            rightFront.setPower(0);
            leftFront.setPower(0);
            rightRear.setPower(0);
            leftRear.setPower(0);
        }

    }

}