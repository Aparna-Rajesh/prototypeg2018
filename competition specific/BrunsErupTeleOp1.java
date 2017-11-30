package org.firstinspires.ftc.teamcode.BrunswickErup;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by aparn on 11/10/2017.
 */

@TeleOp(name = "TeleOp: BrunsErupTeleOp1", group = "TeleOp")
public class BrunsErupTeleOp1 extends LinearOpMode{
    BrunsErupHardware BabyElle = new BrunsErupHardware();

    double leftDriveAmount, rightDriveAmount;
    double    grabPosition      = 0.2 ;
    double    GRAB_SPEED       = 0.01 ;

    double GRAB_MIN_RANGE = 0.40; //prev 0.00
    double GRAB_MAX_RANGE = 1.00;

    double flipperAmount;

    @Override
    public void runOpMode(){
        BabyElle.init(hardwareMap);

        telemetry.addData("Status", "Baby Elle is ready to run");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            leftDriveAmount = gamepad1.left_stick_y;
            rightDriveAmount = gamepad1.right_stick_y;

            BabyElle.leftDrive.setPower(leftDriveAmount);
            BabyElle.rightDrive.setPower(rightDriveAmount);


            //attachments:

            if(gamepad2.b){
                telemetry.addData("", BabyElle.glyphGrip.getPosition());
                telemetry.update();
            }


            if (gamepad2.a)
                grabPosition += GRAB_SPEED;

            if (gamepad2.y)
                grabPosition -= GRAB_SPEED;

            grabPosition  = Range.clip(grabPosition, GRAB_MIN_RANGE, GRAB_MAX_RANGE);
            BabyElle.glyphGrip.setPosition(grabPosition);

            if(gamepad2.right_stick_y < 0.0){
                BabyElle.flipper.setPower(0.1);
            }
            if(gamepad2.right_stick_y > 0.0){
                BabyElle.flipper.setPower(-0.1);
            }
            if(gamepad2.right_bumper){
                BabyElle.flipper.setPower(0.0);
            }


            telemetry.addData("Left Drive: ",   "%.2f", leftDriveAmount);
            telemetry.addData("Right Drive: ",  "%.2f", rightDriveAmount);
            telemetry.addData("Flipper: ",  "%.2f", flipperAmount);
            telemetry.addData("Gripper: ", "%.2f", grabPosition);
            telemetry.update();
        }
    }
}
