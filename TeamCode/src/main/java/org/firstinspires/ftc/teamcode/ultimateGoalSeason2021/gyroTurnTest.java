package org.firstinspires.ftc.teamcode.ultimateGoalSeason2021;

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
public class gyroTurnTest extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor conveyor;
    private DcMotor grabbingRollerRight;
    private DcMotor grabbingRollerLeft;
    private DcMotor diskLauncher;
    boolean strafeRightIsRunning = false;
    boolean resetEncoders = true;

    private BNO055IMU imu;
    private Orientation angles;
    int ticksToTravel;
    PIDController pidRotate, pidDrive, pidStrafe;
    private double globalAngle, correction, rotation;
    private double correctionStrafe;
    Orientation lastAngles = new Orientation();
    private double power = 0.3;

    double rightFrontPosition;
    double leftFrontPosition;
    double rightRearPosition;
    double leftRearPosition;

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

        pidRotate = new PIDController(.003, .00003, 0);
        pidDrive = new PIDController(.05, 0, 0);
        pidStrafe = new PIDController(0.003, 0.00003, 0);

        telemetry.addData("Status:", " Putting In Values");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();

        /*pidDrive.setSetpoint(0);
        pidDrive.setOutputRange(0, 0.6);
        pidDrive.setInputRange(-90, 90);
        pidDrive.enable();*/


        while (opModeIsActive()) {

            correction = pidDrive.performPID(getAngle());

            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.addData("4 turn rotation", rotation);
            telemetry.addData("5 rightFront", rightFront.getCurrentPosition());
            telemetry.addData("5 leftFront", leftFront.getCurrentPosition());
            telemetry.addData("5 rightRear", rightRear.getCurrentPosition());
            telemetry.addData("5 leftRear", leftRear.getCurrentPosition());
            telemetry.update();

            rightFront.setPower(power + correction);
            leftFront.setPower(power - correction);
            rightRear.setPower(power + correction);
            leftRear.setPower(power - correction);

            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double heading = angles.firstAngle;
            double roll = angles.secondAngle;
            double pitch = angles.thirdAngle;


            //turnRightEncoder(-90, 0.6);
            //turnRightGyro(90, power);
            //turnRightGyro(-90, power);
            //turnRightGyro(45, power);
            //strafeRight(20, 0.5);
            strafeRightWithPID(20, 0.5);

            rightFront.setPower(0);
            leftFront.setPower(0);
            rightRear.setPower(0);
            leftRear.setPower(0);

            sleep(5000);
        }


    }

    private void turnRightGyro(double whatAngle, double speed) {

        resetAngle();

        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (Math.abs(whatAngle) > 359) whatAngle = (int) Math.copySign(359, whatAngle);

        pidRotate.reset();
        pidRotate.setSetpoint(whatAngle);
        pidRotate.setInputRange(0, whatAngle);
        pidRotate.setOutputRange(0, speed);
        pidRotate.setTolerance(1);
        pidRotate.enable();

        if (whatAngle < 0) {

            while(opModeIsActive() && getAngle() == 0) {
                rightFront.setPower(-speed);
                leftFront.setPower(speed);
                rightRear.setPower(-speed);
                leftRear.setPower(speed);
                sleep(100);
            }

            do {
                speed = pidRotate.performPID(getAngle());
                rightFront.setPower(speed);
                leftFront.setPower(-speed);
                rightRear.setPower(speed);
                leftRear.setPower(-speed);
            } while (opModeIsActive() && !pidRotate.onTarget());
        }
        else
            do {
                speed = pidRotate.performPID(getAngle());
                rightFront.setPower(speed);
                leftFront.setPower(-speed);
                rightRear.setPower(speed);
                leftRear.setPower(-speed);
            } while (opModeIsActive() && !pidRotate.onTarget());

        /*if (whatAngle > 0) {

            while(angles.firstAngle < whatAngle-10){

                rightFront.setPower(speed);
                leftFront.setPower(-speed);
                rightRear.setPower(speed);
                leftRear.setPower(-speed);
                gyroCalibrate();

                //break;

            }
        } else if(whatAngle < 0) {

            while (angles.firstAngle > whatAngle+10){

                rightFront.setPower(-speed);
                leftFront.setPower(speed);
                rightRear.setPower(-speed);
                leftRear.setPower(speed);
                gyroCalibrate();

                //break;

            }
        }*/
        speed = 0;
        rightFront.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(speed);

        rotation = getAngle();

        sleep(500);

        resetAngle();

        /*while (!isStopRequested()) {
            telemetry.addData("turnRightGyro", "going");
            telemetry.addData("Angle", angles.firstAngle);
            telemetry.addData("Heading", angles.firstAngle);
            telemetry.addData("Roll", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();

        }*/
        /*if (whatAngle == angles.firstAngle) {
            speed = 0;
            rightFront.setPower(speed);
            leftFront.setPower(speed);
            rightRear.setPower(speed);
            leftRear.setPower(speed);
        }*/
        /*while (rightFront.isBusy() && leftFront.isBusy() && rightRear.isBusy() && leftRear.isBusy()) {
            telemetry.addData("turnRightGyro", "going");
            telemetry.addData("Angle", angles.firstAngle);
            telemetry.update();
        }*/

    }
    private void gyroCalibrate() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = angles.firstAngle;
        double roll = angles.secondAngle;
        double pitch = angles.thirdAngle;

        telemetry.addData("Status:", " Initialized");
        telemetry.addData("Heading", heading);
        telemetry.addData("Roll", roll);
        telemetry.addData("Pitch", pitch);
        telemetry.addData("leftFront", leftFront.getCurrentPosition());
        telemetry.addData("rightFront", rightFront.getCurrentPosition());
        telemetry.addData("leftRear", leftRear.getCurrentPosition());
        telemetry.addData("rightRear", rightRear.getCurrentPosition());
        telemetry.update();
    }

    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }

    private double getAngle() {

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    class encoderPosition {

        private void positionReturn() {

            int rightFrontPosition = rightFront.getCurrentPosition();
            int leftFrontPosition = leftFront.getCurrentPosition();
            int rightRearPosition = rightRear.getCurrentPosition();
            int leftRearPosition = leftRear.getCurrentPosition();

        }
    }
    private encoderPosition getPosition() {
        encoderPosition position = new encoderPosition();

        position.positionReturn();

        return position;

    }


    private void turnRightEncoder(int whatAngle, double speed) {
        ticksToTravel = (int) Math.round((tickCount / circumference) * whatAngle);

        rightFront.setTargetPosition(-ticksToTravel);
        leftFront.setTargetPosition(ticksToTravel);
        rightRear.setTargetPosition(-ticksToTravel);
        leftRear.setTargetPosition(ticksToTravel);

        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightFront.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(speed);

        while (rightFront.isBusy() && leftFront.isBusy() && rightRear.isBusy() && leftRear.isBusy()) {
            telemetry.addData("turnRightEncoder", "going");
            telemetry.addData("Angle", angles.firstAngle);
            telemetry.addData("leftFront", leftFront.getCurrentPosition());
            telemetry.addData("rightFront", rightFront.getCurrentPosition());
            telemetry.addData("leftRear", leftRear.getCurrentPosition());
            telemetry.addData("rightRear", rightRear.getCurrentPosition());
            telemetry.update();
        }
        speed = 0;
        rightFront.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(speed);

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void strafeRight(int inches, double speed) {
        //moving right and left
        //ticksToTravel = (int) Math.round((inches / circumference) * tickCount);
        ticksToTravel = (int) Math.round((tickCount/circumference)*inches);

        strafeRightIsRunning=true;

        rightFront.setTargetPosition(-ticksToTravel);
        leftFront.setTargetPosition(ticksToTravel);
        rightRear.setTargetPosition(ticksToTravel);
        leftRear.setTargetPosition(-ticksToTravel);
        //Then perish <--- my heart ;-;
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(-speed);

        while (rightFront.isBusy() && leftFront.isBusy() && rightRear.isBusy() && leftRear.isBusy()) {
            //This block is so that nothing happens while this motors reach their target positions, also telemetry
            telemetry.addData("Status:", " Running");
            telemetry.addData("Motor:", speed);
            telemetry.addData("Angle", angles.firstAngle);
            telemetry.addData("leftFront", leftFront.getCurrentPosition());
            telemetry.addData("rightFront", rightFront.getCurrentPosition());
            telemetry.addData("leftRear", leftRear.getCurrentPosition());
            telemetry.addData("rightRear", rightRear.getCurrentPosition());
            telemetry.update();

        }

        speed = 0;

        rightFront.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(speed);

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        resetEncoders=false;
        strafeRightIsRunning=false;
    }

    public void strafeRightWithPID (int inches, double speed) {
        ticksToTravel = (int) Math.round((tickCount/circumference)*inches);

        strafeRightIsRunning=true;

        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        resetAngle();
        pidStrafe.reset();
        pidStrafe.setSetpoint(0);
        pidStrafe.setInputRange(-90, 90); //Sets min and max of... input/??? idek
        pidStrafe.setOutputRange(-speed, speed); //I did negative and positive cause strafe uses both.
        pidStrafe.enable();

        correctionStrafe = pidStrafe.performPID(getAngle());

        if (inches < 0) {
            while (opModeIsActive() && getAngle() == 0 ) {
                rightFront.setPower(speed);
                leftFront.setPower(-speed);
                rightRear.setPower(-speed);
                leftRear.setPower(speed);
                sleep(100);
            }

            do {
                speed = pidStrafe.performPID(getAngle());
                rightFront.setPower(-speed);
                leftFront.setPower(speed);
                rightRear.setPower(speed);
                leftRear.setPower(-speed);
            } while (opModeIsActive() && !pidStrafe.onTarget());
        }
        else
            do {
                speed = pidStrafe.performPID(getAngle());
                rightFront.setPower(-speed);
                leftFront.setPower(speed);
                rightRear.setPower(speed);
                leftRear.setPower(-speed);
            } while (opModeIsActive() && !pidStrafe.onTarget());

        /*rightFront.setPower(-speed + correctionStrafe);
        leftFront.setPower(speed - correctionStrafe);
        rightRear.setPower(speed + correctionStrafe);
        leftRear.setPower(-speed - correctionStrafe);*/

        //idk i'm adding the correction properly.

        /*this isn't much but I had something in mind i wanted to do and then when I wanted to do it.
        I FORGOT IT, WHY DOES IT HAVE TO BE LIKE THAT!?!?? so i wrote this hoping i would remember but
        i didn't. Also i'm having a pretty tough time trying to wrap my mind around getting strafe to
        work. I thought maybe it could be this simple cause in their example code they really didn't
        have much for their driving forward code. but i doubt this is gonna work ngl.
         */

        //just copied the pid for the gyro, might work? don't see why not but whatever

        while (leftFront.getCurrentPosition() == ticksToTravel || rightRear.getCurrentPosition() == ticksToTravel) {
            speed = 0;

            rightFront.setPower(-speed);
            leftFront.setPower(speed);
            rightRear.setPower(speed);
            leftRear.setPower(-speed);

        }

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

}

