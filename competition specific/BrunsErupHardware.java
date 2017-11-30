package org.firstinspires.ftc.teamcode.BrunswickErup;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aparn on 11/8/2017.
 */

public class BrunsErupHardware {
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public Servo glyphGrip = null;
    public DcMotor flipper = null;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        leftDrive  = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");

        glyphGrip = hwMap.get(Servo.class, "glyphGrip");
        flipper = hwMap.get(DcMotor.class, "flipper");

        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        //glyphGrip.setPosition(0.0); //TODO: figure out where build wants it to start (range is from 0.0 - 1.0)
        flipper.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
