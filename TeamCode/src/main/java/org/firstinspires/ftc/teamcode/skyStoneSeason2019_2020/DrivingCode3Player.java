//DriveCode2Players.java 1/23/2020

package org.firstinspires.ftc.teamcode.skyStoneSeason2019_2020;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
//navX
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

@Disabled
@TeleOp

public class DrivingCode3Player extends LinearOpMode {
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
    double newZeroYaw;
    double counterDriftPower;
    double currentDrift;
    // gyro objects
    private IntegratingGyroscope gyro;
    private NavxMicroNavigationSensor navxMicro;
      ColorSensor colorSensorLT;
      
    @Override
    public void runOpMode() {
       initNavx();
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
        
        while(opModeIsActive()) {
            telemetry.addData("Position",  "%7d",
            claw.getCurrentPosition());
            telemetry.update(); 
            ls = gamepad1.left_trigger;
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
            
            //middlePower = gamepad1.left_stick_x;
            
            rt=gamepad1.right_trigger;
            leftPower = Range.clip(drive + turn,-1,1);
            rightPower = Range.clip(drive - turn,-1,1);
            
       if(ls>0){
            currentDrift=(getYaw());
        telemetry.addData("Yaw: ", getYaw());
    
telemetry.update(); 
        }
            
        
        if(rt>0  && opModeIsActive()){
            
            newZeroYaw=(currentDrift-getYaw());
            counterDriftPower=(newZeroYaw/90)*2;
            backRight.setPower(counterDriftPower);
            backLeft.setPower(-counterDriftPower);
            
            
            
            
        }
           backRight.setPower(0);
            backLeft.setPower(0);
            
           backLeft.setPower(leftPower);
        backRight.setPower(rightPower);
            
            //midShift.setPower(middlePower);
            liftLeft.setPower(liftPower);
            liftRight.setPower(liftPower);
            
            
        } 
        }

        // navX
private void initNavx() {
    navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
    gyro = (IntegratingGyroscope)navxMicro;
    telemetry.log().add("do not move robot navx is calibrating");
      while (navxMicro.isCalibrating() && opModeIsActive());
        telemetry.log().clear();
        telemetry.log().add("done calibrating");
}
private float getYaw() {
    Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    return angles.firstAngle;

}  
}
 
