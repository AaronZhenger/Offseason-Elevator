package team5427.frc.robot.subsystems.diffy.io;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.diffy.DiffyConstants;
import team5427.lib.motors.MotorConfiguration;
import team5427.lib.motors.SteelTalonFX;

public class DiffyIOTalonFXWithFlywheel implements DiffyIO {
  private SteelTalonFX pivotLeaderMotor;
  private SteelTalonFX pivotFollowerMotor;
  private SteelTalonFX flywheelMotor;

  private StatusSignal<Angle> pivotLeaderAngle;
  private StatusSignal<AngularVelocity> pivotLeaderAngularVelocity;
  private StatusSignal<AngularAcceleration> pivotLeaderAngularAcceleration;

  private StatusSignal<Current> pivotLeaderCurrent;
  private StatusSignal<Voltage> pivotLeaderVoltage;

  private StatusSignal<Angle> pivotFollowerAngle;
  private StatusSignal<AngularVelocity> pivotFollowerAngularVelocity;
  private StatusSignal<AngularAcceleration> pivotFollowerAngularAcceleration;

  private StatusSignal<Current> pivotFollowerCurrent;
  private StatusSignal<Voltage> pivotFollowerVoltage;

  private StatusSignal<AngularVelocity> flywheelAngularVelocity;
  private StatusSignal<AngularAcceleration> flywheelAngularAcceleration;

  private StatusSignal<Current> flywheelCurrent;
  private StatusSignal<Voltage> flywheelVoltage;
  private StatusSignal<Temperature> flywheelTemperature;

