//DriveCode2Players.java 1/23/2020

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp

public class DrivingCode2Player extends LinearOpMode {
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor midShift;
    DcMotor liftLeft;
    DcMotor liftRight;
    DcMotor claw;
    Servo pushServo;
    double leftPower;
    double rightPower;
    double middlePower;
    double liftPower;
    double drive;
    double turn;
    double ls;
    double rt;
    int clawPosition;
 
    @Override
    public void runOpMode() {
       
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        midShift = hardwareMap.get(DcMotor.class,"midShift");
        liftLeft = hardwareMap.get(DcMotor.class,"liftLeft");
        liftRight = hardwareMap.get(DcMotor.class,"liftRight");
        claw = hardwareMap.get(DcMotor.class,"claw");
        pushServo = hardwareMap.get(Servo.class, "pushServo");
        
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.FORWARD);

        claw.setDirection(DcMotorSimple.Direction.REVERSE);
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        midShift.setDirection(DcMotorSimple.Direction.REVERSE);
        
        pushServo.setPosition(1);
        
        waitForStart();
        
        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        while(!isStopRequested()) {
            telemetry.addData("Position",  "%7d",
            claw.getCurrentPosition());
            telemetry.update(); 
            
            rt = gamepad1.right_trigger;
            turn = gamepad1.right_stick_x;
            liftPower = (gamepad2.right_stick_y);
            
            if (gamepad1.a) {
               pushServo.setPosition(0);
               sleep(1000);
               pushServo.setPosition(1);
            }
            
            
            if (gamepad2.a) {
             claw.setTargetPosition(250); 
             claw.setPower(1);
                
                claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.b) {
              
                claw.setTargetPosition(80); 
                claw.setPower(1);
                
                claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               }
            
            drive = -gamepad1.left_stick_y;
            
            middlePower = gamepad1.left_stick_x;
            

            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);

            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            
            midShift.setPower(middlePower);
            liftLeft.setPower(liftPower);
            liftRight.setPower(liftPower);
            
            
        } 
        }}
    
 
