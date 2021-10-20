package org.firstinspires.ftc.teamcode.FreightFrenzy2022;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AutonomousColorTest extends LinearOpMode {

    //scanning for yellow currently for autonomous; may change if we decide to use a team element or smth

    DcMotor backLeft;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor frontRight;
    ColorSensor colorSensor;
    int ticksToTravel;
    private final double diameter = 5;
    private final double tickCount = 1120;
    private final double circumference = diameter * Math.PI;
    boolean driveForwardIsRunning = false;
    boolean resetEncoders = true;
    boolean skip = false;
    boolean bottom = false;
    boolean middle = false;
    boolean top = false;
    
    @Override
    public void runOpMode() {

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //RESETS THE DRIVE MOTORS' ENCODER POSITIONS!!! so that where it is now is the new 0.
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets it so that encoder ticks are still determined in the background despite not being used.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        waitForStart();

        telemetry.addData("colorSensorBlue: ", colorSensor.blue());
        telemetry.addData("colorSensorRed: ", colorSensor.red());
        telemetry.update();

        if (driveForwardIsRunning == false && resetEncoders == false) {
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            resetEncoders = true;

        }

        run();
    }
    public void driveForward ( int inches, double speed){
        //moving forward and backwards
        //ticksToTravel = (int) Math.round((inches / circumference) * tickCount);
        ticksToTravel = (int) Math.round((tickCount/circumference)*inches);

        driveForwardIsRunning=true;

        frontRight.setTargetPosition(ticksToTravel);
        frontLeft.setTargetPosition(ticksToTravel);
        backRight.setTargetPosition(ticksToTravel);
        backLeft.setTargetPosition(ticksToTravel);

        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        backRight.setPower(speed);
        backLeft.setPower(speed);

        /*while (frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy()) {
            //This block is so that nothing happens while this motors reach their target positions, also telemetry
            telemetry.addData("Status:", " Running");
            telemetry.addData("Motor:", speed);
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.update();

        }*/

        while (frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy()) {
            telemetry.addData("colorSensorBlue: ", colorSensor.blue());
            telemetry.addData("colorSensorRed: ", colorSensor.red());
            telemetry.update();
        }
        
        speed = 0;

        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        backRight.setPower(speed);
        backLeft.setPower(speed);

        resetEncoders=false;
        driveForwardIsRunning=false;

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    //bottom layer, from wall to first spot
    public void interval1 (){

        driveForward( 26, 0.1);

        while(driveForwardIsRunning == true) {
            if (colorSensor.blue() < 120 && colorSensor.blue() > 110 && colorSensor.red() < 115 && colorSensor.red() > 105) {
                skip = true;
                bottom = true;
                middle = false;
                top = false;
                telemetry.addData("bottom ", "true");
                telemetry.update();
            } else {
                skip = false;
            }
        }

    }

    //middle layer, from first to second
    public void interval2(){

        driveForward( 10, 0.1);

        while(driveForwardIsRunning == true) {
            if (colorSensor.blue() < 120 && colorSensor.blue() > 110 && colorSensor.red() < 115 && colorSensor.red() > 105) {
                skip = true;
                bottom = false;
                middle = true;
                top = false;
                telemetry.addData("middle ", "true");
                telemetry.update();
            } else {
                skip = false;
            }
        }
    }

    //top layer, from second to third
    public void interval3(){

        driveForward( 9, 0.1);

        while(driveForwardIsRunning == true) {
            if (colorSensor.blue() < 120 && colorSensor.blue() > 110 && colorSensor.red() < 115 && colorSensor.red() > 105) {
                skip = true;
                bottom = false;
                middle = false;
                top = true;
                telemetry.addData("top ", "true");
                telemetry.update();
            } else {
                skip = false;
            }
        }
    }

    // idk what we're using to pick up the blocks plus this is on testy so fill this out whenever we figure out what we're using
    public void armOrWhatever(){

        if (bottom == true) {
            driveForward( 33, 0.1);
            telemetry.addData("item ", "placed on bottom");
            telemetry.update();
        }

        if (middle == true){
            driveForward( 23, 0.1);
            telemetry.addData("item ", "placed on middle");
            telemetry.update();
        }

        if (top == true){
            driveForward( 15, 0.1);
            telemetry.addData("item ", "placed on top");
            telemetry.update();
        }
    }

    //the entire thing in one go woohoo hope this works
    public void run(){

        interval1();
        if (skip == true) {
            armOrWhatever();
        }
        else{
            interval2();
            if (skip == true) {
                armOrWhatever();
            }
            else{
                interval3();
                if (skip == true) {
                    armOrWhatever();
                }
                else{
                    driveForward(40, 0.1);
                    sleep(30000);
                }
            }
        }

    }

}