  public DiffyIOTalonFXWithFlywheel() {
    pivotLeaderMotor = new SteelTalonFX(DiffyConstants.kPivotLeaderMotorID);
    pivotFollowerMotor = new SteelTalonFX(DiffyConstants.kPivotFollowerMotorID);
    flywheelMotor = new SteelTalonFX(DiffyConstants.kFlywheelMotorID);

    pivotLeaderMotor.apply(DiffyConstants.kPivotMotorConfiguration);
    pivotFollowerMotor.apply(new MotorConfiguration(DiffyConstants.kPivotMotorConfiguration));
    flywheelMotor.apply(DiffyConstants.kFlywheelMotorConfiguration);

    pivotFollowerMotor
        .getTalonFX()
        .setControl(
            new Follower(pivotLeaderMotor.getTalonFX().getDeviceID(), MotorAlignmentValue.Aligned));

    pivotLeaderMotor.setEncoderPosition(DiffyConstants.kAverageStowPosition);

    pivotLeaderAngle = pivotLeaderMotor.getTalonFX().getPosition();
    pivotLeaderAngularVelocity = pivotLeaderMotor.getTalonFX().getVelocity();
    pivotLeaderAngularAcceleration = pivotLeaderMotor.getTalonFX().getAcceleration();
    pivotLeaderCurrent = pivotLeaderMotor.getTalonFX().getStatorCurrent();
    pivotLeaderVoltage = pivotLeaderMotor.getTalonFX().getMotorVoltage();

    pivotFollowerAngle = pivotFollowerMotor.getTalonFX().getPosition();
    pivotFollowerAngularVelocity = pivotFollowerMotor.getTalonFX().getVelocity();
    pivotFollowerAngularAcceleration = pivotFollowerMotor.getTalonFX().getAcceleration();
    pivotFollowerCurrent = pivotFollowerMotor.getTalonFX().getStatorCurrent();
    pivotFollowerVoltage = pivotFollowerMotor.getTalonFX().getMotorVoltage();

    flywheelAngularVelocity = flywheelMotor.getTalonFX().getVelocity();
    flywheelAngularAcceleration = flywheelMotor.getTalonFX().getAcceleration();
    flywheelCurrent = flywheelMotor.getTalonFX().getStatorCurrent();
    flywheelVoltage = flywheelMotor.getTalonFX().getMotorVoltage();
    flywheelTemperature = flywheelMotor.getTalonFX().getDeviceTemp();

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kHighPriorityUpdateFrequency,
        pivotLeaderAngle,
        pivotFollowerAngle,
        flywheelAngularVelocity);

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kMediumPriorityUpdateFrequency,
        pivotLeaderAngularVelocity,
        pivotFollowerAngularVelocity,
        pivotLeaderCurrent,
        pivotFollowerCurrent,
        flywheelCurrent);

    BaseStatusSignal.setUpdateFrequencyForAll(
        Constants.kLowPriorityUpdateFrequency,
        pivotLeaderAngularAcceleration,
        pivotFollowerAngularAcceleration,
        pivotLeaderVoltage,
        pivotFollowerVoltage,
        flywheelAngularAcceleration,
        flywheelVoltage,
        flywheelTemperature);

    BaseStatusSignal.waitForAll(
        Constants.kLoopSpeed, pivotLeaderAngle, pivotFollowerAngle, flywheelAngularVelocity);

    BaseStatusSignal.waitForAll(
        Constants.kLoopSpeed,
        pivotLeaderAngularVelocity,
        pivotFollowerAngularVelocity,
        pivotLeaderCurrent,
        pivotFollowerCurrent,
        flywheelCurrent);

    BaseStatusSignal.waitForAll(
        Constants.kLoopSpeed,
        pivotLeaderAngularAcceleration,
        pivotFollowerAngularAcceleration,
        pivotLeaderVoltage,
        pivotFollowerVoltage,
        flywheelAngularAcceleration,
        flywheelVoltage,
        flywheelTemperature);

    ParentDevice.optimizeBusUtilizationForAll(
        pivotLeaderMotor.getTalonFX(), pivotFollowerMotor.getTalonFX(), flywheelMotor.getTalonFX());
  }

  @Override
  public void updateInputs(DiffyInputsAutoLogged inputs) {
    BaseStatusSignal.refreshAll(pivotLeaderAngle, pivotFollowerAngle, flywheelAngularVelocity);

    BaseStatusSignal.refreshAll(
        pivotLeaderAngularVelocity,
        pivotFollowerAngularVelocity,
        pivotLeaderCurrent,
        pivotFollowerCurrent,
        flywheelCurrent);

    BaseStatusSignal.refreshAll(
        pivotLeaderAngularAcceleration,
        pivotFollowerAngularAcceleration,
        pivotLeaderVoltage,
        pivotFollowerVoltage,
        flywheelAngularAcceleration,
        flywheelVoltage,
        flywheelTemperature);

    inputs.pivotLeaderAngle = new Rotation2d(pivotLeaderAngle.getValue());
    inputs.pivotLeaderAngularVelocity = pivotLeaderAngularVelocity.getValue();
    inputs.pivotLeaderAngularAcceleration = pivotLeaderAngularAcceleration.getValue();

    inputs.pivotLeaderCurrent = pivotLeaderCurrent.getValue();
    inputs.pivotLeaderVoltage = pivotLeaderVoltage.getValue();

    inputs.pivotFollowerAngle = new Rotation2d(pivotFollowerAngle.getValue());
    inputs.pivotFollowerAngularVelocity = pivotFollowerAngularVelocity.getValue();
    inputs.pivotFollowerAngularAcceleration = pivotFollowerAngularAcceleration.getValue();

    inputs.pivotFollowerCurrent = pivotFollowerCurrent.getValue();
    inputs.pivotFollowerVoltage = pivotFollowerVoltage.getValue();

    inputs.flywheelLinearVelocity =
        MetersPerSecond.of(flywheelMotor.getEncoderVelocity(flywheelAngularVelocity));
    inputs.flywheelLinearAcceleration =
        MetersPerSecondPerSecond.of(
            flywheelMotor.getEncoderAcceleration(flywheelAngularAcceleration));

    inputs.flywheelCurrent = flywheelCurrent.getValue();
    inputs.flywheelVoltage = flywheelVoltage.getValue();
    inputs.flywheelTemperature = flywheelTemperature.getValue();
  }

  // @Override
  // public void setPivotPosition(Angle angle) {
  //   pivotLeaderMotor.setSetpoint(new Rotation2d(angle));
  // }

  // @Override
  // public void setPivotPosition(Rotation2d angle) {
  //   pivotLeaderMotor.setSetpoint(angle);
  // }

  // @Override
  // public void setPivotPosition(double degrees) {
  //   pivotLeaderMotor.setEncoderPosition(Rotation2d.fromDegrees(degrees));
  // }

  @Override
  public void setFlywheelVelocity(LinearVelocity velocity) {
    flywheelMotor.setSetpoint(velocity);
  }

  @Override
  public void setFlywheelVelocity(double metersPerSecond) {
    flywheelMotor.setSetpoint(MetersPerSecond.of(metersPerSecond));
  }
}
