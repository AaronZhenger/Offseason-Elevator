package team5427.frc.robot.subsystems.elevator.io;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Voltage;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.elevator.ElevatorConstants;
import team5427.lib.motors.MotorConfiguration;
import team5427.lib.motors.SteelTalonFX;

public class ElevatorIOTalonFX implements ElevatorIO {
  private SteelTalonFX leaderMotor;
  private SteelTalonFX followerMotor;

  private StatusSignal<Angle> leaderMotorAngle;
  private StatusSignal<AngularVelocity> leaderMotorAngularVelocity;
  private StatusSignal<AngularAcceleration> leaderMotorAngularAcceleration;

  private StatusSignal<Current> leaderMotorCurrent;
  private StatusSignal<Voltage> leaderMotorVoltage;

  private StatusSignal<Angle> followerMotorAngle;
  private StatusSignal<AngularVelocity> followerMotorAngularVelocity;
  private StatusSignal<AngularAcceleration> followerMotorAngularAcceleration;

  private StatusSignal<Current> followerMotorCurrent;
  private StatusSignal<Voltage> followerMotorVoltage;

  public ElevatorIOTalonFX() {
    leaderMotor = new SteelTalonFX(ElevatorConstants.kLeaderMotorID);
    followerMotor = new SteelTalonFX(ElevatorConstants.kFollowerMotorID);

    leaderMotor.apply(ElevatorConstants.kElevatorMotorConfiguration);
    followerMotor.apply(new MotorConfiguration(ElevatorConstants.kElevatorMotorConfiguration));

    leaderMotor.useTorqueCurrentFOC(true);
    followerMotor.useTorqueCurrentFOC(true);

    followerMotor
        .getTalonFX()
        .setControl(
            new Follower(leaderMotor.getTalonFX().getDeviceID(), MotorAlignmentValue.Opposed));

    leaderMotor.setEncoderPosition(ElevatorConstants.kElevatorStowPosition.in(Meters));

    leaderMotorAngle = leaderMotor.getTalonFX().getPosition();
    leaderMotorAngularVelocity = leaderMotor.getTalonFX().getVelocity();
    leaderMotorAngularAcceleration = leaderMotor.getTalonFX().getAcceleration();

    leaderMotorCurrent = leaderMotor.getTalonFX().getStatorCurrent();
    leaderMotorVoltage = leaderMotor.getTalonFX().getMotorVoltage();

    followerMotorAngle = followerMotor.getTalonFX().getPosition();
    followerMotorAngularVelocity = followerMotor.getTalonFX().getVelocity();
    followerMotorAngularAcceleration = followerMotor.getTalonFX().getAcceleration();

    followerMotorCurrent = followerMotor.getTalonFX().getStatorCurrent();
    followerMotorVoltage = followerMotor.getTalonFX().getMotorVoltage();

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kHighPriorityUpdateFrequency, leaderMotorAngle, followerMotorAngle);

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kMediumPriorityUpdateFrequency,
        leaderMotorAngularVelocity,
        followerMotorAngularVelocity,
        leaderMotorCurrent,
        followerMotorCurrent);

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kLowPriorityUpdateFrequency,
        leaderMotorAngularAcceleration,
        followerMotorAngularAcceleration,
        leaderMotorVoltage,
        followerMotorVoltage);

    BaseStatusSignal.waitForAll(Constants.kLoopSpeed, leaderMotorAngle, followerMotorAngle);

    BaseStatusSignal.waitForAll(
        Constants.kLoopSpeed,
        leaderMotorAngularVelocity,
        followerMotorAngularVelocity,
        leaderMotorCurrent,
        followerMotorCurrent);

    BaseStatusSignal.waitForAll(
        Constants.kLoopSpeed,
        leaderMotorAngularAcceleration,
        followerMotorAngularAcceleration,
        leaderMotorVoltage,
        followerMotorVoltage);

    ParentDevice.optimizeBusUtilizationForAll(leaderMotor.getTalonFX(), followerMotor.getTalonFX());
  }

  @Override
  public void updateInputs(ElevatorIOInputsAutoLogged inputs) {
    BaseStatusSignal.refreshAll(leaderMotorAngle, followerMotorAngle);

    BaseStatusSignal.refreshAll(
        leaderMotorAngularVelocity,
        followerMotorAngularVelocity,
        leaderMotorCurrent,
        followerMotorCurrent);

    BaseStatusSignal.refreshAll(
        leaderMotorAngularAcceleration,
        followerMotorAngularAcceleration,
        leaderMotorVoltage,
        followerMotorVoltage);

    inputs.leaderMotorDistance = Meters.of(leaderMotor.getEncoderPosition(leaderMotorAngle));
    inputs.leaderMotorLinearVelocity = MetersPerSecond.of(leaderMotor.getEncoderVelocity(leaderMotorAngularVelocity));
    inputs.leaderMotorLinearAcceleration = MetersPerSecondPerSecond.of(leaderMotor.getEncoderAcceleration(leaderMotorAngularAcceleration));

    inputs.leaderMotorCurrent = leaderMotorCurrent.getValue();
    inputs.leaderMotorVoltage = leaderMotorVoltage.getValue();

    inputs.followerMotorDistance = Meters.of(followerMotor.getEncoderPosition(followerMotorAngle));
    inputs.followerMotorLinearVelocity = MetersPerSecond.of(followerMotor.getEncoderVelocity(followerMotorAngularVelocity));
    inputs.followerMotorLinearAcceleration = MetersPerSecondPerSecond.of(followerMotor.getEncoderAcceleration(followerMotorAngularAcceleration));

    inputs.followerMotorCurrent = followerMotorCurrent.getValue();
    inputs.followerMotorVoltage = followerMotorVoltage.getValue();
  }

  @Override
  public void setElevatorPosition(Distance distance) {
    leaderMotor.setSetpoint(distance);
  }

  @Override
  public void setElevatorPosition(double meters) {
    leaderMotor.setSetpoint(Meters.of(meters));
  }
}
