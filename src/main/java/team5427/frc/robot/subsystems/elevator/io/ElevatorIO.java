package team5427.frc.robot.subsystems.elevator.io;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs {
    public Distance leaderMotorDistance = Meters.of(0.0);
    public LinearVelocity leaderMotorLinearVelocity = MetersPerSecond.of(0.0);
    public LinearAcceleration leaderMotorLinearAcceleration = MetersPerSecondPerSecond.of(0.0);

    public Current leaderMotorCurrent = Amps.of(0.0);
    public Voltage leaderMotorVoltage = Volts.of(0.0);

    public Distance followerMotorDistance = Meters.of(0.0);
    public LinearVelocity followerMotorLinearVelocity = MetersPerSecond.of(0.0);
    public LinearAcceleration followerMotorLinearAcceleration = MetersPerSecondPerSecond.of(0.0);

    public Current followerMotorCurrent = Amps.of(0.0);
    public Voltage followerMotorVoltage = Volts.of(0.0);
  }

  public default void updateInputs(ElevatorIOInputsAutoLogged inputs) {}

  public default void setElevatorPosition(Distance distance) {}

  public default void setElevatorPosition(double meters) {}
}
